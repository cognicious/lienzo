(ns lienzo.utils.js
  (:require [goog.events :as gevents]
            [goog.dom :as gdom]
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

(defn class-push [element class-one]
  (if (not (gclasses/contains element class-one))
    (gclasses/add element class-one))
  element)

(defn class-pop [element class-one]
  (if (gclasses/contains element class-one)
    (gclasses/remove element class-one))
  element)

(defn class-toggle [element class-one class-two]
  (if (gclasses/contains element class-one)
    (do (gclasses/remove element class-one)
        (gclasses/add element class-two))
    (do (gclasses/remove element class-two)
        (gclasses/add element class-one)))
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

(defn element->data [element]
  (let [child-nodes (.-childNodes element)
        child-nodes-length (.-length child-nodes)
        child-data (.-data (aget child-nodes (dec child-nodes-length)))]
    (if (undefined? child-data)
      (element->data (aget child-nodes (dec child-nodes-length)))
      child-data)))

(defn insert-sibling-before [id [tag {:keys [on-click] :as opts} inner]]
  (.log js/console (pr-str {:id id :on-click on-click}))
  (let [anchor (gdom/getElement id)
        selection (gdom/createDom (name tag) (clj->js opts) inner)
        _ (add-event-listener selection "click" on-click)]
    (gdom/insertSiblingBefore selection anchor)))

(defn create [])
