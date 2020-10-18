(ns lienzo.components.select
  (:require [lienzo.utils.js :as util-js]
            [goog.dom.classlist :as gclasses]
            [goog.style :as gstyle]
            [lienzo.utils.js :as util-js] 
            [lienzo.components.textfield :as tf]
            [reagent.core :as r]))


(defn dropdown-popup [args options-atm input-id]
  (fn [args options input-id]
    [:div.lnz-dropdown.lnz-off args
     (into [:ul.lnz-popup.lnz-off]
           (map (fn [i] 
                  (let [text (if (string? i) i (last (clj->js i)))]
                    [:li.lnz {:tab-index -1 :on-click (fn [_] (-> (.getElementById js/document input-id)
                                                    .-value
                                                    (set! text)))} i])) 
                @options-atm))]))

(defn select 
  ([] [select []])
  ([options] [select {} options])
  ([args options]
   (let [args (or args {})
         id (random-uuid)
         input-id (str "input-" id)
         popup-id (str "popup-" id)]
     (r/create-class {:reagent-render (fn []
                                        [:div
                                         (let [args (cond-> args
                                                      (and (= 0 (count options))
                                                           (nil? (:placeholder args)))
                                                      (assoc :placeholder  "No options")
                                                      (and (< 0 (count options))
                                                           (nil? (:placeholder args)))
                                                      (assoc :placeholder "Select options"))
                                               {:keys [on-click]} args
                                               options-atm (r/atom options)
                                               selected-atm (r/atom -1)]
                                           [:label.lnz {:style {:display "block"}}
                                            [:span.field
                                             [tf/textfield (merge  {:icon :i.fas.fa-angle-down
                                                                    :id input-id
                                                                    :auto-complete "off"
                                                                    
                                                                    :on-click (fn [e]
                                                                                (let [element (.getElementById js/document popup-id)]
                                                                                  (util-js/class-toggle element "lnz-off" "lnz-on")
                                                                                  (if (fn? on-click) (on-click e))))
                                                                    
                                                                    ;:on-change
                                                                    #_(fn [e]
                                                                      (.log js/console e)
                                                                      (let [element (.getElementById js/document popup-id)
                                                                            current (.-value (.getElementById js/document input-id))]
                                                                        (.log js/console "change!")
                                                                        (util-js/class-push element "lnz-on")
                                                                        (if (= 0 (count current))
                                                                          (reset! options-atm options)
                                                                          (swap! options-atm (partial filter (fn [i] (let [text (if (string? i) i (last (clj->js i)))]
                                                                                                                       (clojure.string/starts-with? text current)
                                                                                                                       )))))))
                                                                    :keyup (fn [e]
                                                                             (let[
                                                                                  element (.getElementById js/document popup-id)
                                                                                  current (.getElementById js/document input-id)
                                                                                  opt-nodes (-> element .-lastChild .-childNodes)
                                                                                  key-code (-> e .-keyCode)]
                                                                               (.log js/console key-code)
                                                                               (util-js/class-pop element "lnz-off")
                                                                               (util-js/class-push element "lnz-on")
                                                                               (if (and (< -1 @selected-atm)
                                                                                        (< 0 (.-length opt-nodes)))
                                                                                 (util-js/class-pop (aget opt-nodes @selected-atm) "lnz-selected"))
                                                                               (if-let [trigger-fn  (cond (= key-code 40) inc
                                                                                                          (= key-code 39) inc
                                                                                                          (= key-code 38) dec
                                                                                                          (= key-code 37) dec
                                                                                                          :default nil)]
                                                                                 (let [_ (swap! selected-atm (fn [l] (if (< -1 (trigger-fn l) (.-length opt-nodes)) (trigger-fn l) l)))
                                                                                       selected (aget opt-nodes @selected-atm)]
                                                                                   
                                                                                   (util-js/class-push selected "lnz-selected")
                                                                                   (-> current
                                                                                       .-value
                                                                                       (set! (.-textContent (.-lastChild selected)))))
                                                                                 (if (= key-code 13)
                                                                                   (-> element
                                                                                       (util-js/class-pop "lnz-on")
                                                                                       (util-js/class-push "lnz-off"))
                                                                                   (let [] ;(= 0 (count (.-value current)))
                                                                                     (reset! options-atm options)
                                                                                     (swap! options-atm (partial filter (fn [i] (let [text (if (string? i) i (last (clj->js i)))]
                                                                                                                                                   (clojure.string/starts-with? text (.-value current))
                                                                                                                                                   ))))))
                                                                                 )))}
                                                                   (dissoc args :icon :id :on-click))]
                                             #_[[:span "other"]
                                                [:span [:i.fas.fa-desktop {:style {:width "20px" :float "right"}}] "desktop"]
                                                [:span [:i.fas.fa-laptop {:style {:width "20px" :float "right"}}] "laptop"]
                                                [:span [:i.fas.fa-tablet-alt {:style {:width "20px" :float "right"}}] "tablet"]
                                                [:span [:i.fas.fa-mobile-alt {:style {:width "20px" :float "right"}}] "mobile"]]
                                             [dropdown-popup {:id popup-id} options-atm input-id]
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
