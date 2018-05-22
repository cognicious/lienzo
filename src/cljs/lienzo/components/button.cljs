(ns lienzo.components.button
  (:require [lienzo.utils.js :as util-js] 
            [reagent.core :as r]))


(defn button 
  ([]
   [button {} "button"])
  ([text]
   [button {} text])
  ([args text]
   (r/create-class {:reagent-render (fn [args text]
                                      [:button.lnz args text])
                    :component-did-mount (fn [component]
                                           (let [element (r/dom-node component)]
                                             (-> element
                                                 (util-js/event-add-remove "active" "mousedown" "mouseup"))))})))
