(ns lienzo.button-test
  (:require-macros [lienzo.macros :refer [adhoc-render]])
  (:require [cljs.test :refer-macros [is testing async use-fixtures]]
            [devcards.core :refer-macros [deftest defcard defcard-rg reagent]]
            [reagent.core :as r]
            [lienzo.containers :refer [bind-synthetic-container synthetic-container]]
            [lienzo.components.button :refer [button]]))

(use-fixtures :each bind-synthetic-container)

(defcard
  (str "# Button \n"
       "This namespace defines some functions about button component"))

(let [zero  [:div.lnz [button]]]

  (defcard-rg card-button-arity-zero
    "
  ## Arity 0 puts creates a simple button
  ```
  [button]
  ```
  "
  zero)

  (deftest test-button-arity-zero
    (let [render (adhoc-render (r/render zero synthetic-container)
                               (-> js/document
                                   (.getElementsByClassName "lnz")
                                   (aget 0)))
          element (.-firstChild render)]
      (testing "Is a button?"
        (is (= (.-tagName element) "BUTTON")))
      (testing "Contains 'Button' as textContent?"
        (is (= (.-textContent element) "Button"))))))

(let [one [:div.lnz
           [button "Button Arity One"]]]
  (defcard-rg card-button-arity-one-text
    "
  ## Arity 1 puts a simple text
  ```
  [button \"Button Arity One\"]
  ```
  "
    one)

  (deftest test-button-arity-one-text
    (let [render  (adhoc-render (r/render one synthetic-container)
                                (-> js/document
                                    (.getElementsByClassName "lnz")
                                    (aget 1)))
          element (.-firstChild render)]
      (testing "Is a button?"
        (is (= (.-tagName element) "BUTTON")))
      (testing "Contains 'Button Arity One' as textContent?"
        (is (= (.-textContent element) "Button Arity One"))))))


(let [one-attrs [:div.lnz
                 [button {:style {:background "red"}}]]]
  (defcard-rg card-button-arity-one-attrs
    "
  ## Or attributes
  ```
  [button {:style {:background-color \"red\"}}]
  ```
  "
    one-attrs)

  (deftest test-button-arity-one-attrs
    (let [render (adhoc-render (r/render one-attrs synthetic-container)
                               (-> js/document
                                   (.getElementsByClassName "lnz")
                                   (aget 2)))
          element (.-firstChild render)]
      (testing "Is a button?"
        (is (= (.-tagName element) "BUTTON")))
      (testing "Contains 'red' as backgroundColor?"
        (is (clojure.string/starts-with? (-> element .-style .-backgroundColor) "red"))))))


(let [two [:div.lnz
           [button {:on-click #(js/alert "oh!")} "Button Arity Two"]]]
  (defcard-rg card-button-arity-two
    "
  ## Arity 2 receives attributes map and text button

  ```
  [button {:on-click #(js/alert \"oh!\")} \"Button Arity Two\"]
  ```
  "
    two)

  (deftest test-button-arity-two
    (let [render (adhoc-render (r/render two synthetic-container)
                               (-> js/document
                                   (.getElementsByClassName "lnz")
                                   (aget 3)))
          element (.-firstChild render)]
      (testing "Is a button?"
        (is (= (.-tagName element) "BUTTON")))
      (testing "Contains 'Button Arity Two' as textContent?"
        (is (clojure.string/starts-with? (.-textContent element) "Button Arity Two"))))))

(let [disabled [:div.lnz
                [button {:disabled "disabled"} "Button Disabled"]]]
  (defcard-rg card-button-disabled
    "
  ## Testing a disable button, see cursor and animation!
  ```
  [button {:disabled \"disabled\"} \"Button Disabled\"]
  ```
  "
    disabled)

  (deftest test-button-disabled
    (let [render (adhoc-render (r/render disabled synthetic-container)
                               (-> js/document
                                   (.getElementsByClassName "lnz")
                                   (aget 4)))
          element (.-firstChild render)]
      (testing "Is a button?"
        (is (= (.-tagName element) "BUTTON")))
      (testing "Contains 'Button Arity Two' as textContent?"
        (is (clojure.string/starts-with? (.-textContent element) "Button Disabled")))
      (testing "disabled attribute is true?"
        (is (.-disabled element))))))

(let [icon-with-text [:div.lnz
                      [button {:icon :i.fas.fa-cloud} "cloud"]]]
  (defcard-rg card-button-icon-with-text
    "
  ## Icon special attribute to add an icon (FontAwesome5) with text
  ```
  [button {:icon :i.fas.fa-cloud} \"cloud\"]
  ```
  "
    icon-with-text)

  (deftest test-button-icon-with-text
    (let [render (adhoc-render (r/render icon-with-text synthetic-container)
                               (-> js/document
                                   (.getElementsByClassName "lnz")
                                   (aget 5)))
          element (.-firstChild render)]
      (testing "Is a button?"
        (is (= (.-tagName element) "BUTTON")))
      (testing "First child span?"
        (is (= (-> element .-firstChild .-tagName) "SPAN")))
      (testing "First child of child is i?"
        (is (= (-> element .-firstChild .-firstChild .-tagName) "I")))
      (testing "First child of child has fa class?"
        (is (clojure.string/starts-with? (-> element .-firstChild .-firstChild .-className) "fa")))
      (testing "Last child contains 'cloud' as textContent"
        (is (= (.-textContent element) "cloud"))))))


(let [icon-without-text [:div.lnz
                         [button {:icon :i.fas.fa-cloud}]]]
  (defcard-rg card-button-icon-without-text
    "
  ## And without text
  ```
  [button {:icon :i.fab.fa-react}]
  ```
  "
    icon-without-text)

  (deftest test-button-icon-without-text
    (let [render (adhoc-render (r/render icon-without-text synthetic-container)
                               (-> js/document
                                   (.getElementsByClassName "lnz")
                                   (aget 6)))
          element (.-firstChild render)]
      (testing "Is a button?"
        (is (= (.-tagName element) "BUTTON")))
      (testing "First child span?"
        (is (= (-> element .-firstChild .-tagName) "SPAN")))
      (testing "First child of child is i?"
        (is (= (-> element .-firstChild .-firstChild .-tagName) "I")))
      (testing "First child of child has fa class?"
        (is (clojure.string/starts-with? (-> element .-firstChild .-firstChild .-className) "fa")))
      (testing "Last child contains empty string '' as textContent"
        (is (= (.-textContent element) ""))))))
