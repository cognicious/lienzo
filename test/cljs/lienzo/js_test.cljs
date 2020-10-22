(ns lienzo.js-test
  (:require-macros [lienzo.macros :refer [adhoc-render]])
  (:require [cljs.test :refer-macros [is testing async use-fixtures]]
            [devcards.core :refer-macros [deftest defcard defcard-rg reagent]]
            [lienzo.utils.js :as utils-js]
            [reagent.core :as r]
            [lienzo.containers :refer [bind-synthetic-container synthetic-container]]
            [lienzo.components.checkbox :refer [checkbox]]))


(use-fixtures :each bind-synthetic-container)

(defcard
  (str "# JS Utils \n"
       "This namespace defines some functions to manipulate DOM in  a functional way"))


(let [foo [:div {:id "foo" :style {:border "1px solid red" :width "100px"}} "foo"]] 
  (defcard-rg card-add-event-listener
    "##Adds an event listener for a specific event on a native event target.

    ```
    [:div {:id \"foo\" :style {:border \"1px solid red\" :width \"100px\"}} \"foo\"]

    ; some code
    
    (let [foo-node (-> js/document
                       (.getElementById \"foo\")
                       (lienzo.utils.js/add-event-listener \"click\" (fn [] (js/alert \"ok\")))
                       (lienzo.utils.js/add-event-listener \"mouseover\" (fn [] (.log js/console \"mouseover\"))))]
     ; code
     )
    ```
    "
    foo)

  (deftest test-add-event-listener
    (let [foo-node (-> js/document
                       (.getElementById "foo")
                       (utils-js/add-event-listener "click" #(js/alert "ok"))
                       (utils-js/add-event-listener "mouseover" #(.log js/console "mouseover")))]
      
      (testing "returned event is same as passed as param"
        (is (= foo-node (.getElementById js/document "foo")))))))


(let [bar [:div {:id "bar" :style  {:border "1px solid green" :width "100px"}} "bar"]]
  (defcard-rg card-class-push
    "## Adds a class to a given element.

    ```
    ```"
    bar)
  (deftest test-class-push
    (let [bar-node (-> js/document
                       (.getElementById "bar")
                       (utils-js/class-push "foo")
                       (utils-js/class-push "foo")
                       (utils-js/class-push "bar")
                       (utils-js/class-push "foo"))
          _ (.log js/console bar-node)]
      (testing "classes attached"
        (is (= (.-className bar-node)  "foo bar")))))) 
