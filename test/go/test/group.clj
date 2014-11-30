(ns go.test.group
  (:require [go.group :refer :all]
            [expectations :refer [expect]]))

;; a single stone generatas a single alive group
(expect #{#{[:black [16 16]]}} (generate-intermediary-groups [[:black [16 16]]]))

;; two adjacent stones of the same color get grouped together in a single group
(expect #{#{[:black [16 16]]
            [:black [16 15]]}} (generate-intermediary-groups [[:black [16 16]] [:black [16 15]]]))

;; two adjacent stones of different colors get grouped into two groups
(expect #{#{[:black [16 16]]}
          #{[:white [16 15]]}} (generate-intermediary-groups [[:black [16 16]] [:white [16 15]]]))


;; surrounding a group kills it
(expect #{#{[:black [3 2]]}
          #{[:black [2 1]]}
          #{[:black [2 3]]}
          #{[:black [1 2]]}} (generate-surviving-groups #{[:black [3 2]]
                                                      [:black [2 1]]
                                                      [:black [1 2]]
                                                      [:white [2 2]]} [:black [2 3]]))

;; capture at the corners works
(expect #{#{[:black [1 2]]}
          #{[:black [2 1]]}} (generate-surviving-groups #{[:black [1 2]]
                                                      [:white [1 1]]} [:black [2 1]]))
(expect #{#{[:black [19 18]]}
          #{[:black [18 19]]}} (generate-surviving-groups #{[:black [19 18]]
                                                        [:white [19 19]]} [:black [18 19]]))

;; capture at the edge works
(expect #{#{[:black [19 5]]}
          #{[:black [18 6]]}
          #{[:black [19 7]]}} (generate-surviving-groups #{[:black [19 5]]
                                                       [:black [18 6]]
                                                       [:white [19 6]]} [:black [19 7]]))

;; multi stone capture works
(expect #{#{[:black [1 2]]
            [:black [2 2]]}
          #{[:black [3 1]]}} (generate-surviving-groups #{[:black [1 2]]
                                                      [:black [2 2]]
                                                      [:white [1 1]]
                                                      [:white [2 1]]} [:black [3 1]]))

;; putting a stone in a place surrounded is a suicide
(expect #{#{[:black [3 2]]}
          #{[:black [2 1]]}
          #{[:black [2 3]]}
          #{[:black [1 2]]}} (generate-surviving-groups #{[:black [3 2]]
                                                      [:black [2 1]]
                                                      [:black [1 2]]
                                                      [:black [2 3]]} [:white [2 2]]))

;; can capture with a throw in and stay alive
(expect #{#{[:black [2 1]]}
          #{[:white [1 1]]}
          #{[:white [2 2]]}
          #{[:white [1 3]]}} (generate-surviving-groups #{[:black [1 2]]
                                                      [:black [2 1]]
                                                      [:white [2 2]]
                                                      [:white [1 3]]} [:white [1 1]]))

;; can capture multiple groups with a throw in
(expect #{#{[:white [3 1]]}
          #{[:white [1 1]]}
          #{[:white [2 2]]}
          #{[:white [1 3]]}} (generate-surviving-groups #{[:black [1 2]]
                                                      [:black [2 1]]
                                                      [:white [2 2]]
                                                      [:white [1 3]]
                                                      [:white [3 1]]} [:white [1 1]]))

;; can capture in a throw in group of multiple stones
(expect #{
          #{[:black [2 1]]}
          #{[:black [1 2]]
            [:black [1 3]]}
          #{[:white [2 2]]
            [:white [2 3]]}
          #{[:white [1 4]]}} (generate-surviving-groups #{[:black [2 1]]
                                                      [:black [1 3]]
                                                      [:white [1 1]]
                                                      [:white [2 2]]
                                                      [:white [2 3]]
                                                      [:white [1 4]]} [:black [1 2]]))

