(ns lienzo.button-test
  (:require [cljs.test :refer-macros [is testing async use-fixtures]]
            ;[cljs-react-test.utils :as tu]
            [devcards.core :refer-macros [deftest defcard defcard-rg reagent]]
            [reagent.core :as r]
            [lienzo.components.button :refer [button]]))

(defcard
  (str "# Button \n"
       "This namespace defines some functions about button component"))

(defcard-rg card-button-arity-zero
  "
  ## Arity 0 puts creates a simple button
  ```
  [button]
  ```
  "
  [button])

(deftest test-button-arity-zero
  (let [element (-> js/document
                    (.getElementsByClassName "lnz")
                    (aget 0))]
    (testing "Is a button?" 
      (is (= (.-tagName element) "BUTTON")))
    (testing "Contains 'Button' as textContent?"
      (is (= (.-textContent element) "Button")))))

(defcard-rg card-button-arity-one-text
  "
  ## Arity 1 puts a simple text
  ```
  [button \"Button Arity One\"]
  ```
  "
  [button "Button Arity One"])

(deftest test-button-arity-one-text
  (let [element (-> js/document
                    (.getElementsByClassName "lnz")
                    (aget 1))]
    (testing "Is a button?"
      (is (= (.-tagName element) "BUTTON")))
    (testing "Contains 'Button Arity One' as textContent?"
      (is (= (.-textContent element) "Button Arity One")))))


(defcard-rg card-button-arity-one-attrs
  "
  ## Or attributes
  ```
  [button {:style {:background-color \"red\"}}]
  ```
  "
  [button {:style {:background "red"}}])

(deftest test-button-arity-one-attrs
  (let [element (-> js/document
                    (.getElementsByClassName "lnz")
                    (aget 2))]
    (testing "Is a button?"
      (is (= (.-tagName element) "BUTTON")))
    (testing "Contains 'red' as backgroundColor?"
      (is (clojure.string/starts-with? (-> element .-style .-backgroundColor) "red")))))

(defcard-rg card-button-arity-two
  "
  ## Arity 2 receives attributes map and text button

  ```
  [button {:on-click #(js/alert \"oh!\")} \"Button Arity Two\"]
  ```
  "
  [button {:on-click #(js/alert "oh!")} "Button Arity Two"])

(deftest test-button-arity-two
  (let [element (-> js/document
                    (.getElementsByClassName "lnz")
                    (aget 3))]
    (testing "Is a button?"
      (is (= (.-tagName element) "BUTTON")))
    (testing "Contains 'Button Arity Two' as textContent?"
      (is (clojure.string/starts-with? (.-textContent element) "Button Arity Two")))))

(defcard-rg card-button-disabled
  "
  ## Testing a disable button, see cursor and animation!
  ```
  [button {:disabled \"disabled\"} \"Button Disabled\"]
  ```
  "
  [button {:disabled "disabled"} "Button Disabled"])

(deftest test-button-disabled
  (let [element (-> js/document
                    (.getElementsByClassName "lnz")
                    (aget 4))]
    (testing "Is a button?"
      (is (= (.-tagName element) "BUTTON")))
    (testing "Contains 'Button Arity Two' as textContent?"
      (is (.-disabled element)))))

(defcard-rg card-button-icon-with-text
  "
  ## Icon special attribute to add an icon (FontAwesome5) with text
  ```
  [button {:icon :i.fas.fa-cloud} \"cloud\"]
  ```
  "
  [button {:icon :i.fas.fa-cloud} "cloud"])

(deftest test-button-icon-with-text
  (let [element (-> js/document
                    (.getElementsByClassName "lnz")
                    (aget 5))]
    (testing "Is a button?"
      (is (= (.-tagName element) "BUTTON")))
    (testing "First child span?"
      (is (= (-> element .-firstChild .-tagName) "SPAN")))
    (testing "First child of child is i?"
      (is (= (-> element .-firstChild .-firstChild .-tagName) "I")))
    (testing "First child of child has fa class?"
      (is (clojure.string/starts-with? (-> element .-firstChild .-firstChild .-className) "fa")))
    (testing "Last child contains 'cloud' as textContent"
      (is (= (-> element .-textContent) "cloud")))))


(defcard-rg card-button-icon-without-text
  "
  ## And without text
  ```
  [button {:icon :i.fab.fa-react}]
  ```
  "
  [button {:icon :i.fas.fa-cloud}])

(deftest test-button-icon-without-text
  (let [element (-> js/document
                    (.getElementsByClassName "lnz")
                    (aget 6))]
    (testing "Is a button?"
      (is (= (.-tagName element) "BUTTON")))
    (testing "First child span?"
      (is (= (-> element .-firstChild .-tagName) "SPAN")))
    (testing "First child of child is i?"
      (is (= (-> element .-firstChild .-firstChild .-tagName) "I")))
    (testing "First child of child has fa class?"
      (is (clojure.string/starts-with? (-> element .-firstChild .-firstChild .-className) "fa")))
    (testing "Last child contains empty string '' as textContent"
      (is (= (-> element .-textContent) "")))))
