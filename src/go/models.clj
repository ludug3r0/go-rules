(ns go.models
  (:require [schema.core :as s]
            [go.schema :as schema]))

(defn- valid?
  [schema value]
  (when-not (s/check schema value)
    value))

(s/defn stone-color :- schema/color
  [stone :- schema/stone]
  (first stone))

(s/defn move-color :- schema/color
  [move :- schema/move]
  (first move))

(s/defn stone-vertex :- schema/vertex
  [stone :- schema/stone]
  (second stone))

(s/defn moves :- [schema/move]
  [game :- schema/game]
  (filter (partial valid? schema/move) game))

(s/defn stones :- [schema/stone]
  [game :- schema/game]
  (filter (partial valid? schema/stone) game))

