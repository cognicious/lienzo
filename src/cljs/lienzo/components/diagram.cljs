(ns lienzo.components.diagram
  (:require [reagent.core :as r]
            ))

;; Example state
{:id "diagram-id"
 
 :selected {:drag {:id "id" :last [0 0]}
            :current {:id "id"}}
 :draw {}}

(defn edge 
  "Draw an edge from x1 y1 to x2 y2 and put label as text inside"
  [state-atm & {:keys [id class x1 y1 x2 y2 label]
                :as   args}]
  (let [id (or id (str "edge-" (random-uuid)))
        class (if class (str class " l-edge") "l-edge")]
    (fn [state-atm & {:keys [id class x1 y1 x2 y2 label]
                      :or {id id class class}
                      :as   args}]
      (let [h (- y2 y1)                          ;; height
            b (- x2 x1)                          ;; base
            m (/ (- y2 y1) (- x2 x1))            ;; slope
            d (/ (* (Math/atan m) 180) Math/PI)  ;; rads to deg
            o (if (< b 0) 180 0)                 ;; orientation
            p (Math/sqrt (+ (Math/pow h 2) (Math/pow b 2))) ;; hypotenuse
            id-pat (str "pat-" id)
            id-flt (str "flt-" id)]
        [:g {:id id :class class}
         [:defs
          [:pattern {:id id-pat  ;; pattern with arrows
                     :class "pattern"
                     :patternUnits "userSpaceOnUse"
                     :x 0
                     :y (+ y1 5) ;; tricky background offset
                     :width 14
                     :height 30}
           [:rect {:class "background" :x 0 :y 0 :width 14 :height 40}]
           [:line {:class "arrow-top" :x1 5 :y1 5 :x2 13 :y2 13}]
           [:line {:class "arrow-bottom" :x1 13 :y1 13 :x2 5 :y2 20}]]
          [:filter {:id id-flt}
           [:feGaussianBlur {:in "SourceGraphic" :stdDeviation 1}]]]
         [:rect {:x x1
                 :y y1
                 :width p
                 :height 34
                 :transform (str  "rotate(" (+ d o) " " x1 "," y1 ")")
                 :style {:fill (str "url(#" id-pat ")")}}]
         (let [x  (+ x1 (/ b 2.5) (if (< h 0) 13 -13))
               y  (+ y1 (/ h 2.5) (if (< b 0) -13 13))
               cx (+ x (* (count label) 4))
               tr (str "rotate(" (+ d 0) " " x "," y ")")]
           [:g
            [:text {:class "shadow" :x x :y y :filter (str "url(#" id-flt ")") :transform tr} label]
            [:text {:class "label" :x x :y y :transform tr} label]
            [:circle {:cx cx :cy y :r 5 :transform tr}]
            ])]))))

(defn on 
  "Fired when MouseDown occurs in a vertex"
  [ev state-atm id]
  (let [ne (.-nativeEvent ev)
        page-x (.-pageX ne)
        page-y (.-pageY ne)]
    
    ;; Takes our vertex id and replace :selected values
    (swap! state-atm update-in [:selected] assoc :drag {:id id :last [page-x page-y]}
                                                 :current {:id id})
    

    ;; When a edge is drawed
    (if-let [line (get-in @state-atm [:draw :line])]
      (let [prev-id (get-in @state-atm [:draw :id])
            [p-offset-x  p-offset-y] (get-in @state-atm [:vertices prev-id :position] [0 0])
            [offset-x  offset-y] (get-in @state-atm [:vertices id :position] [0 0])
            my-edge [edge state-atm 
                     :x1 (+ p-offset-x 30) 
                     :y1 (+ p-offset-y 30)
                     :x2 (+ offset-x 30)
                     :y2 (+ offset-y 30)]]
        (swap! state-atm update-in [:edges] conj {:from prev-id :to id :label "added"})
        (swap! state-atm dissoc :draw)))))

