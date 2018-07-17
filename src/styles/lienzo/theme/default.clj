(ns lienzo.theme.default
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.color :refer [hsla hsl]]
            [garden.selectors :as gs]
            [garden.stylesheet :refer [at-keyframes]]
            [garden.units :refer [percent em rem px ms]]))

(def default-bg-color (hsla 217 (percent 5) (percent 94) 0.55))
(def hover-bg-color (hsla 217 (percent 5) (percent 94) 0.75))
(def primary-color (hsl 214 (percent 90) (percent 52)))
(def disable-color (hsla 217 (percent 50) (percent 22) 0.25))


(def font-family ["\"Roboto\"" 'Helvetica 'Arial 'sans-serif])
(def font-size-m 0.9)

(def button-size 2.25)
(def border-radius 0.25)

(defn icons []
  (let [style {:margin [[(rem border-radius) (rem border-radius) 0 0]]}]
    [:i
     [:&.fa:before style]
     [:&.fab:before style]
     [:&.fal:before style]
     [:&.far:before style]
     [:&.fas:before style]
     [:&.wo-margin:before {:margin [[(rem border-radius) 0 0 0 '!important]]}]]))

(defn gen-button []
  [:button
   ^:prefix
   {:background-color default-bg-color
    :border-radius    (em 0.25)
    :border-width     (px 0)
    :box-sizing       'border-box
    :color            primary-color
    :cursor           'pointer
    :display          'inline-block
    :font-family      font-family
    :font-size        (rem font-size-m)
    :font-weight      300
    :height           (rem 2.25)
    :letter-spacing   (em 0.025)   
    :margin           [[(rem 0.25) 0]]
    :min-width        (rem (* button-size 2))
    :padding          [[0 (rem (+ (/ button-size 3) (/ border-radius 2)))]]
    :position         'relative
    :outline          'none
    :white-space      'nowrap
    :user-select      'none}
   [:&.lnz-over {:background-color hover-bg-color}]
   [:&.lnz-active {:animation [['pulse-button (ms 300)]]
                   :animation-timing-function "cubic-bezier(0.4, 0, 0.2, 1)"}]])

(defn gen-button-disabled []
  [(gs/button (gs/attr :disabled)) {:animation [['none '!important]]
                                    :color disable-color
                                    :cursor 'not-allowed}])
(defn gen-button-keyframe []
  (at-keyframes 'pulse-button 
                [:0% ^:prefix {:box-shadow [[0 0 0 0 default-bg-color]]}]
                [:70% ^:prefix {:box-shadow [[0 0 0 (rem (/ button-size 4)) (hsla 0 (percent 0) (percent 94) 0.1)]]}]
                [:100% ^:prefix {:box-shadow [[0 0 0 0 (hsla 217 (percent 0) (percent 94) 0.0)]]}]))



(defstyles garden-css 
  [:.lnz 
   (icons)
   (gen-button)
   (gen-button-disabled)]
  (gen-button-keyframe))
