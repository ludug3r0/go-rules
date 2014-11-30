(ns go.schema
  (:require [schema.core :as s]))

(def color
  (s/enum :black :white))

(def coordinate
  (let [positions (range 1 20)]
    (apply s/enum positions)))

(def vertex
  [(s/one coordinate "column") (s/one coordinate "line")])

(def stone
  [(s/one color "color") (s/one vertex "vertex")])

(def pass
  [(s/one color "color") (s/one (s/eq :pass) "pass")])

(def move
  (s/either stone pass))

(def game
  [move])

(def configuration
  #{stone})



