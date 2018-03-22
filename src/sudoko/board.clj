(ns sudoko.board
  (:require [ysera.test :refer [is is-not is=]]))


(defn get-row
  {:test (fn []
           (is= (-> '({:x 0 :y 0} {:x 0 :y 2} {:x 2 :y 0} {:x 2 :y 2} {:x 4 :y 0})
                    (get-row 0))
                '({:x 0 :y 0} {:x 2 :y 0} {:x 4 :y 0})))}
  [board row]
  (->> board
       (filter (fn [n]
                 (when
                   (= (:y n) row)
                   n)))))

(defn get-column
  {:test (fn []
           (is= (-> '({:x 0 :y 0} {:x 0 :y 2} {:x 2 :y 0} {:x 2 :y 2} {:x 4 :y 0})
                    (get-column 2))
                '({:x 2 :y 0} {:x 2 :y 2})))}
  [board column]
  (->> board
       (filter (fn [n]
                 (when
                   (= (:x n) column)
                   n)))))

(defn get-quadrant
  {:test (fn []
           (is= (-> '({:x 0 :y 4} {:x 0 :y 2} {:x 2 :y 0} {:x 2 :y 2} {:x 4 :y 0})
                    (get-quadrant 0 0))
                '({:x 0 :y 2} {:x 2 :y 0} {:x 2 :y 2})))}
  [board row column]
  (let [row-base (* 3 row)
        column-base (* 3 column)
        row-set #{row-base (+ row-base 1) (+ row-base 2)}
        column-set #{column-base (+ column-base 1) (+ column-base 2)}]
    (->> board
          (filter (fn [n]
                    (when (and (contains? row-set (:x n))
                               (contains? column-set (:y n)))
                      n))))))

