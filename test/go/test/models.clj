(ns go.test.models
  (:require [go.models :refer :all]
            [expectations :refer [expect]]))

;; can get stone color
(expect :black (stone-color [:black [15 13]]))
(expect :white (stone-color [:white [1 1]]))

;; can get move color
(expect :black (move-color [:black :pass]))
(expect :black (move-color [:black [13 19]]))

;; can get the stone vertex
(expect [13 1] (stone-vertex [:white [13 1]]))

;;moves
(expect [[:black [3 4]] [:white [7 4]] [:black :pass]]
        (moves [[:black [3 4]] [:white [7 4]] [:black :pass]]))

;;stones
(expect [[:black [3 4]] [:white [7 4]]]
        (stones [[:black [3 4]] [:white [7 4]] [:black :pass]]))



