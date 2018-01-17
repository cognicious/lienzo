(ns lienzo.components.diagram
  (:require [reagent.core :as r]
            ))

;; Example state
{:id "diagram-id"
 
 :selected {:drag {:id "id" :last [0 0]}
            :current {:id "id"}}
 :draw {}}

(defn edge [state-atm & {:keys [id class x1 y1 x2 y2 s]
                            :as   args}]
  (fn [state-atm & {:keys [id class x1 y1 x2 y2 s]
                    :as   args}]
    (let [h (- y2 y1)                          ;; height        
          b (- x2 x1)                          ;; base
          m (/ (- y2 y1) (- x2 x1))            ;; slope 
          d (/ (* (Math/atan m) 180) Math/PI)  ;; rads to deg
          o (if (< b 0) 180 0)                 ;; orientation
          p (Math/sqrt (+ (Math/pow h 2) (Math/pow b 2))) ;; hypotenuse
          id-pat (str "pat-" (random-uuid))
          _ (.log js/console (str "(- y2 y1) = -(" y2 " " y1 ") = " (- y2 y1) ))
          _ (.log js/console (str "(- x2 x1) = -(" x2 " " x1 ") = " (- x2 x1) ))
          _ (.log js/console (str  "m>> " m " g(rad)> " (Math/atan m) " g(deg)> " (/  (* (Math/atan m) 180) Math/PI)))]
      [:g
       [:defs
        [:pattern {:id id-pat 
                   :patternUnits "userSpaceOnUse" 
                   :x 0 
                   :y (+ y1 5) ;; tricky background offset
                   :width 14 
                   :height 30}
         [:rect {:x 0 :y 0 :width 14 :height 40 :style {:fill "rgba(128,128,128,0.5)"}}]
         [:line {:x1 5 :y1 5 :x2 13 :y2 13 :style {:stroke "#eee" :stroke-width "1"} }]
         [:line {:x1 13 :y1 13 :x2 5 :y2 20 :style {:stroke "rgba(255,255,255,0.5)" :stroke-width "1"} }]]]
       [:rect {:x x1
               :y y1
               :width p
               :height 34               
               :transform (str  "rotate(" (+ d o) " " x1 "," y1 ")")
               :style {:fill (str "url(#" id-pat ")")}}]
       (let [x  (+ x1 (/ b 2) (if (< h 0) 13 -13))
             y  (+ y1 (/ h 2) (if (< b 0) -13 13))
             idc (str "connector-" id-pat)]         
         [:g
          [:defs
           [:filter {:id idc}
            [:feGaussianBlur {:in "SourceGraphic" :stdDeviation 1}]
            ]]
          [:text {:x x :y y :fill "white" :text-anchor "middle" :alignment-baseline "central" :font-family "Verdana" :font-size "9" :filter (str "url(#" idc ")") :transform (str  "rotate(" (+ d 0) " " x "," y ")")} "default"]
          [:text {:x x :y y :fill "black" :text-anchor "middle" :alignment-baseline "central" :font-family "Verdana" :font-size "9" :transform (str  "rotate(" (+ d 0) " " x "," y ")")} "default"]])])))

(defn on 
  "Takes ev and register "
  [ev state-atm id]
  (let [ne (.-nativeEvent ev)
        page-x (.-pageX ne)
        page-y (.-pageY ne)]
    
    (swap! state-atm update-in [:selected] assoc :drag {:id id :last [page-x page-y]}
                                                 :current {:id id})
    (if-let [line (get-in @state-atm [:draw :line])]
      (let [prev-id (get-in @state-atm [:draw :id])
            [p-offset-x  p-offset-y] (get-in @state-atm [:nodes prev-id :position] [0 0])
            [offset-x  offset-y] (get-in @state-atm [:nodes id :position] [0 0])
            my-edge [edge state-atm 
                     :x1 (+ p-offset-x 30) 
                     :y1 (+ p-offset-y 30)
                     :x2 (+ offset-x 30)
                     :y2 (+ offset-y 30)]]
        (swap! state-atm update-in [:lines] conj my-edge)
        
        (swap! state-atm dissoc :draw)))

    ;(.log js/console (str "on " @state-atm))
    ))

