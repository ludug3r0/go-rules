(defproject org.clojars.ludug3r0/go-rules "0.0.2-SNAPSHOT"
            :description "A Clojure library designed to validate go games."
            :url "https://github.com/ludug3r0/go-rules"
            :license {:name "Eclipse Public License"
                      :url  "http://www.eclipse.org/legal/epl-v10.html"}
            :dependencies [[prismatic/schema "0.3.3"]
                           [org.clojars.ludug3r0/go-schema "0.0.4-SNAPSHOT"]]
            :scm {:name "git"
                  :url  "https://github.com/ludug3r0/go-rules"}
            :profiles {:dev {:dependencies [[org.clojure/clojure "1.6.0"]
                                            [org.clojure/clojurescript "0.0-2411"]
                                            [com.keminglabs/cljx "0.4.0" :exclusions [org.clojure/clojure]]]
                             :plugins      [[com.keminglabs/cljx "0.4.0" :exclusions [org.clojure/clojure]]
                                            [lein-cljsbuild "1.0.3"]
                                            [com.cemerick/clojurescript.test "0.3.3"]]}}
            :source-paths ["tmp/generated/src/clj" "tmp/generated/src/cljs"]
            :test-paths ["tmp/generated/test/clj"]
            :cljx {:builds [{:source-paths ["src/cljx"]
                             :output-path  "tmp/generated/src/clj"
                             :rules        :clj}
                            {:source-paths ["src/cljx"]
                             :output-path  "tmp/generated/src/cljs"
                             :rules        :cljs}
                            {:source-paths ["test/cljx"]
                             :output-path  "tmp/generated/test/clj"
                             :rules        :clj}
                            {:source-paths ["test/cljx"]
                             :output-path  "tmp/generated/test/cljs"
                             :rules        :cljs}]}
            :cljsbuild {:builds        [{:source-paths ["tmp/generated/src/cljs" "tmp/generated/test/cljs"]
                                         :compiler     {:output-to     "tmp/generated/js/test/testable.js"
                                                        :optimizations :whitespace
                                                        :pretty-print  true}}]
                        :test-commands {"unit-tests" ["phantomjs" :runner
                                                      "tmp/generated/js/test/testable.js"]}}
            :aliases {"deploy" ["do" "clean," "cljx" "once," "deploy" "clojars"]
                      "test"   ["do" "clean," "cljx" "once," "test," "with-profile" "dev" "cljsbuild" "test"]})