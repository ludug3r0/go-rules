(ns go.test.game
  (:require [go.game :as game]
            [go.schema :as s]
            #+clj [clojure.test :as t :refer [is deftest with-test run-tests testing]]
            #+cljs [cemerick.cljs.test :as t :refer-macros [is deftest with-test run-tests testing test-var]]))

(def invalid? (comp not game/valid?))

(deftest a-game-can-have-no-moves
  (is (game/valid? [])))

(deftest first-stone-played-should-be-black
  (is (game/valid? [[:black [16 16]]])))

(deftest white-cannot-place-the-first-stone
  (is (invalid? [[:white [4 4]]])))

(deftest white-can-play-after-black
  (is (game/valid? [[:black [16 16]]
                    [:white [4 4]]])))

(deftest players-alternate-their-turns
  (is (game/valid? [[:black [16 16]]
                    [:white [4 4]]
                    [:black [16 4]]
                    [:white [4 16]]])))

(deftest no-stone-can-be-put-where-other-already-is
  (is (invalid? [[:black [16 16]]
                 [:white [16 16]]])))

(deftest two-players-can-both-pass-in-a-row
  (is (game/valid? [[:black [16 16]]
                    [:white [4 4]]
                    [:black :pass]
                    [:white :pass]])))


(deftest an-empty-game-has-an-empty-configuration
  (is (= #{} (game/configuration []))))

(deftest can-generate-a-configuration-for-the-placemente-of-a-single-stone
  (is (= #{[:black [16 16]]} (game/configuration [[:black [16 16]]]))))

(deftest an-empty-board-has-361-valid-placements
  (is (= 361 (count (game/valid-placements [])))))

(deftest after-the-first-move-board-has-360-valid-placements
  (is (= 360 (count (game/valid-placements [[:black [16 16]]])))))

(deftest an-empty-board-has-361-empty-vertices
  (is (= 361 (count (game/empty-vertices [])))))

(deftest after-the-first-move-board-has-360-empty-vertices
  (is (= 360 (count (game/empty-vertices [[:black [16 16]]])))))

(deftest first-player-is-black
  (is (= :black (game/current-player-color []))))

(deftest after-black-move-its-whites-turn
  (is (= :white (game/current-player-color [[:black [16 16]]]))))

(deftest after-white-move-its-blacks-turn
  (is (= :black (game/current-player-color [[:black [16 16]] [:white :pass]]))))





