(ns lienzo.components.checkbox
  (:require [goog.dom.classlist :as gclasses]
            [lienzo.utils.js :as util-js] 
            [reagent.core :as r]))

(defn checkbox
  "checkbox"
  ([]
   [checkbox {} ""])
  ([text]
   (if (map? text)
     [checkbox text nil]
     [checkbox {} text]))
  ([args text]
   (r/create-class {:reagent-render (fn [args text]
                                      (let [id (random-uuid)]
                                        (if-let [icon (:icon args)]
                                          [:label.lnz {:for id}
                                           [:input (merge {:id id :type "checkbox"} args)] 
                                           [:span [icon] (if text [:span text])]]
                                          [:label.lnz {:for id}
                                           [:input (merge {:id id :type "checkbox"} args)] text])))
                    :component-did-mount (fn [component]
                                           (let [element (r/dom-node component)]
                                             (-> element
                                                 (util-js/add-event-listener "keydown" (fn [e]
                                                                                         (let [key-code (-> e .-keyCode)]
                                                                                           (if (or (= key-code 13) (= key-code 32))
                                                                                             (gclasses/add element (util-js/type->class "active"))))))
                                                 (util-js/add-event-listener "keyup" (fn [e]
                                                                                       (let [key-code (-> e .-keyCode)]
                                                                                         (if (or (= key-code 13) (= key-code 32))
                                                                                           (gclasses/remove element (util-js/type->class "active"))))))
                                                 (util-js/event-add-remove "active" "mousedown" "mouseup")
                                                 (util-js/event-add-remove "active" "touchstart" "touchend")
                                                 (util-js/event-add-remove "over" "mouseover" "mouseout")
                                                 (util-js/event-add-remove "over" "focusin" "focusout"))))})))