(defn off 
  "Fired when MouseUp occurs in a vertex"
  [state-atm]
  ;(swap! state-atm update-in [:selected :drag] #(get-in @state-atm [:selected :previous]))
  
  ;; Stop to dragging vertex
  (swap! state-atm update-in [:selected] dissoc :drag))

(defn move 
  "Fired when MouseMove occurs over svg"
  [ev state-atm]
  (.stopPropagation ev)
  (.preventDefault ev)
  (let [ne (-> ev .-nativeEvent)
        page-x (-> ne .-pageX)
        page-y (-> ne .-pageY)
        source-tag (-> ne .-srcElement .-nodeName)]
    
    ;; When dragging a vertex 
    (if-let [drag (get-in @state-atm [:selected :drag])] 
      (let [{:keys [id last]} drag
            [last-x last-y] last
            [offset-x offset-y] (get-in @state-atm [:vertices id :position] [0 0])]
        (swap! state-atm assoc-in [:vertices id :position] [(- (+ offset-x page-x) last-x)
                                                         (- (+ offset-y page-y) last-y)])
        (swap! state-atm update-in [:selected] assoc :drag {:id id :last [page-x page-y]})))

    ;; When drawing a line
    (if-let [line (get-in @state-atm [:draw :line])]
      (let [id (get-in @state-atm [:draw :id])
            [offset-x offset-y] (get-in @state-atm [:vertices id :position] [0 0])]
        (swap! state-atm update-in [:draw] assoc :line [edge state-atm
                                                        :x1 (+ offset-x 30)
                                                        :y1 (+ offset-y 30)
                                                        :x2 page-x
                                                        :y2 page-y])))

    ;; When move outside a vertex
    (if (= source-tag "svg")
      (swap! state-atm dissoc :vertex-hover))))

(defn click 
  "Fired when onClick occurs over svg"
  [ev state-atm]
  (let [ne (-> ev .-nativeEvent)
        source-tag (-> ne .-srcElement .-nodeName)
        page-x (.-pageX ne)
        page-y (.-pageY ne)]

    ;; When click is over empty space
    (cond (= source-tag "svg")
          (do (swap! state-atm update-in [:selected] dissoc :current)
              (swap! state-atm dissoc :draw)))))

(defn draw-on 
  "Fired when OnMouseDown in Connect"
  [ev state-atm id]
  (.stopPropagation ev) 
  (.preventDefault ev)
  (let [ne (.-nativeEvent ev)
        page-x (.-pageX ne)
        page-y (.-pageY ne)
        [offset-x offset-y] (get-in @state-atm [:vertices id :position] [0 0])
        prev-id (get-in @state-atm [:draw :id])]
    
    (if (= id prev-id)
      (swap! state-atm dissoc :draw)
      (do
        (swap! state-atm update-in [:selected] dissoc :drag)
        (swap! state-atm update-in [:draw] assoc :id id)
        (swap! state-atm update-in [:draw] assoc :line [:line {:x1 (+ offset-x 30) 
                                                               :y1 (+ offset-y 30) 
                                                               :x2 page-x
                                                               :y2 page-y
                                                               :style {:stroke "rgb(255,0,0)"
                                                                       :stroke-width 2}}])))))

(defn moveover [ev state-atm id]
  (.stopPropagation ev) 
  (.preventDefault ev)
  (let [ne (-> ev .-nativeEvent)
        class-tag (-> ne .-srcElement .-className .-baseVal)]
    (cond (= class-tag "circle")
          (swap! state-atm update-in [:title-hover] assoc :type id)
          (or (= class-tag "l-circle") (= class-tag "circle-panel"))
          (swap! state-atm update-in [:vertex-hover] assoc :type id))))

(defn moveout [ev state-atm id]
  (.stopPropagation ev) 
  (.preventDefault ev)
  (swap! state-atm dissoc :title-hover))

(defn delete [ev state-atm id]
  (.stopPropagation ev) 
  (.preventDefault ev)

  ;; delete vertex
  (swap! state-atm update-in [:vertices] dissoc id)  
  (swap! state-atm dissoc :selected)
  (swap! state-atm dissoc :draw)
  
  ;; delete edge
  (let [edges (reduce 
               (fn [r {:keys [from to] :as edge}]
                 (if (or (= from id) (= to id))
                   r
                   (conj r edge)))
               []
               (get @state-atm :edges))]
    (swap! state-atm assoc :edges edges)))

(defn vertex
  [state-atm & {:keys [id class x y s]
                :as   args}]
  (let [id (or id (str "rect-" (random-uuid)))
        class (if class (str class " l-rect") "l-rect")]
    (fn [state-atm & {:keys [id class x y s]
                      :or {id id class class}
                      :as args}]
      (let [[offset-x offset-y] (get-in @state-atm [:vertices id :position] [0 0])
            current? (= id (get-in @state-atm [:selected :current :id]))
            hover? (= id (get-in @state-atm [:vertex-hover :type]))
            type (get-in @state-atm [:title-hover :type])
            radius (* 2  15.91549430918954)]
        [:g (assoc args 
                      :id id :class class 
                      :onMouseDown #(on % state-atm id)
                      :onMouseUp #(off state-atm)
                      :onMouseOver #(moveover % state-atm id)
                      :onMouseOut #(moveout % state-atm nil)
                      :transform (str "translate(" offset-x "," offset-y ")")
                      :class (if current? "grp selected" "grp"))
         
         [:circle {:cx 30 :cy  30 :r (* 2 radius) :class "circle-panel" :shape-rendering "optimizeQuality"}]
         
         [:circle {:cx 30 :cy  30 :r radius :class "circle" :shape-rendering "optimizeQuality" 
                   :onMouseDown #(draw-on % state-atm id) 
                   :onMouseOver #(moveover % state-atm "connect") 
                   :onMouseOut #(moveout % state-atm nil) }]
         [:text {:class (if (and hover? (= type "connect")) "title selected" "title") 
                 :x 82 :y 18 :fill "black" :font-family "Verdana" :font-weight "lighter" :font-size "8" 
                 :transform "rotate(-18 80,18)"} "connect"]
         
         [:circle {:cx 30 :cy  30 :r radius :class "circle" :shape-rendering "optimizeQuality"
                   ;:onMouseDown #(draw-on % state-atm id) 
                   :onMouseOver #(moveover % state-atm "edit") 
                   :onMouseOut #(moveout % state-atm nil) }]
         [:text {:class (if (and hover? (= type "edit")) "title selected" "title") 
                 :x 63 :y -12 :fill "black" :font-family "Verdana" :font-weight "lighter" :font-size "8" 
                 :transform "rotate(-54 63,-12)"} "edit"]
         
         [:circle {:cx 30 :cy  30 :r radius :class "circle" :shape-rendering "optimizeQuality"
                   :onMouseDown #(delete % state-atm id)
                   :onMouseOver #(moveover % state-atm "delete") 
                   :onMouseOut #(moveout % state-atm nil) }]
         [:text {:class (if (and hover? (= type "delete")) "title selected" "title") 
                 :x 32 :y -24 :fill "black" :font-family "Verdana" :font-weight "lighter" :font-size "8" 
                 :transform "rotate(-90 32,-24)"} "delete"]

         [:circle {:cx 30 :cy  30 :r radius :class "circle" :shape-rendering "optimizeQuality"}]
         [:circle {:cx 30 :cy  30 :r radius :class "circle" :shape-rendering "optimizeQuality"}]
         [:circle {:cx 30 :cy  30 :r radius :class "l-circle" :shape-rendering "optimizeQuality"}]
         
         (let [idc (str "filter-" id)
               x (- radius 3)
               y 78]
           [:g
            [:defs
             [:filter {:id idc}
              [:feGaussianBlur {:in "SourceGraphic" :stdDeviation 1}]
              ]]
            [:text {:x x :y y :class "shadow" :filter (str "url(#" idc ")") } id]
            [:text {:x x :y y :class "label" } id]])]))))

(defn diagram [attrs state onchange]
  (let [id (str "diagram-" (random-uuid))
        state-atm (r/atom (assoc state :id id))]
    (fn [attrs state onchange]
      (let [_ (and onchange (onchange @state-atm))
            line (get-in @state-atm [:draw :line])
            edges (reduce
                   (fn [r {:keys [label from to]}]
                     (let [[x1 y1] (get-in @state-atm [:vertices from :position])
                           [x2 y2] (get-in @state-atm [:vertices to :position])]
                       (conj r [edge state-atm :x1 (+  x1 30) :y1 (+ y1 30) :x2 (+ x2 30) :y2 (+ y2 30) :label label])))
                   []
                   (:edges @state-atm))                                        
            set (reduce-kv
                 (fn [r k v]
                   (conj r [vertex state-atm :id k]))
                 []

                 (:vertices @state-atm))
            things (clojure.set/union (cons line set) edges)]
        (into  [:svg (assoc attrs :id id :onMouseMove #(move % state-atm) :onClick #(click % state-atm))]
               things)))))




