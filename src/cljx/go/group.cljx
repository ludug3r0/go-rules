(ns go.group
  (:require [go.models :as m]
            [clojure.set :as set]))


(defn- group-color
  [group]
  (-> group first m/color))

(defn- neighbour-coordinates
  [coordinate]
  (case coordinate
    1 [2]
    19 [18]
    [(dec coordinate) (inc coordinate)]))

(defn- stone-adjacent-vertices
  [stone]
  (let [[column line] (m/move stone)]
    (-> (concat (map (fn [column] [column line]) (neighbour-coordinates column))
                (map (fn [line] [column line]) (neighbour-coordinates line)))
        set)))

(defn- group-vertices
  [group]
  (set (map m/move group)))

(defn- adjacent-to-stone?
  [stone group]
  (when (= (m/color stone) (group-color group))
    (let [stone-adjacent-vertices (stone-adjacent-vertices stone)
          group-vertices (group-vertices group)]
      (not-empty (set/intersection stone-adjacent-vertices group-vertices)))))

(defn- merging-into-groups
  [groups stone]
  (let [adjacent-groups (set/select (partial adjacent-to-stone? stone) groups)
        separated-groups (set/difference groups adjacent-groups)
        merged-group (apply set/union #{stone} adjacent-groups)]
    (conj separated-groups merged-group)))

(defn- has-empty-adjacent-vertex?
  [stones stone]
  (let [stones-vertices (set (map m/move stones))
        stone-adjacent-vertices (stone-adjacent-vertices stone)]
    (not-every? (partial contains? stones-vertices) stone-adjacent-vertices)))

(defn- group-has-liberties?
  [stones group]
  (some (partial has-empty-adjacent-vertex? stones) group))

(defn generate-intermediary-groups
  [stones]
  (reduce merging-into-groups #{} stones))

(defn generate-surviving-groups
  [stones stone]
  (let [all-stones (conj stones stone)
        intermediary-groups (generate-intermediary-groups all-stones)
        alive-groups (set/select (partial group-has-liberties? all-stones) intermediary-groups)
        dead-groups (set/difference intermediary-groups alive-groups)
        throw-in-group (set/select #(contains? % stone) dead-groups)]
    (if (< 1 (count dead-groups))
      (set/union alive-groups throw-in-group)
      alive-groups)))