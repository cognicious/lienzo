(ns lienzo.radio-test
  (:require [cljs.test :refer-macros [is testing async use-fixtures]]
            [cljs-react-test.utils :as tu]
            [devcards.core :refer-macros [deftest defcard defcard-rg reagent]]
            [reagent.core :as r]
            [lienzo.components.button :refer [button]]
            [lienzo.components.checkbox :refer [checkbox]]
            [lienzo.components.radio :refer [radio]]))

(defcard
  (str "# Radio \n"
       "This namespace defines some functions about radio component\n\n"
       "A Radio is a `<label>` and contains an icon `<i>`, a real radio `<input>` and optionally a `<span>` or a simple text."))

(defcard-rg card-radio-arity-zero
  "
  ## Arity 0 puts creates a simple radio
  ```
  [radio]
  ```
  produces in hiccup:
  ```
  [:label.lnz {:for \"random-uuid\"}                ;; Label wrapper
    [:i.lnz {:tab-index 0}]                       ;; Stylized Pseudo INPUT
    [:input {:id \"random-uuid\" :type \"radio\"}] ;; Real INPUT 
    \"\"]
     
  "
  [radio])

(deftest test-radio-arity-zero
  (let [label (-> js/document
                  (.getElementsByClassName "lnz")
                  (aget 0))
        pseudo-input (-> label .-firstChild)
        input (-> pseudo-input .-nextSibling)]
    (testing "Wrapper is a `<label>`?"
      (is (= (-> label .-tagName) "LABEL")))
    (testing "First child of label is an `<i>`?"
      (is (= (-> pseudo-input .-tagName) "I")))
    (testing "Real input is an `<input>`?" 
      (is (= (-> input .-tagName) "INPUT")))
    (testing "And input is of type `radio`" 
      (is (= (-> input .-type) "radio")))
    (testing "`<label>` contains '' as textContent?"
      (is (= (-> label .-textContent) "")))))

(defcard-rg card-radio-arity-one-text
  "
  ## Arity 1 puts a simple text
  ```
  [radio \"Radio Arity One\"]
  ```
  "
  [radio "Radio Arity One"])

(deftest test-radio-arity-one-text
  (let [label (-> js/document
                  (.getElementsByClassName "lnz")
                  (aget 2))
        pseudo-input (-> label .-firstChild)
        input (-> pseudo-input .-nextSibling)]
    (testing "Wrapper is a `<label>`?"
      (is (= (-> label .-tagName) "LABEL")))
    (testing "First child of label is an `<i>`?"
      (is (= (-> pseudo-input .-tagName) "I")))
    (testing "Real input is an `<input>`?" 
      (is (= (-> input .-tagName) "INPUT")))
    (testing "And input is of type `radio`" 
      (is (= (-> input .-type) "radio")))
    (testing "`<label>` contains 'Radio Arity One' as textContent?"
      (is (= (-> label .-textContent) "Radio Arity One")))))

(defcard-rg card-radio-arity-one-attrs
  "
  ## Or attributes
  ```
  [radio {:defaultChecked true}}]
  ```
  "
  [radio {:defaultChecked true}])

(deftest test-radio-arity-one-attrs
  (let [label (-> js/document
                  (.getElementsByClassName "lnz")
                  (aget 4))
        pseudo-input (-> label .-firstChild)
        input (-> pseudo-input .-nextSibling)]
    (testing "Wrapper is a `<label>`?"
      (is (= (-> label .-tagName) "LABEL")))
    (testing "First child of label is an `<i>`?"
      (is (= (-> pseudo-input .-tagName) "I")))
    (testing "Real input is an `<input>`?" 
      (is (= (-> input .-tagName) "INPUT")))
    (testing "And input is of type `radio`" 
      (is (= (-> input .-type) "radio")))
    (testing "`<label>` contains '' as textContent?"
      (is (= (-> label .-textContent) "")))
    (testing "`<input>` are checked?"
      (is (-> input .-checked)))))

(defcard-rg card-radio-arity-two
  "
  ## Arity 2 receives attributes map and text radio

  ```
  [radio {:on-click #(js/alert \"oh!\")} \"Radio Arity Two\"]
  ```
  "
  [radio {:on-click #(js/alert "oh!")} "Radio Arity Two"])

(deftest test-radio-arity-two
  (let [label (-> js/document
                  (.getElementsByClassName "lnz")
                  (aget 6))
        pseudo-input (-> label .-firstChild)
        input (-> pseudo-input .-nextSibling)]
    (testing "Wrapper is a `<label>`?"
      (is (= (-> label .-tagName) "LABEL")))
    (testing "First child of label is an `<i>`?"
      (is (= (-> pseudo-input .-tagName) "I")))
    (testing "Real input is an `<input>`?" 
      (is (= (-> input .-tagName) "INPUT")))
    (testing "And input is of type `radio`" 
      (is (= (-> input .-type) "radio")))
    (testing "`<label>` contains 'Radio Arity Two' as textContent?"
      (is (= (-> label .-textContent) "Radio Arity Two")))))

(defcard-rg card-radio-disabled
  "
  ## Testing a disable radio, see cursor and animation!
  ```
  [radio {:disabled \"disabled\"} \"Radio Disabled\"]
  ```
  "
  [radio {:disabled "disabled"} "Radio Disabled"])

(deftest test-radio-disabled
  (let [label (-> js/document
                  (.getElementsByClassName "lnz")
                  (aget 8))
        pseudo-input (-> label .-firstChild)
        input (-> pseudo-input .-nextSibling)]
    (testing "Wrapper is a `<label>`?"
      (is (= (-> label .-tagName) "LABEL")))
    (testing "First child of label is an `<i>`?"
      (is (= (-> pseudo-input .-tagName) "I")))
    (testing "Real input is an `<input>`?" 
      (is (= (-> input .-tagName) "INPUT")))
    (testing "And input is of type `radio`" 
      (is (= (-> input .-type) "radio")))
    (testing "`<label>` contains 'Radio Disabled' as textContent?"
      (is (= (-> label .-textContent) "Radio Disabled")))
    (testing "`<input>` are disabled?"
      (is (-> input .-disabled)))))

(defcard-rg card-radio-icon-without-text
  "
  ## Icon special attribute to add an icon (FontAwesome5)
  ```
  [radio {:icon :i.fas.fa-cloud}]
  ```
  produces in hiccup:
  ```
  [:label.lnz {:for \"random-uuid\"}                ;; Label wrapper
    [:i.lnz {:tab-index 0}]                       ;; Stylized Pseudo INPUT
    [:input {:id \"random-uuid\" :type \"radio\"}] ;; Real INPUT 
    [:span [:icon :i.fas.fa-cloud]]]              ;; Icon!
  "
  [radio {:icon :i.fas.fa-cloud}])

(deftest test-radio-icon-without-text
  (let [label (-> js/document
                  (.getElementsByClassName "lnz")
                  (aget 10))
        pseudo-input (-> label .-firstChild)
        input (-> pseudo-input .-nextSibling)
        span  (-> input .-nextSibling)
        icon  (-> span .-firstChild)]
    (testing "Wrapper is a `<label>`?"
      (is (= (-> label .-tagName) "LABEL")))
    (testing "First child of label is an `<i>`?"
      (is (= (-> pseudo-input .-tagName) "I")))
    (testing "Real input is an `<input>`?" 
      (is (= (-> input .-tagName) "INPUT")))
    (testing "And input is of type `radio`" 
      (is (= (-> input .-type) "radio")))
    (testing "`<label>` contains `<span>` as last child"
      (is (= (-> span .-tagName) "SPAN")))
    (testing "`<span>` contains an `<i>`"
      (is (= (-> icon .-tagName) "I")))
    (testing "`<label>` contains '' as textContent?"
      (is (= (-> label .-textContent) "")))))


(defcard-rg card-radio-icon-with-text
  "
  ## And with text
  ```
  [radio {:icon :i.fab.fa-react}]
  ```
  "
  [radio {:icon :i.fab.fa-react} "React!"])

(deftest test-radio-icon-with-text
  (let [label (-> js/document
                  (.getElementsByClassName "lnz")
                  (aget 12))
        pseudo-input (-> label .-firstChild)
        input (-> pseudo-input .-nextSibling)
        span  (-> input .-nextSibling)
        icon  (-> span .-firstChild)]
    (testing "Wrapper is a `<label>`?"
      (is (= (-> label .-tagName) "LABEL")))
    (testing "First child of label is an `<i>`?"
      (is (= (-> pseudo-input .-tagName) "I")))
    (testing "Real input is an `<input>`?" 
      (is (= (-> input .-tagName) "INPUT")))
    (testing "And input is of type `radio`" 
      (is (= (-> input .-type) "radio")))
    (testing "`<label>` contains `<span>` as last child"
      (is (= (-> span .-tagName) "SPAN")))
    (testing "`<span>` contains an `<i>`"
      (is (= (-> icon .-tagName) "I")))
    (testing "`<label>` contains 'cloud' as textContent?"
      (is (= (-> label .-textContent) "React!")))))

(defcard-rg card-radio-icon-with-text-and-description
  "
  ## With icon, text and description
  ```
  [:div
   [:div 
    [radio {:name \"framework\" icon :i.fab.fa-react} 
     [:span \"React\" [:div [:small \"In computing, React is a JavaScript library for building user interfaces. It is maintained by Facebook, Instagram and a community of individual developers and corporations.\"]]]]]
   [:div
    [radio {:name \"framework\" icon :i.fab.fa-angular}
     [:span \"Angular\" [:div [:small \"Angular is a TypeScript-based open-source front-end web application platform led by the Angular Team at Google and by a community of individuals and corporations.\"]]]]]]
  ```
  "
  [:div
   [:div 
    [radio {:name "framework" :icon :i.fab.fa-react} 
     [:span "React" [:div [:small "In computing, React is a JavaScript library for building user interfaces. It is maintained by Facebook, Instagram and a community of individual developers and corporations."]]]]]
   [:div
    [radio {:name "framework" :icon :i.fab.fa-angular}
     [:span "Angular" [:div [:small "Angular is a TypeScript-based open-source front-end web application platform led by the Angular Team at Google and by a community of individuals and corporations. Angular is a complete rewrite from the same team that built AngularJS."]]]]]])

(deftest test-radio-icon-with-text-and-description
  (let [label (-> js/document
                  (.getElementsByClassName "lnz")
                  (aget 12))
        pseudo-input (-> label .-firstChild)
        input (-> pseudo-input .-nextSibling)
        span  (-> input .-nextSibling)
        icon  (-> span .-firstChild)]
    (testing "Wrapper is a `<label>`?"
      (is (= (-> label .-tagName) "LABEL")))
    (testing "First child of label is an `<i>`?"
      (is (= (-> pseudo-input .-tagName) "I")))
    (testing "Real input is an `<input>`?" 
      (is (= (-> input .-tagName) "INPUT")))
    (testing "And input is of type `radio`" 
      (is (= (-> input .-type) "radio")))
    (testing "`<label>` contains `<span>` as last child"
      (is (= (-> span .-tagName) "SPAN")))
    (testing "`<span>` contains an `<i>`"
      (is (= (-> icon .-tagName) "I")))
    (testing "`<label>` contains 'cloud' as textContent?"
      (is (= (-> label .-textContent) "React!")))))

