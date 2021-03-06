(ns lienzo.components.radio
  (:require [goog.events :as gevents]
            [goog.dom.classlist :as gclasses]
            [lienzo.utils.js :as util-js] 
            [reagent.core :as r]))



(defn render 
  "Render radio with args and text"
  [args text]
  (let [id (get args :id (random-uuid))
        disabled (get args :disabled)
        checked (get args :defaultChecked)
        
        label-key (if disabled :label.lnz-disabled :label)
        pseudo-key (keyword (cond-> "i.radio"
                             disabled (str ".lnz-disabled")
                             checked (str ".lnz-checked")))]
    (if-let [icon (:icon args)]
      [label-key {:for id}
       [pseudo-key {:tab-index 0}]
       [:input (merge {:id id :type "radio"} args)] 
       [:span [icon]] 
       text]
      [label-key {:for id}
       [pseudo-key {:tab-index 0}]
       [:input (merge {:id id :type "radio"} args)] 
       text])))

(defn mount
  "Adds Listeners for CSS FX"
  [component]
  (let [element (r/dom-node component)
        pseudo (-> element .-firstChild)
        input  (-> element .-firstChild .-nextSibling)
        check-fn  (fn [e]
                    (let [checked (util-js/type->class "checked")
                         ]
                      
                      (if (.-checked input) 
                        (let [siblings (array-seq (-> js/document (.getElementsByName (.-name input))))]
                          (reduce (fn [_ s] (gclasses/remove (-> s .-previousSibling) checked)) nil siblings)
                          (gclasses/add pseudo checked))
                        (gclasses/remove pseudo checked))))
        label-fn  (fn [e]                                                             
                    (if (gclasses/contains pseudo (util-js/type->class "active"))
                      (gclasses/remove pseudo (util-js/type->class "active"))
                      (gclasses/add pseudo (util-js/type->class "active"))))]
    
    (-> input
        (util-js/add-event-listener "change" check-fn))
    (-> pseudo
        (util-js/add-event-listener "keydown" (fn [e]
                                                (let [key-code (-> e .-keyCode)]
                                                  (.log js/console (gclasses/contains pseudo (util-js/type->class "active")))
                                                  (if (or (= key-code 13) (= key-code 32))
                                                    (if (gclasses/contains pseudo (util-js/type->class "active"))
                                                      (gclasses/remove pseudo (util-js/type->class "active"))
                                                      (do (gclasses/add pseudo (util-js/type->class "active"))
                                                          (set! (.-checked input) (not (.-checked input)))
                                                          (check-fn e)
                                                          (.preventDefault e)))))))
        (util-js/add-event-listener "keyup" (fn [e]
                                              (let [key-code (-> e .-keyCode)]
                                                (.log js/console (gclasses/contains pseudo (util-js/type->class "active")))
                                                (if (or (= key-code 13) (= key-code 32))
                                                  (if (gclasses/contains pseudo (util-js/type->class "active"))
                                                    (gclasses/remove pseudo (util-js/type->class "active"))
                                                    (do (gclasses/add pseudo (util-js/type->class "active"))
                                                        (set! (.-checked input) (not (.-checked input)))
                                                        (check-fn e)
                                                        (.preventDefault e)))))))
        (util-js/event-add-remove "active" "mousedown" "mouseup")
        (util-js/event-add-remove "active" "touchstart" "touchend")
        (util-js/event-add-remove "over" "mouseover" "mouseout")
        (util-js/event-add-remove "over" "focusin" "focusout"))
    (-> element 
        (util-js/add-event-listener "mousedown" label-fn)
        (util-js/add-event-listener "mouseup" label-fn)
        (util-js/add-event-listener "touchstart" label-fn)
        (util-js/add-event-listener "touchend" label-fn))))

(defn radio
  "Radio"
  ([]
   [radio {} ""])
  ([text]
   (if (map? text)
     [radio text nil]
     [radio {} text]))
  ([args text]
   (r/create-class {:reagent-render render
                    :component-did-mount mount})))
