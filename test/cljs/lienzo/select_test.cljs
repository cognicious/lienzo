(ns lienzo.select-test
  (:require [cljs.test :refer-macros [is testing async]]
            [devcards.core :refer-macros [deftest defcard defcard-rg]]
            [sablono.core :as sab]
            [reagent.core :as r]
            [lienzo.components.select :refer [select]]))

(defcard
  (str "# Select \n"
       "This namespace defines some functions about select component"))

(defcard-rg test-input-box
  [select])

