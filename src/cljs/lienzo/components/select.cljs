(ns lienzo.components.select
  (:require [lienzo.utils.js :as util-js] 
            [reagent.core :as r]))


(defn input-box []
  (r/create-class {:reagent-render (fn []
                                     [:button "oko"])
                   :component-did-mount (fn [component]
                                          (let [element (r/dom-node component)]
                                            (-> element
                                                ;(util-js/event-add-remove "selected" "focusin" "focusout")
                                                (util-js/event-add-remove "active" "mousedown" "mouseup"))))}))

(defn pseudo-menu [& attrs]
  [:div (first attrs)
   [:ul
    [:li [:span "one"]]
    [:li [:span "two"]]
    [:li [:span "three"]]]])

(defn input-wrapper []
  [:div {:style {:border "1px solid gray"}}
   [input-box {:on-focus (fn [e] (-> js/document
                                     (.getElementById "zzz")
                                     (.setAttribute "style" "display: block")))
               :on-blur (fn [e] (-> js/document
                                     (.getElementById "zzz")
                                     (.setAttribute "style" "display: none")))}]
   [pseudo-menu {:id "zzz" :style {:display "none"}}]])


(defn mdl-select []
  [:div {:class "mdl-textfield mdl-js-textfield getmdl-select"}
   [:input {:type "text" :class "mdl-textfield__input" :id "sample1" :read-only true}]
   [:input {:type "hidden" :name "sample1"}]
   [:label {:for "sample1" :class "mdl-textfield__label"} "Country"]
   [:ul {:for "sample1" :class "mdl-menu mdl-menu--bottom-left mdl-js-menu"}
    [:li {:class "mdl-menu__item" :data-val "DEU"} "Germany"]
    [:li {:class "mdl-menu__item" :data-val "BLR"} "Belarus"]
    [:li {:class "mdl-menu__item" :data-val "RUS"} "Russia"]]])
