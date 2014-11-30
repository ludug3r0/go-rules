(ns go.test.game
  (:require [go.game :refer :all]
            [expectations :refer [expect]]
            [go.schema :as s]))

(def invalid? (comp not valid?))

;; a game can have no moves
(expect valid? [])

;; first stone played should be black's
(expect valid? [[:black [16 16]]])

;; white cannot play the first stone
(expect invalid? [[:white [4 4]]])

;; white can play the after black
(expect valid? [[:black [16 16]]
                [:white [4 4]]])

;; players alternate their turns
(expect valid? [[:black [16 16]]
                [:white [4 4]]
                [:black [16 4]]
                [:white [4 16]]])


;; can generate a configuration for a empty game
(expect #{} (configuration []))

;; can generate a configuration for the placement of a single stone
(expect #{[:black [16 16]]} (configuration [[:black [16 16]]]))



