(defproject cognicious/lienzo "0.0.1-SNAPSHOT"
  :description ""
  :url "https://github.com/cognicious/lienzo"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  
  :dependencies [[cljsjs/material "1.3.0-0"]
                 [cljsjs/dialog-polyfill "0.4.3-0"]
                 [reagent "0.6.0-rc" :exclusions [cljsjs/react]]
                 [cljsjs/react-with-addons "15.2.1-1"]
                 
                 
                 [lein-doo "0.1.10"]
                 [devcards "0.2.4"]
                 
                 [garden "1.3.5"]]
  
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.15"]
            [lein-doo "0.1.10"]
            [lein-sass "0.5.0"]
            [lein-garden "0.3.0"]]
  
  :profiles {:dev {:dependencies [[prismatic/dommy "1.1.0"]
                                  [cljs-react-test "0.1.4-SNAPSHOT"
                                   :exclusions [cljsjs/react-with-addons
                                                org.clojure/core.async
                                                org.clojure/tools.analyzer.jvm
                                                ]]
                                  [cljsjs/react-dom "15.2.1-1" :exclusions [cljsjs/react]]
                                  [org.clojure/clojure "1.8.0"]
                                  [org.clojure/clojurescript "1.9.93"]
                                  [figwheel-sidecar "0.5.4-7"]]}}
  :source-paths ["src/cljs"]
  
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target" "out"]
  
  :sass {:src "src/sass"
         :output-directory "resources/public/css"}

  :garden {:builds [{;; Optional name of the build:
                     :id "screen"
                     ;; Source paths where the stylesheet source code is
                     :source-paths ["src/styles"]
                     ;; The var containing your stylesheet:
                     :stylesheet lienzo.css/screen
                     ;; Compiler flags passed to `garden.core/css`:
                     :compiler {;; Where to save the file:
                                :output-to "resources/public/css/screen.css"
                                ;; Compress the output?
                                :pretty-print? true}}]}

  :cljsbuild {
              :builds [{:id "dev"
                        :source-paths ["src/cljs" "demo/src/cljs"]
                        :figwheel { :on-jsload "lienzo.demo/on-js-reload" }
                        :compiler {:main lienzo.demo
                                   :asset-path "js/compiled/out"
                                   :output-to "resources/public/js/compiled/lienzo.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :source-map-timestamp true}}
                       {:id "demo"
                        :source-paths ["src/cljs" "demo/src/cljs"]
                        ;:figwheel { :on-jsload "lienzo.demo/on-js-reload" }
                        :compiler {:main lienzo.demo
                                   :asset-path "js/compiled/out"
                                   :output-to "resources/public/js/compiled/lienzo.js"
                                   :optimizations :advanced
                                   :pretty-print false}}

                       {:id "devcards-test"
                        :source-paths ["src/cljs" "test/cljs"]
                        :figwheel {:devcards true}
                        :compiler {:main lienzo.tests
                                   :optimizations :none
                                   :asset-path "js/tests/out"
                                   :output-to "resources/public/js/tests/all-tests.js"
                                   :output-dir "resources/public/js/tests/out"
                                   :source-map-timestamp true}}
                       
                       #_{:id "min"
                        :source-paths ["src/cljc"]
                        :compiler {:output-to "target/js/re_mdl.js"
                                   :main re-mdl.core
                                   :optimizations :advanced
                                   :pretty-print false}}
                       
                       #_{:id "test"
                        :source-paths ["src/cljc" "src-dev" "test/cljs"]
                          :compiler {:output-to "resources/public/js/compiled/test.js"
                                     :main re-mdl.runner
                                     :optimizations :none}}]} 

  :figwheel {
             ;; :http-server-root "public" ;; default and assumes "resources"
             ;; :server-port 3449 ;; default
             ;; :server-ip "127.0.0.1"
             :server-ip   "0.0.0.0"
             :css-dirs ["resources/public/css"] ;; watch and update CSS

             ;; Start an nREPL server into the running figwheel process
             ;; :nrepl-port 7888

             ;; Server Ring Handler (optional)
             ;; if you want to embed a ring handler into the figwheel http-kit
             ;; server, this is for simple ring servers, if this
             ;; doesn't work for you just run your own server :)
             ;; :ring-handler hello_world.server/handler

             ;; To be able to open files in your editor from the heads up display
             ;; you will need to put a script on your path.
             ;; that script will have to take a file path and a line number
             ;; ie. in  ~/bin/myfile-opener
             ;; #! /bin/sh
             ;; emacsclient -n +$2 $1
             ;;
             ;; :open-file-command "myfile-opener"

             ;; if you want to disable the REPL
             ;; :repl false

             ;; to configure a different figwheel logfile path
             ;; :server-logfile "tmp/logs/figwheel-logfile.log"
             :readline false
             })