(defn off [state-atm]
  ;(swap! state-atm update-in [:selected :drag] #(get-in @state-atm [:selected :previous]))
  (swap! state-atm update-in [:selected] dissoc :drag)
  ;(.log js/console (str "off " @state-atm))
  )

(defn move [ev state-atm]
  (let [ne (.-nativeEvent ev)        
        page-x (.-pageX ne)
        page-y (.-pageY ne)]
                                        
    ;; dragging 
    (if-let [drag (get-in @state-atm [:selected :drag])] 
      (let [{:keys [id last]} drag
            [last-x last-y] last
            [offset-x offset-y] (get-in @state-atm [:nodes id :position] [0 0])]
        (swap! state-atm assoc-in [:nodes id :position] [(- (+ offset-x page-x) last-x)
                                                  (- (+ offset-y page-y) last-y)])
        (swap! state-atm update-in [:selected] assoc :drag {:id id :last [page-x page-y]})))

    ;; drawing
    (if-let [line (get-in @state-atm [:draw :line])]
      (let [id (get-in @state-atm [:draw :id])
            [offset-x offset-y] (get-in @state-atm [:nodes id :position] [0 0])]
        (swap! state-atm update-in [:draw] assoc :line [edge state-atm
                                                        :x1 (+ offset-x 30) 
                                                        :y1 (+ offset-y 30)
                                                        :x2 page-x
                                                        :y2 page-y
                                                        #_{
                                                         :style {:stroke "rgb(0,0,255)"
                                                                       :stroke-width 2}}])))
    ))

(defn click [ev state-atm]
  (let [ne (-> ev .-nativeEvent)
        source-tag (-> ne .-srcElement .-nodeName)
        page-x (.-pageX ne)
        page-y (.-pageY ne)
        ;_ (.log js/console ne)
        ]
    (cond (= source-tag "svg")
          (do (swap! state-atm update-in [:selected] dissoc :current)
              (swap! state-atm dissoc :draw))
          ;(= source-tag "circle")
          )))

(defn draw-on [ev state-atm id]
  (.stopPropagation ev) 
  (.preventDefault ev)
  (let [ne (.-nativeEvent ev)
        page-x (.-pageX ne)
        page-y (.-pageY ne)
        [offset-x offset-y] (get-in @state-atm [:nodes id :position] [0 0])
        prev-id (get-in @state-atm [:draw :id])]
    ;(.log js/console (str {:id id :prev-id prev-id}))
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
                                                                       :stroke-width 2}}])))
    ;(.log js/console (str "draw-on " @state-atm))
    ))

