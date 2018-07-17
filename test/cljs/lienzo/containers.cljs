(ns lienzo.containers)

(def ^:dynamic synthetic-container)

(defn bind-synthetic-container [test-fn]
  (binding [synthetic-container (let [id (str "container-" (gensym))
                                      node (.createElement js/document "div")
                                      _ (set! (.-id node) id)
                                      _ (.appendChild (.-body js/document) node)]
                                  (.getElementById js/document id))]
    (test-fn)
    (.unmountComponentAtNode js/ReactDOM synthetic-container)))
