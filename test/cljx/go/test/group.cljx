(ns go.test.group
  (:require [go.group :as group]
            #+clj [clojure.test :as t :refer [is deftest with-test run-tests testing]]
            #+cljs [cemerick.cljs.test :as t :refer-macros [is deftest with-test run-tests testing test-var]]))

(deftest a-single-stone-generates-a-single-alive-group
  (is (= #{#{[:black [16 16]]}} (group/generate-intermediary-groups [[:black [16 16]]]))))

(deftest two-adjacent-stones-of-same-color-get-grouped-together
  (is (= #{#{[:black [16 16]]
             [:black [16 15]]}} (group/generate-intermediary-groups [[:black [16 16]] [:black [16 15]]]))))

(deftest two-adjacent-stones-of-different-colors-get-grouped-in-different-groups
  (is (= #{#{[:black [16 16]]}
           #{[:white [16 15]]}} (group/generate-intermediary-groups [[:black [16 16]] [:white [16 15]]]))))


(deftest surrounding-a-group-kills-it
  (is (= #{#{[:black [3 2]]}
           #{[:black [2 1]]}
           #{[:black [2 3]]}
           #{[:black [1 2]]}} (group/generate-surviving-groups #{[:black [3 2]]
                                                                 [:black [2 1]]
                                                                 [:black [1 2]]
                                                                 [:white [2 2]]} [:black [2 3]]))))

(deftest a-stone-surrounded-by-two-at-a-corner-dies
  (is (= #{#{[:black [1 2]]}
           #{[:black [2 1]]}} (group/generate-surviving-groups #{[:black [1 2]]
                                                                 [:white [1 1]]} [:black [2 1]])))
  (is (= #{#{[:black [19 18]]}
           #{[:black [18 19]]}} (group/generate-surviving-groups #{[:black [19 18]]
                                                                   [:white [19 19]]} [:black [18 19]]))))

(deftest a-stones-surrounded-by-three-at-a-edge-dies
         (is (= #{#{[:black [19 5]]}
                  #{[:black [18 6]]}
                  #{[:black [19 7]]}} (group/generate-surviving-groups #{[:black [19 5]]
                                                                         [:black [18 6]]
                                                                         [:white [19 6]]} [:black [19 7]]))))

(deftest multiple-stones-can-be-captured-at-once
  (is (= #{#{[:black [1 2]]
             [:black [2 2]]}
           #{[:black [3 1]]}} (group/generate-surviving-groups #{[:black [1 2]]
                                                                 [:black [2 2]]
                                                                 [:white [1 1]]
                                                                 [:white [2 1]]} [:black [3 1]]))))

(deftest putting-a-stone-in-a-place-completely-surrounded-is-a-suicide
  (is (= #{#{[:black [3 2]]}
           #{[:black [2 1]]}
           #{[:black [2 3]]}
           #{[:black [1 2]]}} (group/generate-surviving-groups #{[:black [3 2]]
                                                                 [:black [2 1]]
                                                                 [:black [1 2]]
                                                                 [:black [2 3]]} [:white [2 2]]))))

(deftest can-capture-with-a-throw-in-and-stay-alive
  (is (= #{#{[:black [2 1]]}
           #{[:white [1 1]]}
           #{[:white [2 2]]}
           #{[:white [1 3]]}} (group/generate-surviving-groups #{[:black [1 2]]
                                                                 [:black [2 1]]
                                                                 [:white [2 2]]
                                                                 [:white [1 3]]} [:white [1 1]]))))

(deftest multiple-groups-can-be-captured-with-a-throw-in
  (is (= #{#{[:white [3 1]]}
           #{[:white [1 1]]}
           #{[:white [2 2]]}
           #{[:white [1 3]]}} (group/generate-surviving-groups #{[:black [1 2]]
                                                                 [:black [2 1]]
                                                                 [:white [2 2]]
                                                                 [:white [1 3]]
                                                                 [:white [3 1]]} [:white [1 1]]))))

(deftest can-capture-with-a-throw-in-of-multiple-stones
  (is (= #{#{[:black [2 1]]}
           #{[:black [1 2]]
             [:black [1 3]]}
           #{[:white [2 2]]
             [:white [2 3]]}
           #{[:white [1 4]]}} (group/generate-surviving-groups #{[:black [2 1]]
                                                                 [:black [1 3]]
                                                                 [:white [1 1]]
                                                                 [:white [2 2]]
                                                                 [:white [2 3]]
                                                                 [:white [1 4]]} [:black [1 2]]))))

