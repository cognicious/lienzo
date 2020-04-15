(ns lienzo.theme.default
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.color :refer [hsla hsl]]
            [garden.selectors :as gs]
            [garden.stylesheet :refer [at-keyframes]]
            [garden.units :refer [percent em rem px ms]]))

(def default-bg-color (hsla 217 (percent 5) (percent 94) 0.55))
(def bg-color (hsla 217 (percent 5) (percent 94) 0.85))
(def hover-bg-color (hsla 217 (percent 5) (percent 94) 0.75))
(def primary-color (hsl 214 (percent 90) (percent 52)))
(def disable-color (hsla 217 (percent 50) (percent 22) 0.25))

(def font-family ["\"Roboto\"" 'Helvetica 'Arial 'sans-serif])
(def font-icon   [['Font "Awesome\\" '5 'Free]])
(def font-size-m 0.9)
(def font-size-s 0.7)                 ;

(def button-size 2.25)
(def border-radius 0.25)
(def letter-spacing 0.025)

(defn gen-icons []
  (let [style {:margin [[(rem border-radius) (rem border-radius) 0 0]]}]
    [:i
     [:&.fa:before style]
     [:&.fab:before style]
     [:&.fal:before style]
     [:&.far:before style]
     [:&.fas:before style]
     [:&.wo-margin:before {:margin [[(rem border-radius) 0 0 0 '!important]]}]
     [:&:focus {:outline 'none :caret-color 'transparent}]
     [:&.checkbox:before ^:prefix
      {;:border-radius (percent 50)
       :box-sizing 'border-box
       :color primary-color
       :content "\"\\f0c8\""
       :cursor 'pointer
       :display 'inline-block
       :font-family font-icon
       :font-size (rem (* font-size-m 1.15))
       :font-style 'normal
       :font-variant 'normal
       :font-weight 500
       :letter-spacing (rem letter-spacing)
       :line-height (em 1.0)
       :margin [[(rem border-radius) (rem (* border-radius 2)) 0 0]]
       :outline 'none
       :position 'relative
       :text-rendering 'auto
       :user-select 'none}]
     [:&.radio:before ^:prefix
      {:border-radius (percent 50)
       :box-sizing 'border-box
       :color primary-color
       :content "\"\\f111\""
       :cursor 'pointer
       :display 'inline-block
       :font-family font-icon
       :font-size (rem (* font-size-m 1.15))
       :font-style 'normal
       :font-variant 'normal
       :font-weight 500
       :letter-spacing (rem letter-spacing)
       :line-height (em 1.0)
       :margin [[(rem border-radius) (rem (* border-radius 2)) 0 0]]
       :outline 'none
       :position 'relative
       :text-rendering 'auto
       :user-select 'none}]
     [:&.checkbox.lnz-checked:before {:content "\"\\f14a\"" :font-weight 900}]
     [:&.radio.lnz-checked:before {:content "\"\\f192\"" :font-weight 900}]     
     [:&.lnz-over:before {:font-weight 900 :transform "scale(1.1)"}]     
     [:&.lnz-active:before {:animation [['pulse-checkbox (ms 300)]]
                            :animation-timing-function "cubic-bezier(0.4, 0, 0.2, 1)"}]
     [:&.lnz-disabled:before {:animation [['none '!important]]
                              :color disable-color
                              :cursor 'not-allowed}]]))

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
    :letter-spacing   (rem letter-spacing)
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

(defn gen-label []
  [:label
   {:color       primary-color
    :font-family font-family
    :font-size   (rem font-size-m)
    :font-weight 300
    :letter-spacing (rem letter-spacing)
    :line-height    (em 1.0)
    :margin         [[0 (rem (* border-radius 6)) 0 0]]}
   [:&.lnz-disabled {:animation [['none '!important]]
                     :color disable-color
                     :cursor 'not-allowed}]
   [:&.textfield
    {:display 'inline-block
     :color primary-color
     :font-size (rem font-size-s)
     :font-weight 300
     :letter-spacing (rem letter-spacing)
     :line-height    (em 1.0)
     :min-height     (em 2.0)}
    [:&.lnz-over 
     [:span
      [:&.field {:animation [['pulse-textfield (ms 300)]]
                 :animation-timing-function "cubic-bezier(0.4, 0, 0.2, 1)"
                 :animation-fill-mode 'both}]]]
    [:&.lnz-focus
     [:span
      [:&.field {}]]]
    [(gs/input (gs/attr= :type :text)) {:outline 'none
                                        :border 'none
                                        :font-family font-family
                                        :font-size font-size-m
                                        :background-color 'transparent}]
    [:&.lnz-disabled:before {:animation [['none '!important]]
                             :color disable-color
                             :cursor 'not-allowed}]
    [:&.lnz-disabled {:animation [['none '!important]]
                      :color disable-color
                      :cursor 'not-allowed}
     [:span 
      [:&.name {:color disable-color}
       [:&.empty {:display 'none}]]
      [:&.field {:border [[(px 1) "solid" disable-color]]}]]]
    [:&.lnz-read-only {:animation [['none '!important]]
                       :cursor 'not-allowed}
     [:span
      [:&.name 
       [:&.empty {:display 'none}]]
      [:&.field {:background 'white
                 :border [[(px 1) "dashed" disable-color]]}]]]
    [:span 
     [:&.name {:display 'block}
      [:&.empty {:display 'none}]]
     [:&.field {:display 'inline-block
                :background (str "linear-gradient(90deg , hsla(214, 61%, 25%, 0.10), hsla(214, 61%, 25%, 0.05));")
                :background-size [[(percent 200) (percent 200)]]
                :padding [[(em (+ (/ button-size 6) (/ border-radius 2))) (em (+ (/ button-size 6) (/ border-radius 2)))]]
                :border-radius (em border-radius)
                :margin [[(rem border-radius) 0]]
                ;:min-height (em 2.2)
                }]
     [:span {:margin-left (rem border-radius)}]
     [:div {:color 'black
            :font-weight 300
            :margin-left (rem (* border-radius 8))}]
     [:i {:font-size font-size-m}]]]])

(defn gen-checkbox []
  [(gs/input (gs/attr= :type :checkbox))
   {:display 'none}])

(defn gen-radio []
  [(gs/input (gs/attr= :type :radio))
   {:display 'none}])

(defn gen-checkbox-keyframe []
  (at-keyframes 'pulse-checkbox
                [:0% ^:prefix {:box-shadow [[0 0 0 0 default-bg-color]] 
                               :transform "scale(0.85)"}]
                [:70% ^:prefix {:box-shadow [[0 0 0 (rem (/ button-size 4)) (hsla 0 (percent 0) (percent 94) 0.1)]]
                                :transform "scale(0.90)"}]
                [:100% ^:prefix {:box-shadow [[0 0 0 0 (hsla 217 (percent 0) (percent 94) 0.0)]]
                                 :transform "scale(0.95)"}]))
(defn gen-radio-keyframe []
  (at-keyframes 'pulse-radio
                [:0% ^:prefix {:box-shadow [[0 0 0 0 default-bg-color]] 
                               :transform "scale(0.85)"}]
                [:70% ^:prefix {:box-shadow [[0 0 0 (rem (/ button-size 4)) (hsla 0 (percent 0) (percent 94) 0.1)]]
                                :transform "scale(0.90)"}]
                [:100% ^:prefix {:box-shadow [[0 0 0 0 (hsla 217 (percent 0) (percent 94) 0.0)]]
                                 :transform "scale(0.95)"}]))
(defn gen-textfield-keyframe []
  (at-keyframes 'pulse-textfield
                [:0% ^:prefix {:background-position [[(percent 0) (percent 0)]]}]
                [:100% ^:prefix {:background-position [[(percent -100) (percent 0)]]}]))

(defn gen-span-div []
  [:span
   [:div {:color 'black
          :margin-left (rem (* border-radius 6))}]])

(defn gen-dropdown []
  [:.lnz-dropdown {:display "none"
                   :position "absolute"
                   :background (str "linear-gradient(90deg , hsla(214, 61%, 25%, 0.10), hsla(214, 61%, 25%, 0.05));")
                   :min-width "160px"
                   :box-shadow "0px 2px 4px 0px rgba(0,0,0,0.2)"
                   :z-index 1
                   :color primary-color
                   :font-family font-family
                   :font-size (rem font-size-s)
                   :font-weight 300
                   :letter-spacing (rem letter-spacing)
                   :margin [[0 '!important]]}
   [:&.lnz-on {:display "block"
               :animation [['pulse-dropdown (ms 300)]]}]
   [:&.lnz-off {:display "none"}]
   [:ul {:margin 0 
         :list-style-type "none"
         :padding 0}]
   [:li {:padding [[(em (+ (/ button-size 6) (/ border-radius 2))) (em (+ (/ button-size 6) (/ border-radius 2)))]]}
    [:&:hover {:color primary-color}]]])

(defn gen-dropdown-keyframe []
  (at-keyframes 'pulse-dropdown
                [:0% ^:prefix {:opacity "0"}]
                [:100% ^:prefix {:opacity "1"}]))

(defn gen-popup []
  [:.lnz-popup])

(defstyles garden-css
  [:.lnz 
   (gen-button)
   (gen-button-disabled)
   (gen-checkbox)
   (gen-icons)
   (gen-label)
   (gen-radio)
   (gen-span-div)]
  (gen-button-keyframe)
  (gen-checkbox-keyframe)
  (gen-radio-keyframe)
  (gen-textfield-keyframe)
  (gen-dropdown)
  (gen-dropdown-keyframe)
  (gen-popup))
