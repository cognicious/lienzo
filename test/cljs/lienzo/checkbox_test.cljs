(ns lienzo.checkbox-test
  (:require [cljs.test :refer-macros [is testing async use-fixtures]]
            [cljs-react-test.utils :as tu]
            [devcards.core :refer-macros [deftest defcard defcard-rg reagent]]
            [reagent.core :as r]
            [lienzo.components.checkbox :refer [checkbox]]))

(defcard
  (str "# Checkbox \n"
       "This namespace defines some functions about checkbox component"))

(defcard-rg card-checkbox-arity-zero
  "
  ## Arity 0 puts creates a simple checkbox
  ```
  [checkbox]
  ```
  "
  [checkbox])

(deftest test-checkbox-arity-zero
  (let [element (-> js/document
                    (.getElementsByClassName "lnz")
                    (aget 0))]
    (.log js/console element)
    (testing "Is an input?" 
      (is (= (-> element .-firstChild .-tagName) "INPUT")))
    (testing "Is of type 'checkbox'" 
      (is (= (-> element .-firstChild .-type) "checkbox")))
    (testing "Contains '' as textContent?"
      (is (= (-> element .-textContent) "")))))

(defcard-rg card-checkbox-arity-one-text
  "
  ## Arity 1 puts a simple text
  ```
  [checkbox \"Checkbox Arity One\"]
  ```
  "
  [checkbox "Checkbox Arity One"])

(deftest test-checkbox-arity-one-text
  (let [element (-> js/document
                    (.getElementsByClassName "lnz")
                    (aget 1))]
    (testing "Is an input?"
      (is (= (-> element .-firstChild .-tagName) "INPUT")))
    (testing "Is of type 'checkbox'" 
      (is (= (-> element .-firstChild .-type) "checkbox")))
    (testing "Contains 'Checkbox Arity One' as textContent?"
      (is (= (-> element .-textContent) "Checkbox Arity One")))))

(defcard-rg card-checkbox-arity-one-attrs
  "
  ## Or attributes
  ```
  [checkbox {:style {:background-color \"red\"}}]
  ```
  "
  [checkbox {:style {:background-color "red"}}])

(deftest test-checkbox-arity-one-attrs
  (let [element (-> js/document
                    (.getElementsByClassName "lnz")
                    (aget 2))]
    (testing "Is an input?"
      (is (= (-> element .-firstChild .-tagName) "INPUT")))
    (testing "Is of type 'checkbox'" 
      (is (= (-> element .-firstChild .-type) "checkbox")))
    (testing "Contains 'red' as backgroundColor?"
      (is (clojure.string/starts-with? (-> element .-firstChild .-style .-backgroundColor) "red")))))

(defcard-rg card-checkbox-arity-two
  "
  ## Arity 2 receives attributes map and text checkbox

  ```
  [checkbox {:on-click #(js/alert \"oh!\")} \"Checkbox Arity Two\"]
  ```
  "
  [checkbox {:on-click #(js/alert "oh!")} "Checkbox Arity Two"])

(deftest test-checkbox-arity-two
  (let [element (-> js/document
                    (.getElementsByClassName "lnz")
                    (aget 3))]
    (testing "Is an input?"
      (is (= (-> element .-firstChild .-tagName) "INPUT")))
    (testing "Is of type 'checkbox'" 
      (is (= (-> element .-firstChild .-type) "checkbox")))
    (testing "Contains 'Checkbox Arity Two' as textContent?"
      (is (clojure.string/starts-with? (.-textContent element) "Checkbox Arity Two")))))

(defcard-rg card-checkbox-disabled
  "
  ## Testing a disable checkbox, see cursor and animation!
  ```
  [checkbox {:disabled \"disabled\"} \"Checkbox Disabled\"]
  ```
  "
  [checkbox {:disabled "disabled"} "Checkbox Disabled"])

(deftest test-checkbox-disabled
  (let [element (-> js/document
                    (.getElementsByClassName "lnz")
                    (aget 4))]
    (testing "Is an input?"
      (is (= (-> element .-firstChild .-tagName) "INPUT")))
    (testing "Is of type 'checkbox'?" 
      (is (= (-> element .-firstChild .-type) "checkbox")))
    (testing "Is disabled?"
      (is (-> element .-firstChild .-disabled)))))

(defcard-rg card-checkbox-icon-with-text
  "
  ## Icon special attribute to add an icon (FontAwesome5) with text
  ```
  [checkbox {:icon :i.fas.fa-cloud} \"cloud\"]
  ```
  "
  [checkbox {:icon :i.fas.fa-cloud} "cloud"])

(deftest test-checkbox-icon-with-text
  (let [element (-> js/document
                    (.getElementsByClassName "lnz")
                    (aget 5))]
    (testing "Is an input?"
      (is (= (-> element .-firstChild .-tagName) "INPUT")))
    (testing "Is of type 'checkbox'?" 
      (is (= (-> element .-firstChild .-type) "checkbox")))
    (testing "First child span?"
      (is (= (-> element .-firstChild .-nextSibling .-tagName) "SPAN")))
    (testing "First child of child is i?"
      (is (= (-> element .-firstChild .-nextSibling .-firstChild .-tagName) "I")))
    (testing "First child of child has fa class?"
      (is (clojure.string/starts-with? (-> element .-firstChild .-nextSibling .-firstChild .-className) "fa")))
    (testing "Last child contains 'cloud' as textContent"
      (is (= (-> element .-lastChild .-textContent) "cloud")))))


(defcard-rg card-checkbox-icon-without-text
  "
  ## And without text
  ```
  [checkbox {:icon :i.fab.fa-react}]
  ```
  "
  [checkbox {:icon :i.fas.fa-cloud}])

(deftest test-checkbox-icon-without-text
  (let [element (-> js/document
                    (.getElementsByClassName "lnz")
                    (aget 6))]
    (testing "Is an input?"
      (is (= (-> element .-firstChild .-tagName) "INPUT")))
    (testing "Is of type 'checkbox'?" 
      (is (= (-> element .-firstChild .-type) "checkbox")))
    (testing "First child span?"
      (is (= (-> element .-firstChild .-nextSibling .-tagName) "SPAN")))
    (testing "First child of child is i?"
      (is (= (-> element .-firstChild .-nextSibling .-firstChild .-tagName) "I")))
    (testing "First child of child has fa class?"
      (is (clojure.string/starts-with? (-> element .-firstChild .-nextSibling .-firstChild .-className) "fa")))
    (testing "Last child contains empty string '' as textContent"
      (is (= (-> element .-lastChild .-lastChild .-textContent) "")))))
