(ns lienzo.utils.js
  (:require [goog.events :as gevents]
            [goog.dom.classlist :as gclasses]))

(def prefix "lnz-")

(defn type->class [type]
  (str prefix type))

(defn class->type [class]
  (second (re-matches #"lnz-([a-z]+)" class)))

(defn class-change-fn [element]
  (fn [event]
    (let [type (-> event .-type)]
      (gclasses/add element (type->class type)))))

(defn add-event-listener [element stimulus fn]
  (gevents/listen element stimulus fn)
  element)

(defn event-add-remove
  ([element map]
   (let [[type-add vals-add] (first map)
         [type-rem vals-rem] (second map)
         type-add (-> type-add name)
         type-rem (-> type-rem name)
         vals-add (reduce #(conj %1 (type->class %2)) [] vals-add)
         vals-rem (reduce #(conj %1 (type->class %2)) [] vals-rem)]
     (-> element
         (add-event-listener type-add (fn [event]
                                        (gclasses/addAll element (clj->js vals-add))))
         (add-event-listener type-rem (fn [event]
                                        (gclasses/removeAll element (clj->js vals-rem)))))))
  ([element type-add type-rem]
   (-> element
       (add-event-listener type-add (fn [event]
                                      (gclasses/addRemove element (type->class type-rem) (type->class type-add))))
       (add-event-listener type-rem (fn [event]
                                      (gclasses/addRemove element (type->class type-add) (type->class type-rem))))))
  ([element class type-add type-rem]
   (event-add-remove element {(keyword type-add) [class]
                              (keyword type-rem) [class]})))
