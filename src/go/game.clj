(ns go.game
  (:require [go.models :as m]
            [schema.core :as s]
            [go.schema :as schema]
            [go.rules :as rules]))

(s/defn configuration :- schema/configuration
  [game :- schema/game]
  (let [stones (m/stones game)]
    (rules/surviving-stones stones)))

(s/defn valid? :- schema/game
  [game :- schema/game]
  (when (rules/alternating-colors? game)
    ;;TODO: super-ko rule
    game))
