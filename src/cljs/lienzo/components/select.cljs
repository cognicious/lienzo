(ns lienzo.components.select
  (:require [lienzo.utils.js :as util-js]
            [goog.dom.classlist :as gclasses]
            [lienzo.utils.js :as util-js] 
            [reagent.core :as r]))


(defn popup [options]
  (into [:ul#popup.lnz-popup.lnz-off] (map (fn [i] [:li i]) options)))

(defn select 
  ([] [select nil nil])
  ([args options]
   (r/create-class {:reagent-render (fn [args options]
                                      [:div 
                                       [:label.lnz {:style {:display "block"}}
                                        [:span.field 
                                         [:span {:style {:font-size "1rem" :display "inline-block" :width "200px" :height "18px" :padding "4px 0px 2px 0px" :margin "1px 0px -2px 0px"}} "1"]
                                        ;[:input {:type "text" :style {:display "inline-block" :width "200px" }}]
                                         [:i.fas.fa-chevron-down {:on-click (fn [e]
                                                                              (let [element (.getElementById js/document "popup")]
                                                                                (if (gclasses/contains element "lnz-off")
                                                                                  (do (gclasses/remove element "lnz-off")
                                                                                      (gclasses/add element "lnz-on"))
                                                                                  (do (gclasses/remove element "lnz-on")
                                                                                      (gclasses/add element "lnz-off")))))}]
                                         [popup [1 2 3 4 5]]
                                         ]]

                                       
                                       [:label.lnz {:style {:display "block"}}
                                        [:span.field 
                                         [:input {:type "text" :value "1" :style {:display "inline-block" :width "200px"}}]
                                         [:i.fas.fa-chevron-down {:on-click (fn [e]
                                                                              (let [element (.getElementById js/document "popup")]
                                                                                (if (gclasses/contains element "lnz-off")
                                                                                  (do (gclasses/remove element "lnz-off")
                                                                                      (gclasses/add element "lnz-on"))
                                                                                  (do (gclasses/remove element "lnz-on")
                                                                                      (gclasses/add element "lnz-off")))))}]
                                         [popup [1 2 3 4 5]]
                                         ]]])
                    :component-did-mount (fn [component])})))
