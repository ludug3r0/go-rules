(ns go.test.rules
  (:require [go.rules :refer :all]
            [expectations :refer [expect]]))

(def not-alternating-colors? (comp not alternating-colors?))

;; colors alternate
(expect alternating-colors? [[:black [16 16]]
                             [:white [4 4]]
                             [:black [16 4]]])

;; white cannot start
(expect not-alternating-colors? [[:white [16 16]]])

;; a player cannot place two stones in a row
(expect not-alternating-colors? [[:black [16 16]]
                                 [:black [4 4]]])

;; a player cannot pass after playing a stone
(expect not-alternating-colors? [[:black [16 16]]
                                 [:black :pass]])

;; a player cannot pass twice in a row
(expect not-alternating-colors? [[:black :pass]
                                 [:black :pass]])

;; a player cannot place a stone after passing
(expect not-alternating-colors? [[:black :pass]
                                 [:black [16 16]]])



;; a single stone is alive
(expect #{[:black [16 16]]} (surviving-stones [[:black [16 16]]]))

;; cannot place a stone where another is already placed
(expect Exception (surviving-stones [[:black [16 16]] [:white [16 16]]]))

;; two stones survive in the board
(expect #{[:black [16 16]]
          [:white [4 4]]}
        (surviving-stones [[:black [16 16]] [:white [4 4]]]))

;; surronding a single stone with enemy stones kills it
(expect #{[:black [3 2]]
          [:black [2 1]]
          [:black [2 3]]
          [:black [1 2]]}
        (surviving-stones [[:black [3 2]]
                           [:black [2 1]]
                           [:black [2 3]]
                           [:white [2 2]]
                           [:black [1 2]]]))