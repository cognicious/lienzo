(ns lienzo.textfield-test
  (:require [cljs.test :refer-macros [is testing async use-fixtures]]
            ;[cljs-react-test.utils :as tu]
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
  [:label.lnz {:for \"random-uuid\"}                           ;; Label wrapper
    [:span.name \"\"]                                          ;; Field name
    [:span.field [:input {:type \"text\" :id \"random-uuid\"}]]] ;; Real INPUT wrapped by SPAN    
  "
  [:div.lnz [textfield]])

(deftest test-textfield-arity-zero
  (let [label (-> js/document
                  (.getElementsByClassName "lnz")
                  (aget 0)
                  .-firstChild)
        name  (-> label .-firstChild)
        field (-> name .-nextSibling)
        input (-> field .-firstChild)]
    (testing "Wrapper is a `<label>`?"
      (is (= (-> label .-tagName) "LABEL")))
    (testing "First child of label is an `<i>`?"
      (is (= (-> name .-tagName) "SPAN")))
    (testing "Real input is an `<input>`?" 
      (is (= (-> field .-tagName) "SPAN")))
    (testing "And input is of type `textfield`" 
      (is (= (-> input .-type) "text")))
    (testing "`<label>` contains '' as textContent?"
      (is (= (-> label .-textContent) "")))))

(defcard-rg card-textfield-arity-one-text
  "
  ## Arity 1 puts a simple text
  ```
  [textfield \"Textfield Arity One\"]
  ```
  "
  [:div.lnz [textfield "Textfield Arity One"]])

(deftest test-textfield-arity-one-text
  (let [label (-> js/document
                  (.getElementsByClassName "lnz")
                  (aget 1)
                  .-firstChild)
        name  (-> label .-firstChild)
        field (-> name .-nextSibling)
        input (-> field .-firstChild)]
    (testing "Wrapper is a `<label>`?"
      (is (= (-> label .-tagName) "LABEL")))
    (testing "First child of label is an `<i>`?"
      (is (= (-> name .-tagName) "SPAN")))
    (testing "Real input is an `<input>`?" 
      (is (= (-> field .-tagName) "SPAN")))
    (testing "And input is of type `textfield`" 
      (is (= (-> input .-type) "text")))
    (testing "`<label>` contains '' as textContent?"
      (is (= (-> label .-textContent) "Textfield Arity One")))))

(defcard-rg card-textfield-arity-one-attrs
  "
  ## Or attributes
  ```
  [textfield {:placeholder \"John Doe\"}}]
  ```
  "
  [:div.lnz [textfield {:placeholder "John Doe"}]])

(deftest test-textfield-arity-one-attrs
  (let [label (-> js/document
                  (.getElementsByClassName "lnz")
                  (aget 2)
                  .-firstChild)
        name  (-> label .-firstChild)
        field (-> name .-nextSibling)
        input (-> field .-firstChild)]
    (testing "Wrapper is a `<label>`?"
      (is (= (-> label .-tagName) "LABEL")))
    (testing "First child of label is an `<i>`?"
      (is (= (-> name .-tagName) "SPAN")))
    (testing "Real input is an `<input>`?" 
      (is (= (-> field .-tagName) "SPAN")))
    (testing "And input is of type `textfield`" 
      (is (= (-> input .-type) "text")))
    (testing "`<label>` contains '' as textContent?"
      (is (= (-> label .-textContent) "")))))

(defcard-rg card-textfield-arity-two
  "
  ## Arity 2 receives attributes map and text textfield

  ```
  [textfield {:on-click #(js/alert \"oh!\")} \"Textfield Arity Two\"]
  ```
  "
  [:div.lnz [textfield {:on-click #(js/alert "oh!")} "Textfield Arity Two"]])

(deftest test-textfield-arity-two
  (let [label (-> js/document
                  (.getElementsByClassName "lnz")
                  (aget 3)
                  .-firstChild)
        name  (-> label .-firstChild)
        field (-> name .-nextSibling)
        input (-> field .-firstChild)]
    (testing "Wrapper is a `<label>`?"
      (is (= (-> label .-tagName) "LABEL")))
    (testing "First child of label is an `<i>`?"
      (is (= (-> name .-tagName) "SPAN")))
    (testing "Real input is an `<input>`?" 
      (is (= (-> field .-tagName) "SPAN")))
    (testing "And input is of type `textfield`" 
      (is (= (-> input .-type) "text")))
    (testing "`<label>` contains '' as textContent?"
      (is (= (-> label .-textContent) "Textfield Arity Two")))))

(defcard-rg card-textfield-disabled
  "
  ## Testing a disable textfield, see cursor and animation!
  ```
  [textfield {:disabled true :value \"Foo\"} \"Textfield Disabled\"]
  ```
  "
  [:div.lnz [textfield {:disabled true :value "Foo"} "Textfield Disabled"]])

(deftest test-textfield-disabled
  (let [label (-> js/document
                  (.getElementsByClassName "lnz")
                  (aget 4)
                  .-firstChild)
        name  (-> label .-firstChild)
        field (-> name .-nextSibling)
        input (-> field .-firstChild)]
    (testing "Wrapper is a `<label>`?"
      (is (= (-> label .-tagName) "LABEL")))
    (testing "First child of label is an `<i>`?"
      (is (= (-> name .-tagName) "SPAN")))
    (testing "Real input is an `<input>`?" 
      (is (= (-> field .-tagName) "SPAN")))
    (testing "And input is of type `text`" 
      (is (= (-> input .-type) "text")))
    (testing "And input hast value `Foo`" 
      (is (= (-> input .-value) "Foo")))
    (testing "`<label>` contains '' as textContent?"
      (is (= (-> label .-textContent) "Textfield Disabled")))))

(defcard-rg card-textfield-readonly
  "
  ## Testing a readonly textfield, see cursor and animation!
  ```
  [textfield {:read-only true :value \"Bar\"} \"Textfield ReadOnly\"]
  ```
  "
  [:div.lnz [textfield {:read-only true :value "Bar"} "Textfield ReadOnly"]])

(deftest test-textfield-readonly
  (let [label (-> js/document
                  (.getElementsByClassName "lnz")
                  (aget 5)
                  .-firstChild)
        name  (-> label .-firstChild)
        field (-> name .-nextSibling)
        input (-> field .-firstChild)]
    (testing "Wrapper is a `<label>`?"
      (is (= (-> label .-tagName) "LABEL")))
    (testing "First child of label is an `<i>`?"
      (is (= (-> name .-tagName) "SPAN")))
    (testing "Real input is an `<input>`?" 
      (is (= (-> field .-tagName) "SPAN")))
    (testing "And input is of type `text`" 
      (is (= (-> input .-type) "text")))
    (testing "And input hast value `Foo`" 
      (is (= (-> input .-value) "Bar")))
    (testing "`<label>` contains '' as textContent?"
      (is (= (-> label .-textContent) "Textfield ReadOnly")))))

(defcard-rg card-textfield-icon-without-text
  "
  ## Icon special attribute to add an icon (FontAwesome5)
  ```
  [textfield {:icon :i.fas.fa-cloud}]
  ```
  produces in hiccup:
  ```
  [:label.lnz {:for \"random-uuid\"}                          ;; Label wrapper
    [:span.name \"\"]                                         ;; Field name
    [:span.field [:input {:type \"text\" :id \"random-uuid\"}]] ;; Real INPUT wrapped by SPAN 
                 [:i.fas.fa-cloud]]                         ;; Icon
  "
  [:div.lnz [textfield {:icon :i.fas.fa-cloud}]])

(deftest test-textfield-icon-without-text
  (let [label (-> js/document
                  (.getElementsByClassName "lnz")
                  (aget 6)
                  .-firstChild)
        name  (-> label .-firstChild)
        field (-> name .-nextSibling)
        input (-> field .-firstChild)
        icon  (-> input .-nextSibling)]
    (testing "Wrapper is a `<label>`?"
      (is (= (-> label .-tagName) "LABEL")))
    (testing "First child of label is an `<i>`?"
      (is (= (-> name .-tagName) "SPAN")))
    (testing "Real input is an `<input>`?" 
      (is (= (-> field .-tagName) "SPAN")))
    (testing "And input is of type `text`" 
      (is (= (-> input .-type) "text")))
    (testing "Icon is an `<i>`?"
      (is (= (-> icon .-tagName) "I")))
    (testing "`<label>` contains '' as textContent?"
      (is (= (-> label .-textContent) "")))))


(defcard-rg card-textfield-icon-with-text
  "
  ## And with text
  ```
  [textfield {:icon :i.fab.fa-react} \"React!\"]
  ```
  "
  [:div.lnz [textfield {:icon :i.fab.fa-react} "React!"]])

(deftest test-textfield-icon-with-text
  (let [label (-> js/document
                  (.getElementsByClassName "lnz")
                  (aget 7)
                  .-firstChild)
        name  (-> label .-firstChild)
        field (-> name .-nextSibling)
        input (-> field .-firstChild)
        icon  (-> input .-nextSibling)]
    (testing "Wrapper is a `<label>`?"
      (is (= (-> label .-tagName) "LABEL")))
    (testing "First child of label is an `<i>`?"
      (is (= (-> name .-tagName) "SPAN")))
    (testing "Real input is an `<input>`?" 
      (is (= (-> field .-tagName) "SPAN")))
    (testing "And input is of type `text`" 
      (is (= (-> input .-type) "text")))
    (testing "Icon is an `<i>`?"
      (is (= (-> icon .-tagName) "I")))
    (testing "`<label>` contains 'React!' as textContent?"
      (is (= (-> label .-textContent) "React!")))))


(defcard-rg card-textfield-password
  "
  ## A password entry
  ```
  [textfield {:icon :i.fab.fa-react :type \"password\"} \"Password\"]
  ```
  "
  [:div.lnz [textfield {:icon :i.fas.fa-lock :type "password"} "Password"]])
