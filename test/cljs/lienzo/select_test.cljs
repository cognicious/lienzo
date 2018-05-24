(ns lienzo.select-test
  (:require [cljs.test :refer-macros [is testing async]]
            [devcards.core :refer-macros [deftest defcard defcard-rg]]
            [sablono.core :as sab]
            [reagent.core :as r]
            [lienzo.components.select :refer [input-box input-wrapper pseudo-menu mdl-select]]))

(defcard
  (str "# Select \n"
       "This namespace defines some functions about select component"))

(defcard-rg test-input-box
  [input-box])

(defcard-rg test-input-wrapper
  [input-wrapper])

(defcard-rg test-pseudo-menu
  [pseudo-menu])

(defcard-rg test-mdl-select
  [mdl-select])
