(defproject org.clojars.ludug3r0/go-rules "0.0.2-SNAPSHOT"
            :description "A Clojure library designed to validate go games."
            :url "https://github.com/ludug3r0/go-rules"
            :license {:name "Eclipse Public License"
                      :url  "http://www.eclipse.org/legal/epl-v10.html"}
            :dependencies [[org.clojure/clojure "1.6.0"]
                           [org.clojars.ludug3r0/go-schema "0.0.2-SNAPSHOT"]
                           [prismatic/schema "0.3.3"]]
            :scm {:name "git"
                  :url  "https://github.com/ludug3r0/go-rules"}
            :profiles {:dev        {:dependencies [[expectations "2.0.9"]]
                                    :plugins [[lein-expectations "0.0.7"]]}})