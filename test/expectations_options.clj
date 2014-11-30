(ns expectations-options
  (:require [schema.core :as s]))

(defn enable-prismatic-schema-checks
  "enable prismatic schema function argument and return checking"
  {:expectations-options :in-context}
  [work]
  (s/with-fn-validation
    (work)))