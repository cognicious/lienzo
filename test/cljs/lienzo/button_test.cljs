(ns lienzo.button-test
  (:require [cljs.test :refer-macros [is testing async use-fixtures]]
            [cljs-react-test.utils :as tu]
            [devcards.core :refer-macros [deftest defcard defcard-rg reagent]]
            [dommy.core :as dommy :refer-macros [sel1]]
            [reagent.core :as r]
            [lienzo.components.button :refer [button]]))

(def ^:dynamic c)

(use-fixtures :each (fn [test-fn]
                      (binding []
                        (test-fn)
                        (tu/unmount! c))))

(defcard
  (str "# Button \n"
       "This namespace defines some functions about button component"))

(defcard-rg card-button-arity-zero
  "
  Arity 0 puts creates a simple button
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

(defcard-rg test-button-arity-one-text
  "
  Arity 1 puts a simple text
  ```
  [button \"Button Arity One\"]
  ```
  "
  [button "Button Arity One"])

(defcard-rg test-button-arity-one-attrs
  "
  Or attributes
  ```
  [button {:style {:background \"#1676f3\"}}]
  ```
  "
  [button {:style {:background "red"}}])

(defcard-rg test-button-arity-two
  "
  Arity 2 receives attributes map and text button

  ```
  [button {:on-click #(js/alert \"oh!\")} \"Button Arity Two\"]
  ```
  "
  [button {:on-click #(js/alert "oh!")} "Button Arity Two"])

(defcard-rg test-button-disabled
  "
  Testing a disable button, see cursor and animation!
  ```
  [button {:disabled \"disabled\"} \"Button Disabled\"]
  ```
  "
  [button {:disabled "disabled"} "Button Disabled"])

(defcard-rg test-button-icon-with-text
  "
  Icon special attribute to add an icon (FontAwesome5) with text
  ```
  [button {:icon :i.fas.fa-cloud} \"cloud\"]
  ```
  "
  [button {:icon :i.fas.fa-cloud} "cloud"])

(defcard-rg card-button-icon-without-text
  "
  Or not
  ```
  [button {:icon :i.fab.fa-react}]
  ```
  "
  [button {:icon :i.fas.fa-cloud}])

