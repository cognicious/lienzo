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
  ([] [select nil {:placeholder "Select an option"}])
  ([args options]
   (let [options (or options {})
         id (random-uuid)
         input-id (str "input-" id)
         popup-id (str "popup-" id)]
     (r/create-class {:reagent-render (fn []
                                        [:div
                                         (let [{:keys [on-click]} options]
                                           [:label.lnz {:style {:display "block"}}
                                            [:span.field
                                             [tf/textfield (merge  {:icon :i.fas.fa-angle-down
                                                                    :id input-id
                                                                    :on-click (fn [e]
                                                                                (let [element (.getElementById js/document popup-id)]
                                                                                  (.log js/console element)
                                                                                  (util-js/class-toggle element "lnz-off" "lnz-on")
                                                                                  (on-click e)))}
                                                                   (dissoc options :icon :id :on-click))]
                                             [dropdown-popup {:id popup-id} [[:span "other"]
                                                                             [:span [:i.fas.fa-desktop {:style {:width "20px" :float "right"}}] "desktop"]
                                                                             [:span [:i.fas.fa-laptop {:style {:width "20px" :float "right"}}] "laptop"]
                                                                             [:span [:i.fas.fa-tablet-alt {:style {:width "20px" :float "right"}}] "tablet"]
                                                                             [:span [:i.fas.fa-mobile-alt {:style {:width "20px" :float "right"}}] "mobile"]] input-id]
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
