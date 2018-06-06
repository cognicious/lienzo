(ns lienzo.components.textfield
  (:require [goog.events :as gevents]
            [goog.dom.classlist :as gclasses]
            [lienzo.utils.js :as util-js] 
            [reagent.core :as r]))


(defn mount
  [component]
  (let [element (r/dom-node component)
        input (-> element .-lastChild .-lastChild)]
    (.log js/console input)
    (-> element
        (util-js/add-event-listener "keydown" (fn [e]
                                                (let [key-code (-> e .-keyCode)]
                                                  (if (or (= key-code 32))
                                                    (gclasses/add element (util-js/type->class "active"))))))
        (util-js/add-event-listener "keyup" (fn [e]
                                              (let [key-code (-> e .-keyCode)]
                                                (if (or  (= key-code 32))
                                                  (gclasses/remove element (util-js/type->class "active"))))))
        (util-js/event-add-remove "active" "mousedown" "mouseup")
        (util-js/event-add-remove "active" "touchstart" "touchend")
        (util-js/event-add-remove "over" "mouseover" "mouseout")
        (util-js/event-add-remove "over" "focusin" "focusout"))))

(defn textfield
  "Textfield"
  ([]
   [textfield {} ""])
  ([text]
   (if (map? text)
     [textfield text nil]
     [textfield {} text]))
  ([args text]
   (r/create-class {:reagent-render (fn [args text]
                                      (let [id (get args :id (random-uuid))]
                                        [:label.lnz  {:for id}
                                         [:span.name text]
                                         [:span.field [:input (merge args {:type "text" :id id})]]]))
                    :component-did-mount mount})))
