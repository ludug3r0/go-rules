(ns go.test.rules
  (:require [go.rules :as rules]
            #+clj [clojure.test :as t :refer [is deftest with-test run-tests testing]]
            #+cljs [cemerick.cljs.test :as t :refer-macros [is deftest with-test run-tests testing test-var]]))

(def not-alternating-colors? (comp not rules/alternating-colors?))

(deftest colors-alternate
  (is (rules/alternating-colors? [[:black [16 16]]
                                  [:white [4 4]]
                                  [:black [16 4]]])))

(deftest white-cannot-start
  (is (not-alternating-colors? [[:white [16 16]]])))

(deftest a-player-cannot-place-two-stones-in-a-row
  (is (not-alternating-colors? [[:black [16 16]]
                                [:black [4 4]]])))

(deftest a-player-cannot-pass-after-playing-a-stone
  (is (not-alternating-colors? [[:black [16 16]]
                                [:black :pass]])))

(deftest a-player-canot-pass-twice-in-a-row
  (is (not-alternating-colors? [[:black :pass]
                                [:black :pass]])))

(deftest a-player-cannot-place-a-stone-after-passing
  (is (not-alternating-colors? [[:black :pass]
                                [:black [16 16]]])))



(deftest a-single-stone-is-alive
  (is (= #{[:black [16 16]]} (rules/surviving-stones [[:black [16 16]]]))))

(deftest cannot-place-a-stone-where-another-is-already-placed
  (is (thrown? #+clj Exception #+cljs js/Error (rules/surviving-stones [[:black [16 16]] [:white [16 16]]])))
  (is (= nil (rules/stones-dont-overlap? [[:black [16 16]] [:white [16 16]]]))))

(deftest two-stones-survive-in-the-board
  (is (= #{[:black [16 16]]
           [:white [4 4]]}
         (rules/surviving-stones [[:black [16 16]] [:white [4 4]]]))))

(deftest when-a-stone-is-surrounded-it-gets-killed
  (is (= #{[:black [3 2]]
           [:black [2 1]]
           [:black [2 3]]
           [:black [1 2]]}
         (rules/surviving-stones [[:black [3 2]]
                                  [:black [2 1]]
                                  [:black [2 3]]
                                  [:white [2 2]]
                                  [:black [1 2]]]))))