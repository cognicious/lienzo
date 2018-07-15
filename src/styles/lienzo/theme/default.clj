(ns lienzo.theme.default
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.color :refer [hsla hsl]]
            [garden.selectors :as gs]
            [garden.stylesheet :refer [at-keyframes]]
            [garden.units :refer [percent em rem px ms]]))

(def background-color (hsla 241 (percent 61) (percent 25) 0.05))
(def primary-color (hsl 214 (percent 90) (percent 52)))

(def font-family ["\"Roboto\"" 'Helvetica 'Arial 'sans-serif])
(def font-size-m 1)

(def button-size 2.25)
(def border-radius 0.25)


(defn gen-button []
  [:button
   {:background-color background-color
    :border-radius    (em 0.25)
    :border-width     (px 0)
    :box-sizing       'border-box
    :color            primary-color
    :cursor           'pointer
    :display          'inline-block
    :font-family      font-family
    :font-size        (rem font-size-m)
    :font-weight      500
    :height           (rem 2.25)
    :letter-spacing   (em 0.025)   
    :margin           [[(rem 0.25) 0]]
    :min-width        (rem (* button-size 2))
    :padding          [[0 (rem (+ (/ button-size 3) (/ border-radius 2)))]]
    :position         'relative
    :outline          'none
    :white-space      'nowrap
    :-moz-user-select 'none
    }
   [:&.lnz-over {:background-color (hsla 217 (percent 42) (percent 94) 0.85)}]
   [:&.lnz-active {:animation [['pulse-button (ms 400)]]
                   :animation-timing-function "cubic-bezier(0.4, 0, 0.2, 1)"}]])
(defn gen-button-disabled []
  [(gs/button (gs/attr :disabled)) {:animation [['none '!important]]
                              :color (hsla 214 (percent 50) (percent 22) 0.26)
                              :cursor 'not-allowed}])

(defn gen-button-keyframe []
  (at-keyframes 'pulse-button 
                [:0% {:box-shadow [[0 0 0 0 (hsla 217 (percent 42) (percent 94) 0.85)]]}]
                [:70% {:box-shadow [[0 0 0 (px 10) (hsla 217 (percent 42) (percent 94) 0.0)]]}]
                [:100% {:box-shadow [[0 0 0 0 (hsla 217 (percent 42) (percent 94) 0.0)]]}]))

(defstyles css
  [:.lnz 
   (gen-button)
   (gen-button-disabled)]
  (gen-button-keyframe))
