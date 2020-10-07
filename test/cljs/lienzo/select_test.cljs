(ns lienzo.select-test
  (:require [cljs.test :refer-macros [is testing async]]
            [devcards.core :refer-macros [deftest defcard defcard-rg]]
            [sablono.core :as sab]
            [reagent.core :as r]
            [lienzo.components.select :refer [select]]))

(defcard
  (str "# Select \n"
       "This namespace defines some functions about select component"))

(defcard-rg test-select-box
  [select])

(defcard-rg test-select-box-arity-1-text
  [select ["foo" "bar" "baz"]])

(defcard-rg test-select-box-arity-1-hiccup
  [select [[:div {:tab-index 0 :style {:color "green"}} "foo"] [:div {:tab-index 1 :style {:color "purple"}} "bar"]]])
