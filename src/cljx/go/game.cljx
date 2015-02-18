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


(s/defn current-player-color :- [schema/color]
  [game :- schema/game]
  (let [last-move (last game)
        last-player-color (m/color last-move)]
    (if (and last-player-color
             (= last-player-color :black))
      :white
      :black)))

(s/defn empty-placements :- [schema/placement]
  [game :- schema/game]
  (let [occupied-vertices (set (map m/move (configuration game)))
        current-player-color (current-player-color game)]
    (for [line (range 1 20)
          column (range 1 20)
          :let [vertex [line column]]
          :when ((comp not contains?) occupied-vertices vertex)]
      [current-player-color vertex])))


(s/defn valid-placements :- [schema/placement]
  [game :- schema/game]
  (filter (fn [placement]
            (let [possible-game (conj game placement)]
              (and (valid? possible-game)
                   (configuration possible-game))))
          (empty-placements game)))
