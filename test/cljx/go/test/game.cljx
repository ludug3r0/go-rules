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


(deftest an-empty-game-has-an-empty-configuration
  (is (= #{} (game/configuration []))))

(deftest can-generate-a-configuration-for-the-placemente-of-a-single-stone
  (is (= #{[:black [16 16]]} (game/configuration [[:black [16 16]]]))))



