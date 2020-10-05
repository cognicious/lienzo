(ns lienzo.components.textfield
  (:require [goog.events :as gevents]
            [goog.dom.classlist :as gclasses]
            [lienzo.utils.js :as util-js] 
            [reagent.core :as r]))

(defn render [args text]
  (let [id (get args :id (random-uuid))
        disabled (get args :disabled)
        read-only (get args :read-only)
        label-key (keyword (cond-> "label.textfield"
                             disabled (str ".lnz-disabled")
                             read-only (str ".lnz-read-only")))]
    [label-key  {:for id}
     [:span.name (if (or (nil? text) (empty? text)) {:class "empty"} {}) text]
     [:span.field [:input (merge args {:type "text" :id id})]
      (if-let [icon (:icon args)]
        (let [on-click (:on-click args)]
          [icon {:on-click on-click}]))]]))

(defn mount
  [component]
  (let [element (r/dom-node component)
        input (-> element .-lastChild .-firstChild)
        icon (-> element .-lastChild .-firstChild .-nextSibling)
        active-fn (fn [e]
                    (if-not (gclasses/contains element (util-js/type->class "focus"))
                      (if (gclasses/contains element (util-js/type->class "active"))
                        (gclasses/remove element (util-js/type->class "active"))
                        (gclasses/add element (util-js/type->class "active")))))
        focusin-fn (fn [e]
                     (gclasses/add element (util-js/type->class "focus")))
        focusout-fn (fn [e]
                     (gclasses/remove element (util-js/type->class "focus")))
        prevent-fn (fn [e]
                     (.preventDefault e))]
    (if icon
      (util-js/add-event-listener icon "click" prevent-fn))

    (-> input 
        (util-js/add-event-listener "focusin" active-fn)
        (util-js/add-event-listener "focusin" focusin-fn)
        (util-js/add-event-listener "focusout" active-fn)
        (util-js/add-event-listener "focusout" focusout-fn))

    
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
        (util-js/event-add-remove "over" "focusin" "focusout")
        )))

(defn textfield
  "Textfield"
  ([]
   [textfield {} ""])
  ([text]
   (if (map? text)
     [textfield text nil]
     [textfield {} text]))
  ([args text]
   (r/create-class {:reagent-render render
                    :component-did-mount mount})))
