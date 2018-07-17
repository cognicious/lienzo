(ns lienzo.components.button
  (:require [goog.dom.classlist :as gclasses]
            [lienzo.utils.js :as util-js] 
            [reagent.core :as r]))

(defn render
  [args text]
  (if-let [icon (:icon args)]
    [:button args [:span [icon (if-not text {:class "wo-margin"})] text]]
    [:button args text]))

(defn mount
  [component]
  (let [element (r/dom-node component)]
    (-> element
        (util-js/add-event-listener "keydown" (fn [e]
                                                (let [key-code (.-keyCode e)]
                                                  (if (or (= key-code 13) (= key-code 32))
                                                    (gclasses/add element (util-js/type->class "active"))))))
        (util-js/add-event-listener "keyup" (fn [e]
                                              (let [key-code (.-keyCode e)]
                                                (if (or (= key-code 13) (= key-code 32))
                                                  (gclasses/remove element (util-js/type->class "active"))))))
        (util-js/event-add-remove "active" "mousedown" "mouseup")
        (util-js/event-add-remove "active" "touchstart" "touchend")
        (util-js/event-add-remove "over" "mouseover" "mouseout")
        (util-js/event-add-remove "over" "focusin" "focusout"))))

(defn button 
  ([]
   [button {} "Button"])
  ([text]
   (if (map? text)
     [button text nil]
     [button {} text]))
  ([args text]
   (r/create-class {:reagent-render render
                    :component-did-mount mount})))
