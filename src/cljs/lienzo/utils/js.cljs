(ns lienzo.utils.js)

(defn class-changer-generator [element]
  (fn [event]
    (let [type (-> event (.-type))])
    (.log js/console type)))

(defn add-event-listener [element stimulus fn]
  (.addEventListener element stimulus fn false)
  element)
