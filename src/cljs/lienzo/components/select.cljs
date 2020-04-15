(ns lienzo.components.select
  (:require [lienzo.utils.js :as util-js]
            [goog.dom.classlist :as gclasses]
            [goog.style :as gstyle]
            [lienzo.utils.js :as util-js] 
            [lienzo.components.textfield :as tf]
            [reagent.core :as r]))


(defn dropdown-popup [args options input-id]
  [:div.lnz-dropdown args
   (into [:ul.lnz-popup.lnz-off]
         (map (fn [i] (let [text (last (clj->js i))] 
                        [:li.lnz {:on-click (fn [_] (-> (.getElementById js/document input-id)
                                                        .-value
                                                        (set! text)))} i])) options))])

(defn select 
  ([] [select nil nil])
  ([args options]
   (let [id (random-uuid)
         input-id (str "input-" id)
         popup-id (str "popup-" id)]
     (r/create-class {:reagent-render (fn [args options]
                                        [:div
                                         (let [
                                               debug-style {}; {:font-size "1rem" :display "inline-block" :width "200px" :height "18px" :padding "6px 0px 2px 0px" :margin "1px 0px -2px 0px"}
                                               ]
                                           [:label.lnz {:style {:display "block"}}
                                            [:span.field
                                             ;[:span {:style debug-style} [:span [:i.fas.fa-desktop] 1] ]
                                             [tf/textfield {:icon :i.fas.fa-angle-down 
                                                            :id input-id 
                                                            :style {:width 200} 
                                                            :on-click (fn [e]
                                                                        (let [element (.getElementById js/document popup-id)]
                                                                          (.log js/console element)
                                                                          (util-js/class-toggle element "lnz-off" "lnz-on")))}]
                                        ;[:input {:id input-id :type "text" :style {:display "inline-block" :width "200px"}}]
                                             ;[:i.fas.fa-chevron-down]
                                             [dropdown-popup {:id popup-id} [[:span [:i.fas.fa-desktop] "desktop"]
                                                                             [:span [:i.fas.fa-laptop] "laptop"]
                                                                             [:span [:i.fas.fa-tablet-alt] "tablet"]
                                                                             [:span [:i.fas.fa-mobile-alt] "mobile"]] input-id]
                                             ]])
                                         #_(let [id (random-uuid)
                                                 popup-id (str "popup-" id)]
                                             [:label.lnz {:style {:display "block"}}
                                              [:span.field
                                               [:input {:type "text" :style {:display "inline-block" :width "200px"}}]
                                               [:i.fas.fa-chevron-down {:on-click (fn [e]
                                                                                    (let [element (.getElementById js/document popup-id)]
                                                                                      (if (gclasses/contains element "lnz-off")
                                                                                        (do (gclasses/remove element "lnz-off")
                                                                                            (gclasses/add element "lnz-on"))
                                                                                        (do (gclasses/remove element "lnz-on")
                                                                                            (gclasses/add element "lnz-off")))))}]
                                               [popup {:id popup-id} [1 2 3 4 5]]
                                               ]])])
                      :component-did-mount (fn [component]
                                             (let [input-width (->> input-id
                                                                    (.getElementById js/document)
                                                                    (.-parentElement)
                                                                    (.-parentElement)
                                                                    (gstyle/getSize)
                                                                    .-width)
                                                   element (->> popup-id 
                                                                (.getElementById js/document))]
                                               (gstyle/setWidth element input-width)))}))))