(defn node
  [state-atm & {:keys [id class x y s]
                :as   args}]
  (let [id (or id (str "rect-" (random-uuid)))
        class (if class (str class " l-rect") "l-rect")]
    (fn [state-atm & {:keys [id class x y s]
                      :or {id id class class}
                      :as args}]
      (let [;_ (.log js/console (str {:id id}))
            [offset-x offset-y] (get-in @state-atm [:nodes id :position] [0 0])
            ;_ (.log js/console (str {:id id :position [offset-x offset-y]}))
            current? (= id (get-in @state-atm [:selected :current :id]))            
            radius (* 2  15.91549430918954)
            ]
        [:g (assoc args 
                      :id id :class class 
                      :onMouseDown #(on % state-atm id)
                      :onMouseUp #(off state-atm)
                      ;:onMouseOver #(info % state-atm id)
                      ;:onMouseLeave #(off state-atm)
                      :transform (str "translate(" offset-x "," offset-y ")")
                      :class (if current? "grp selected" "grp"))
         
         [:circle {:cx 30 :cy  30 :r (* 2 radius) :class "circle-panel" :shape-rendering "optimizeQuality"}]
         [:circle {:cx 30 :cy  30 :r radius :class "circle" :shape-rendering "optimizeQuality" :onMouseDown #(draw-on % state-atm id)}
          [:title "connect"]]
         [:circle {:cx 30 :cy  30 :r radius :class "circle" :shape-rendering "optimizeQuality"}]
         [:circle {:cx 30 :cy  30 :r radius :class "circle" :shape-rendering "optimizeQuality"}]
         [:circle {:cx 30 :cy  30 :r radius :class "circle" :shape-rendering "optimizeQuality"}]
         [:circle {:cx 30 :cy  30 :r radius :class "circle" :shape-rendering "optimizeQuality"}]
         [:circle {:cx 30 :cy  30 :r radius :class "l-circle" :shape-rendering "optimizeQuality"}]
         
         (let [idc (str "connector-" id)]
           [:g
            [:defs
             [:filter {:id idc}
              [:feGaussianBlur {:in "SourceGraphic" :stdDeviation 1}]
              ]]
            [:text {:x (+ -2 (- 60 (min 60 (* 5 (count id))))) :y 78 
                    :textLength (min 60 (* 4.5 (count id)))
                    :fill "white"
                    :font-family "Verdana" :font-size "9" :filter (str "url(#" idc ")") :lengthAdjust "spacingAndGlyphs" } id]
            [:text {:x (+ -2 (- 60 (min 60 (* 5 (count id))))) :y 78 
                    :textLength (min 60 (* 4.5 (count id))) 
                    :font-family "Verdana" :font-size "9" :lengthAdjust "spacingAndGlyphs"} id]])
         
         ;[:circle {:cx 30 :cy  30 :r radius :class "circle-panel" :shape-rendering "optimizeQuality"}]
         ;[:circle {:cx 30 :cy  30 :r 8 :class "circle" :shape-rendering "optimizeQuality" :transform "translate(22.28,-22.28)"}]
         ;[:circle {:cx 30 :cy  30 :r 6 :class "circle" :shape-rendering "optimizeQuality" :transform "translate(-22.28,-22.28)"}]
         ;[:circle {:cx 30 :cy  30 :r 8 :class "circle" :shape-rendering "optimizeQuality" :transform "translate(0,-31.28)"}]
         ;[:circle {:cx 30 :cy  30 :r 6 :class "circle" :shape-rendering "optimizeQuality" :transform "translate(-31.28,0)"}]
         ;[:circle {:cx 30 :cy  30 :r 6 :class "circle" :shape-rendering "optimizeQuality" :transform "translate(0,31.28)"}]
         ;[:circle {:cx 30 :cy  30 :r 8 :class "circle" :shape-rendering "optimizeQuality" :transform "translate(31.28,0)"}]
         ;[:circle {:cx 30 :cy  30 :r 6 :class "circle" :shape-rendering "optimizeQuality" :transform "translate(22.28,22.28)"}]
         ;[:circle {:cx 30 :cy  30 :r 6 :class "circle" :shape-rendering "optimizeQuality" :transform "translate(-22.28,22.28)"}]

         ]))))



(defn diagram [attrs state]
  (let [id (str "diagram-" (random-uuid))
        state-atm (r/atom (assoc state :id id))]
    (fn [attrs state]
      (let [line (get-in @state-atm [:draw :line])
            lines (get-in @state-atm [:lines])
            ;_ (.log js/console (str (:nodes @state-atm)))
            set (reduce-kv
                 (fn [r k v]
                   ;(.log js/console (str "reduce: " [k v]))
                   (conj r [node state-atm :id k]))
                 []

                 (:nodes @state-atm))
            things (clojure.set/union lines (cons line set))
            ;_ (.log js/console (str "things:" things))
            ]
        (into  [:svg (assoc attrs :id id :onMouseMove #(move % state-atm) :onClick #(click % state-atm))]
               things)))))




