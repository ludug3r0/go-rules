(ns go.game
  (:require [go.models :as m]
            [schema.core :as s :include-macros true]
            [go.schema :as schema]
            [go.rules :as rules]))

(s/defn configuration :- schema/configuration
  [game :- schema/game]
  (let [stones (m/stones game)]
    (rules/surviving-stones stones)))

(s/defn valid? :- schema/game
  [game :- schema/game]
  (when (and
          (rules/alternating-colors? game)
          (rules/stones-dont-overlap? (m/stones game)))
    ;;TODO: super-ko rule
    game))

(s/defn possible-placements :- [schema/placement]
  [game :- schema/game]
  (filter (fn [placement]
            (let [possible-game (conj game placement)]
              (and (valid? possible-game)
                   (configuration possible-game))))
          (for [color [:black :white]
                line (range 1 20)
                column (range 1 20)]
            [color [line column]])))
