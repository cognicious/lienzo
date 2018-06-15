(ns lienzo.components.select
  (:require [lienzo.utils.js :as util-js] 
            [reagent.core :as r]))


(defn popup []
  [:div "hola"])

(defn select 
  ([] [select nil nil])
  ([args options]
   (r/create-class {:reagent-render (fn [args options]
                                      [:label.lnz
                                       [:span.field 
                                        [:input {:type "text"}]
                                        [:i.fas.fa-chevron-down {:on-click #(js/alert "click!")}]
                                        [:div  "hola"]]])
                    :component-did-mount (fn [component])})))
