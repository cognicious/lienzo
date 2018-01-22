(ns ^:figwheel-always lienzo.demo
  (:require [lienzo.core :as lienzo]
            [reagent.core :as r]
            [cljs.repl    :refer-macros [source]]
            ))

(enable-console-print!)

(defn app-view []
  (let []
    [lienzo/diagram 
     {:width "100vw" :height "100vh"}
     {:vertices {"ssh-tunnel" {:position [126 32]}
                 "send-telegram"  {:position [134 230]}
                 "wait-for-response" {:position [431 144]}
                 "okopoko" {:position [594 266]}}
      :edges #{{:label "default"
                :from "ssh-tunnel"
                :to "send-telegram"}
               {:label "default"
                :from "send-telegram"
                :to "wait-for-response"}}}
     (fn [state]
       ;(.log js/console (str state))
       )]))

(defn ^:export run []
  (r/render [app-view]
            (js/document.getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  ;; (run)
  (run))

(run)
