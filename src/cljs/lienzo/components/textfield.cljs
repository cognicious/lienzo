(ns lienzo.components.textfield
  (:require [goog.events :as gevents]
            [goog.dom.classlist :as gclasses]
            [lienzo.utils.js :as util-js] 
            [reagent.core :as r]))


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
                    :component-did-mount (fn [comp]
                                           )})))
