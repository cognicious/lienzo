(ns lienzo.checkbox-test
  (:require [cljs.test :refer-macros [is testing async use-fixtures]]
            [cljs-react-test.utils :as tu]
            [devcards.core :refer-macros [deftest defcard defcard-rg reagent]]
            [reagent.core :as r]
            [lienzo.components.button :refer [button]]
            [lienzo.components.checkbox :refer [checkbox]]))

(defcard
  (str "# Checkbox \n"
       "This namespace defines some functions about checkbox component\n\n"
       "A Checkbox is a `<label>` and contains an icon `<i>`, a real checkbox `<input>` and optionally a `<span>` or a simple text."))

(defcard-rg card-checkbox-arity-zero
  "
  ## Arity 0 puts creates a simple checkbox
  ```
  [checkbox]
  ```
  produces in hiccup:
  ```
  [:label.lnz {:for \"random-uuid\"}                ;; Label wrapper
    [:i.lnz {:tab-index 0}]                       ;; Stylized Pseudo INPUT
    [:input {:id \"random-uuid\" :type \"checkbox\"}] ;; Real INPUT 
    \"\"]
     
  "
  [checkbox])

(deftest test-checkbox-arity-zero
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
    (testing "And input is of type `checkbox`" 
      (is (= (-> input .-type) "checkbox")))
    (testing "`<label>` contains '' as textContent?"
      (is (= (-> label .-textContent) "")))))

(defcard-rg card-checkbox-arity-one-text
  "
  ## Arity 1 puts a simple text
  ```
  [checkbox \"Checkbox Arity One\"]
  ```
  "
  [checkbox "Checkbox Arity One"])

(deftest test-checkbox-arity-one-text
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
    (testing "And input is of type `checkbox`" 
      (is (= (-> input .-type) "checkbox")))
    (testing "`<label>` contains 'Checkbox Arity One' as textContent?"
      (is (= (-> label .-textContent) "Checkbox Arity One")))))

(defcard-rg card-checkbox-arity-one-attrs
  "
  ## Or attributes
  ```
  [checkbox {:defaultChecked true}}]
  ```
  "
  [checkbox {:defaultChecked true}])

(deftest test-checkbox-arity-one-attrs
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
    (testing "And input is of type `checkbox`" 
      (is (= (-> input .-type) "checkbox")))
    (testing "`<label>` contains '' as textContent?"
      (is (= (-> label .-textContent) "")))
    (testing "`<input>` are checked?"
      (is (-> input .-checked)))))

(defcard-rg card-checkbox-arity-two
  "
  ## Arity 2 receives attributes map and text checkbox

  ```
  [checkbox {:on-click #(js/alert \"oh!\")} \"Checkbox Arity Two\"]
  ```
  "
  [checkbox {:on-click #(js/alert "oh!")} "Checkbox Arity Two"])

(deftest test-checkbox-arity-two
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
    (testing "And input is of type `checkbox`" 
      (is (= (-> input .-type) "checkbox")))
    (testing "`<label>` contains 'Checkbox Arity Two' as textContent?"
      (is (= (-> label .-textContent) "Checkbox Arity Two")))))

(defcard-rg card-checkbox-disabled
  "
  ## Testing a disable checkbox, see cursor and animation!
  ```
  [checkbox {:disabled \"disabled\"} \"Checkbox Disabled\"]
  ```
  "
  [checkbox {:disabled "disabled"} "Checkbox Disabled"])

(deftest test-checkbox-disabled
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
    (testing "And input is of type `checkbox`" 
      (is (= (-> input .-type) "checkbox")))
    (testing "`<label>` contains 'Checkbox Disabled' as textContent?"
      (is (= (-> label .-textContent) "Checkbox Disabled")))
    (testing "`<input>` are disabled?"
      (is (-> input .-disabled)))))

(defcard-rg card-checkbox-icon-with-text
  "
  ## Icon special attribute to add an icon (FontAwesome5)
  ```
  [checkbox {:icon :i.fas.fa-cloud}]
  ```
  produces in hiccup:
  ```
  [:label.lnz {:for \"random-uuid\"}                ;; Label wrapper
    [:i.lnz {:tab-index 0}]                       ;; Stylized Pseudo INPUT
    [:input {:id \"random-uuid\" :type \"checkbox\"}] ;; Real INPUT 
    [:span [:icon :i.fas.fa-cloud]]]              ;; Icon!
  "
  [checkbox {:icon :i.fas.fa-cloud}])

(deftest test-checkbox-icon-with-text
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
    (testing "And input is of type `checkbox`" 
      (is (= (-> input .-type) "checkbox")))
    (testing "`<label>` contains `<span>` as last child"
      (is (= (-> span .-tagName) "SPAN")))
    (testing "`<span>` contains an `<i>`"
      (is (= (-> icon .-tagName) "I")))
    (testing "`<label>` contains '' as textContent?"
      (is (= (-> label .-textContent) "")))))


(defcard-rg card-checkbox-icon-without-text
  "
  ## And with text
  ```
  [checkbox {:icon :i.fab.fa-react}]
  ```
  "
  [checkbox {:icon :i.fab.fa-react} "React!"])

(deftest test-checkbox-icon-without-text
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
    (testing "And input is of type `checkbox`" 
      (is (= (-> input .-type) "checkbox")))
    (testing "`<label>` contains `<span>` as last child"
      (is (= (-> span .-tagName) "SPAN")))
    (testing "`<span>` contains an `<i>`"
      (is (= (-> icon .-tagName) "I")))
    (testing "`<label>` contains 'cloud' as textContent?"
      (is (= (-> label .-textContent) "React!")))))
