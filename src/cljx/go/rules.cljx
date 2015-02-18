(ns go.rules
  (:require [go.schema :as s]
            [go.group :as group]
            [clojure.set :as set]
            [go.models :as m]))

(defn- overlapping-stone?
  [stones stone]
  (let [stones-vertices (into #{} (map m/move stones))
        stone-vertex (m/move stone)]
    (contains? stones-vertices stone-vertex)))

(defn- place-stone
  [configuration stone]
  (if (overlapping-stone? configuration stone)
    (throw (ex-info "trying to place stone where another exist" {:configuration configuration :stone stone})))
  (apply set/union (group/generate-surviving-groups configuration stone)))

(def fast-place-stone (memoize place-stone))

(defn surviving-stones
  [stones]
  (reduce fast-place-stone #{} stones))

(defn stones-dont-overlap?
  [stones]
  (try
    (surviving-stones stones)
    (catch #+clj Exception #+cljs js/Error e
                                           nil)))

(defn alternating-colors?
  [moves]
  (let [actual-colors (map m/color moves)
        expected-colors (take (count actual-colors) (cycle [:black :white]))]
    (= actual-colors expected-colors)))