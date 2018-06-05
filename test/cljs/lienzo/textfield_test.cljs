(ns lienzo.textfield-test
  (:require [cljs.test :refer-macros [is testing async use-fixtures]]
            [cljs-react-test.utils :as tu]
            [devcards.core :refer-macros [deftest defcard defcard-rg reagent]]
            [reagent.core :as r]
            [lienzo.components.button :refer [button]]
            [lienzo.components.checkbox :refer [checkbox]]
            [lienzo.components.textfield :refer [textfield]]))

(defcard
  (str "# Textfield \n"
       "This namespace defines some functions about textfield component\n\n"
       "A Textfield is a `<label>` and contains an icon `<i>`, a real textfield `<input>` and optionally a `<span>` or a simple text."))

(defcard-rg card-textfield-arity-zero
  "
  ## Arity 0 puts creates a simple textfield
  ```
  [textfield]
  ```
  produces in hiccup:
  ```
  [:label.lnz {:for \"random-uuid\"}                ;; Label wrapper
    [:i.lnz {:tab-index 0}]                       ;; Stylized Pseudo INPUT
    [:input {:id \"random-uuid\" :type \"textfield\"}] ;; Real INPUT 
    \"\"]
     
  "
  [textfield])

(deftest test-textfield-arity-zero
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
    (testing "And input is of type `textfield`" 
      (is (= (-> input .-type) "textfield")))
    (testing "`<label>` contains '' as textContent?"
      (is (= (-> label .-textContent) "")))))

(defcard-rg card-textfield-arity-one-text
  "
  ## Arity 1 puts a simple text
  ```
  [textfield \"Textfield Arity One\"]
  ```
  "
  [textfield "Textfield Arity One"])

(deftest test-textfield-arity-one-text
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
    (testing "And input is of type `textfield`" 
      (is (= (-> input .-type) "textfield")))
    (testing "`<label>` contains 'Textfield Arity One' as textContent?"
      (is (= (-> label .-textContent) "Textfield Arity One")))))

(defcard-rg card-textfield-arity-one-attrs
  "
  ## Or attributes
  ```
  [textfield {:defaultChecked true}}]
  ```
  "
  [textfield {:defaultChecked true}])

(deftest test-textfield-arity-one-attrs
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
    (testing "And input is of type `textfield`" 
      (is (= (-> input .-type) "textfield")))
    (testing "`<label>` contains '' as textContent?"
      (is (= (-> label .-textContent) "")))
    (testing "`<input>` are checked?"
      (is (-> input .-checked)))))

(defcard-rg card-textfield-arity-two
  "
  ## Arity 2 receives attributes map and text textfield

  ```
  [textfield {:on-click #(js/alert \"oh!\")} \"Textfield Arity Two\"]
  ```
  "
  [textfield {:on-click #(js/alert "oh!")} "Textfield Arity Two"])

(deftest test-textfield-arity-two
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
    (testing "And input is of type `textfield`" 
      (is (= (-> input .-type) "textfield")))
    (testing "`<label>` contains 'Textfield Arity Two' as textContent?"
      (is (= (-> label .-textContent) "Textfield Arity Two")))))

(defcard-rg card-textfield-disabled
  "
  ## Testing a disable textfield, see cursor and animation!
  ```
  [textfield {:disabled \"disabled\"} \"Textfield Disabled\"]
  ```
  "
  [textfield {:disabled "disabled"} "Textfield Disabled"])

(deftest test-textfield-disabled
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
    (testing "And input is of type `textfield`" 
      (is (= (-> input .-type) "textfield")))
    (testing "`<label>` contains 'Textfield Disabled' as textContent?"
      (is (= (-> label .-textContent) "Textfield Disabled")))
    (testing "`<input>` are disabled?"
      (is (-> input .-disabled)))))

(defcard-rg card-textfield-icon-without-text
  "
  ## Icon special attribute to add an icon (FontAwesome5)
  ```
  [textfield {:icon :i.fas.fa-cloud}]
  ```
  produces in hiccup:
  ```
  [:label.lnz {:for \"random-uuid\"}                ;; Label wrapper
    [:i.lnz {:tab-index 0}]                       ;; Stylized Pseudo INPUT
    [:input {:id \"random-uuid\" :type \"textfield\"}] ;; Real INPUT 
    [:span [:icon :i.fas.fa-cloud]]]              ;; Icon!
  "
  [textfield {:icon :i.fas.fa-cloud}])

(deftest test-textfield-icon-without-text
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
    (testing "And input is of type `textfield`" 
      (is (= (-> input .-type) "textfield")))
    (testing "`<label>` contains `<span>` as last child"
      (is (= (-> span .-tagName) "SPAN")))
    (testing "`<span>` contains an `<i>`"
      (is (= (-> icon .-tagName) "I")))
    (testing "`<label>` contains '' as textContent?"
      (is (= (-> label .-textContent) "")))))


(defcard-rg card-textfield-icon-with-text
  "
  ## And with text
  ```
  [textfield {:icon :i.fab.fa-react}]
  ```
  "
  [textfield {:icon :i.fab.fa-react} "React!"])

(deftest test-textfield-icon-with-text
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
    (testing "And input is of type `textfield`" 
      (is (= (-> input .-type) "textfield")))
    (testing "`<label>` contains `<span>` as last child"
      (is (= (-> span .-tagName) "SPAN")))
    (testing "`<span>` contains an `<i>`"
      (is (= (-> icon .-tagName) "I")))
    (testing "`<label>` contains 'cloud' as textContent?"
      (is (= (-> label .-textContent) "React!")))))

(defcard-rg card-textfield-icon-with-text-and-description
  "
  ## With icon, text and description
  ```
  [:div
   [:div 
    [textfield {:name \"framework\" icon :i.fab.fa-react} 
     [:span \"React\" [:div [:small \"In computing, React is a JavaScript library for building user interfaces. It is maintained by Facebook, Instagram and a community of individual developers and corporations.\"]]]]]
   [:div
    [textfield {:name \"framework\" icon :i.fab.fa-angular}
     [:span \"Angular\" [:div [:small \"Angular is a TypeScript-based open-source front-end web application platform led by the Angular Team at Google and by a community of individuals and corporations.\"]]]]]]
  ```
  "
  [:div
   [:div 
    [textfield {:name "framework" :icon :i.fab.fa-react} 
     [:span "React" [:div [:small "In computing, React is a JavaScript library for building user interfaces. It is maintained by Facebook, Instagram and a community of individual developers and corporations."]]]]]
   [:div
    [textfield {:name "framework" :icon :i.fab.fa-angular}
     [:span "Angular" [:div [:small "Angular is a TypeScript-based open-source front-end web application platform led by the Angular Team at Google and by a community of individuals and corporations. Angular is a complete rewrite from the same team that built AngularJS."]]]]]])

(deftest test-textfield-icon-with-text-and-description
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
    (testing "And input is of type `textfield`" 
      (is (= (-> input .-type) "textfield")))
    (testing "`<label>` contains `<span>` as last child"
      (is (= (-> span .-tagName) "SPAN")))
    (testing "`<span>` contains an `<i>`"
      (is (= (-> icon .-tagName) "I")))
    (testing "`<label>` contains 'cloud' as textContent?"
      (is (= (-> label .-textContent) "React!")))))

