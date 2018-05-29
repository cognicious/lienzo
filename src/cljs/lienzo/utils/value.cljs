(ns lienzo.utils.value
  (:require [reagent.core :as r]))

(defn >value 
  [model]
  (if (satisfies? IDeref model)
    @model
    model))

(defn >props 
  [props]
  (apply hash-map (rest props)))

(defn >args 
  [args]
  (-> args
      r/argv
      >props))
