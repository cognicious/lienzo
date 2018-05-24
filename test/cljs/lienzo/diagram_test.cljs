(ns lienzo.diagram-test
  (:require [cljs.test :refer-macros [is testing async]]
            [devcards.core :refer-macros [deftest defcard defcard-rg]]
            [sablono.core :as sab]
            [reagent.core :as r]))

(defcard
  (str "# Diagram \n"
       "This namespace defines some functions about diagram component"))
