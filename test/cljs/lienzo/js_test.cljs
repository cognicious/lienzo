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
    " ##Adds an event listener for a specific event on a native event target.

    ```
    [:div {:id \"foo\" :style {:border \"1px solid red\" :width \"100px\"}} \"foo\"]

    ; some code
    
    (let [foo-node (-> js/document
                       (.getElementById \"foo\")
                        (lienzo.utils.js/add-event-listener \"click\" (fn [] (js/alert \"ok\"))))]
     ; code
     )
    ```
    "
    foo)

  (deftest test-add-event-listener
    (let [foo-node (-> js/document
                       (.getElementById "foo")
                  (utils-js/add-event-listener "click" #(js/alert "ok")))]
      
      (testing "returned event is same as passed as param"
        (is (= foo-node (.getElementById js/document "foo")))))))


(defcard
  (str "# Checkbox \n"
       "This namespace defines some functions about checkbox component\n\n"
       "A Checkbox is a `<label>` and contains an icon `<i>`, a real checkbox `<input>` and optionally a `<span>` or a simple text."))

(let [zero [:div.lnz [checkbox]]]
  (defcard-rg card-checkbox-arity-zero
    "
  ## Arity 0 puts creates a simple checkbox
  ```
  [checkbox]
  ```
  produces in hiccup:
  ```
  [:label.lnz {:for \"random-uuid\"}                ;; Label wrapper
    [:i.lnz {:tab-index 0}]                       ;; Stylized Pseudo INPUT
    [:input {:id \"random-uuid\" :type \"checkbox\"}] ;; Real INPUT 
    \"\"]
     
  "
    zero)

  (deftest test-checkbox-arity-zero
    (let [label (adhoc-render (.-firstChild (r/render zero synthetic-container))
                              (-> js/document
                                  (.getElementsByClassName "lnz")
                                  (aget 0)
                                  .-firstChild))
          pseudo-input (.-firstChild label)
          input (.-nextSibling pseudo-input)]
      (testing "Wrapper is a `<label>`?"
        (is (= (.-tagName label) "LABEL")))
      (testing "First child of label is an `<i>`?"
        (is (= (.-tagName pseudo-input) "I")))
      (testing "Real input is an `<input>`?" 
        (is (= (.-tagName input) "INPUT")))
      (testing "And input is of type `checkbox`" 
        (is (= (.-type input) "checkbox")))
      (testing "`<label>` contains '' as textContent?"
        (is (= (.-textContent label) ""))))))

(let [one-text [:div.lnz [checkbox "Checkbox Arity One"]]]
  (defcard-rg card-checkbox-arity-one-text
    "
  ## Arity 1 puts a simple text
  ```
  [checkbox \"Checkbox Arity One\"]
  ```
  "
    one-text)

  (deftest test-checkbox-arity-one-text
    (let [label (adhoc-render (.-firstChild (r/render one-text synthetic-container))
                              (-> js/document
                                  (.getElementsByClassName "lnz")
                                  (aget 1)
                                  .-firstChild))
          pseudo-input (.-firstChild label)
          input (.-nextSibling pseudo-input)]
      (testing "Wrapper is a `<label>`?"
        (is (= (.-tagName label) "LABEL")))
      (testing "First child of label is an `<i>`?"
        (is (= (.-tagName pseudo-input) "I")))
      (testing "Real input is an `<input>`?" 
        (is (= (.-tagName input) "INPUT")))
      (testing "And input is of type `checkbox`" 
        (is (= (.-type input) "checkbox")))
      (testing "`<label>` contains 'Checkbox Arity One' as textContent?"
        (is (= (.-textContent label) "Checkbox Arity One"))))))

(let [one-attrs [:div.lnz [checkbox {:defaultChecked true}]]]
  (defcard-rg card-checkbox-arity-one-attrs
    "
  ## Or attributes
  ```
  [checkbox {:defaultChecked true}}]
  ```
  "
    one-attrs)

  (deftest test-checkbox-arity-one-attrs
    (let [label (adhoc-render (.-firstChild (r/render one-attrs synthetic-container))
                              (-> js/document
                                  (.getElementsByClassName "lnz")
                                  (aget 2)
                                  .-firstChild))
          pseudo-input (.-firstChild label)
          input (.-nextSibling pseudo-input)]
      (testing "Wrapper is a `<label>`?"
        (is (= (.-tagName label) "LABEL")))
      (testing "First child of label is an `<i>`?"
        (is (= (.-tagName pseudo-input) "I")))
      (testing "Real input is an `<input>`?" 
        (is (= (.-tagName input) "INPUT")))
      (testing "And input is of type `checkbox`" 
        (is (= (.-type input) "checkbox")))
      (testing "`<label>` contains '' as textContent?"
        (is (= (.-textContent label) "")))
      (testing "`<input>` are checked?"
        (is (.-checked input))))))

(let [two [:div.lnz [checkbox {:on-click #(js/alert "oh!")} "Checkbox Arity Two"]]]
  (defcard-rg card-checkbox-arity-two
    "
  ## Arity 2 receives attributes map and text checkbox

  ```
  [checkbox {:on-click #(js/alert \"oh!\")} \"Checkbox Arity Two\"]
  ```
  "
    two)

  (deftest test-checkbox-arity-two
    (let [label (adhoc-render (.-firstChild (r/render two synthetic-container))
                              (-> js/document
                                  (.getElementsByClassName "lnz")
                                  (aget 3)
                                  .-firstChild))
          pseudo-input (.-firstChild label)
          input (.-nextSibling pseudo-input)]
      (testing "Wrapper is a `<label>`?"
        (is (= (.-tagName label) "LABEL")))
      (testing "First child of label is an `<i>`?"
        (is (= (.-tagName pseudo-input) "I")))
      (testing "Real input is an `<input>`?" 
        (is (= (.-tagName input) "INPUT")))
      (testing "And input is of type `checkbox`" 
        (is (= (.-type input) "checkbox")))
      (testing "`<label>` contains 'Checkbox Arity Two' as textContent?"
        (is (= (.-textContent label) "Checkbox Arity Two"))))))

(let [disabled [:div.lnz [checkbox {:disabled "disabled"} "Checkbox Disabled"]]]
  (defcard-rg card-checkbox-disabled
    "
  ## Testing a disable checkbox, see cursor and animation!
  ```
  [checkbox {:disabled \"disabled\"} \"Checkbox Disabled\"]
  ```
  "
    disabled)

  (deftest test-checkbox-disabled
    (let [label (adhoc-render (.-firstChild (r/render disabled synthetic-container)) 
                              (-> js/document
                                  (.getElementsByClassName "lnz")
                                  (aget 4)
                                  .-firstChild))
          pseudo-input (.-firstChild label)
          input (.-nextSibling pseudo-input)]
      (testing "Wrapper is a `<label>`?"
        (is (= (.-tagName label) "LABEL")))
      (testing "First child of label is an `<i>`?"
        (is (= (.-tagName pseudo-input) "I")))
      (testing "Real input is an `<input>`?" 
        (is (= (.-tagName input) "INPUT")))
      (testing "And input is of type `checkbox`" 
        (is (= (.-type input) "checkbox")))
      (testing "`<label>` contains 'Checkbox Disabled' as textContent?"
        (is (= (.-textContent label) "Checkbox Disabled")))
      (testing "`<input>` are disabled?"
        (is (.-disabled input))))))

(let [icon-without-text [:div.lnz [checkbox {:icon :i.fas.fa-cloud}]]]
  (defcard-rg card-checkbox-icon-without-text
    "
  ## Icon special attribute to add an icon (FontAwesome5)
  ```
  [checkbox {:icon :i.fas.fa-cloud}]
  ```
  produces in hiccup:
  ```
  [:label.lnz {:for \"random-uuid\"}                ;; Label wrapper
    [:i.lnz {:tab-index 0}]                       ;; Stylized Pseudo INPUT
    [:input {:id \"random-uuid\" :type \"checkbox\"}] ;; Real INPUT 
    [:span [:icon :i.fas.fa-cloud]]]              ;; Icon!
  "
    icon-without-text)

  (deftest test-checkbox-icon-without-text
    (let [label (adhoc-render (.-firstChild (r/render icon-without-text synthetic-container))
                              (-> js/document
                                  (.getElementsByClassName "lnz")
                                  (aget 5)
                                  .-firstChild))
          pseudo-input (.-firstChild label)
          input (.-nextSibling pseudo-input)
          span  (.-nextSibling input)
          icon  (.-firstChild span)]
      (testing "Wrapper is a `<label>`?"
        (is (= (.-tagName label) "LABEL")))
      (testing "First child of label is an `<i>`?"
        (is (= (.-tagName pseudo-input) "I")))
      (testing "Real input is an `<input>`?" 
        (is (= (.-tagName input) "INPUT")))
      (testing "And input is of type `checkbox`" 
        (is (= (.-type input) "checkbox")))
      (testing "`<label>` contains `<span>` as last child"
        (is (= (.-tagName span) "SPAN")))
      (testing "`<span>` contains an `<i>`"
        (is (= (.-tagName icon) "I")))
      (testing "`<label>` contains '' as textContent?"
        (is (= (.-textContent label) ""))))))

(let [icon-with-text [:div.lnz [checkbox {:icon :i.fab.fa-react} "React!"]]]
  (defcard-rg card-checkbox-icon-with-text
    "
  ## And with text
  ```
  [checkbox {:icon :i.fab.fa-react}]
  ```
  "
    icon-with-text)

  (deftest test-checkbox-icon-with-text
    (let [label (adhoc-render (.-firstChild (r/render icon-with-text synthetic-container))
                              (-> js/document
                                  (.getElementsByClassName "lnz")
                                  (aget 6)
                                  .-firstChild))
          pseudo-input (.-firstChild label)
          input (.-nextSibling pseudo-input)
          span  (.-nextSibling input)
          icon  (.-firstChild span)]
      (testing "Wrapper is a `<label>`?"
        (is (= (.-tagName label) "LABEL")))
      (testing "First child of label is an `<i>`?"
        (is (= (.-tagName pseudo-input) "I")))
      (testing "Real input is an `<input>`?" 
        (is (= (.-tagName input) "INPUT")))
      (testing "And input is of type `checkbox`" 
        (is (= (.-type input) "checkbox")))
      (testing "`<label>` contains `<span>` as last child"
        (is (= (.-tagName span) "SPAN")))
      (testing "`<span>` contains an `<i>`"
        (is (= (.-tagName icon) "I")))
      (testing "`<label>` contains 'cloud' as textContent?"
        (is (= (.-textContent label) "React!"))))))

(let [icon-with-text-and-description [:div.lnz
                                      [:div 
                                       [checkbox {:icon :i.fab.fa-react} 
                                        [:span "React" [:div [:small "In computing, React is a JavaScript library for building user interfaces. It is maintained by Facebook, Instagram and a community of individual developers and corporations."]]]]]
                                      [:div
                                       [checkbox {:icon :i.fab.fa-angular}
                                        [:span "Angular" [:div [:small "Angular is a TypeScript-based open-source front-end web application platform led by the Angular Team at Google and by a community of individuals and corporations. Angular is a complete rewrite from the same team that built AngularJS."]]]]]]]
  (defcard-rg card-checkbox-icon-with-text-and-description
    "
  ## With icon, text and description
  ```
  [:div
   [:div 
    [checkbox {:icon :i.fab.fa-react} 
     [:span \"React\" [:div [:small \"In computing, React is a JavaScript library for building user interfaces. It is maintained by Facebook, Instagram and a community of individual developers and corporations.\"]]]]]
   [:div
    [checkbox {:icon :i.fab.fa-angular}
     [:span \"Angular\" [:div [:small \"Angular is a TypeScript-based open-source front-end web application platform led by the Angular Team at Google and by a community of individuals and corporations.\"]]]]]]
  ```
  "
    icon-with-text-and-description)

  (deftest test-checkbox-icon-with-text-and-description
    (let [label (adhoc-render (-> (r/render icon-with-text-and-description synthetic-container)
                                  .-firstChild
                                  .-firstChild)
                              (-> js/document
                                  (.getElementsByClassName "lnz")
                                  (aget 7)
                                  .-firstChild
                                  .-firstChild))
          pseudo-input (.-firstChild label)
          input (.-nextSibling pseudo-input)
          span  (.-nextSibling input)
          icon  (.-firstChild span)]
      (testing "Wrapper is a `<label>`?"
        (is (= (.-tagName label) "LABEL")))
      (testing "First child of label is an `<i>`?"
        (is (= (.-tagName pseudo-input) "I")))
      (testing "Real input is an `<input>`?" 
        (is (= (.-tagName input) "INPUT")))
      (testing "And input is of type `checkbox`" 
        (is (= (.-type input) "checkbox")))
      (testing "`<label>` contains `<span>` as last child"
        (is (= (.-tagName span) "SPAN")))
      (testing "`<span>` contains an `<i>`"
        (is (= (.-tagName icon) "I")))
      (testing "`<label>` contains 'cloud' as textContent?"
        (is (clojure.string/starts-with? (.-textContent label) "React"))))))

