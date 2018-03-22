(ns sudoko.board
  (:require [ysera.test :refer [is is-not is=]]
            [clojure.set :as set]))


(defn get-row
  {:test (fn []
           (is= (-> #{{:x 0 :y 0} {:x 0 :y 2} {:x 2 :y 0} {:x 2 :y 2} {:x 4 :y 0}}
                    (get-row 0))
                #{{:x 0 :y 0} {:x 2 :y 0} {:x 4 :y 0}}))}
  [board row]
  (reduce (fn [row-set cell]
            (if (= (:y cell) row)
              (conj row-set cell)
              row-set))
          #{}
          board))

(defn get-column
  {:test (fn []
           (is= (-> #{{:x 0 :y 0} {:x 0 :y 2} {:x 2 :y 0} {:x 2 :y 2} {:x 4 :y 0}}
                    (get-column 2))
                #{{:x 2 :y 0} {:x 2 :y 2}}))}
  [board column]
  (reduce (fn [column-set cell]
            (if (= (:x cell) column)
              (conj column-set cell)
              column-set))
          #{}
          board))

(defn get-quadrant
  {:test (fn []
           (is= (-> #{{:x 0 :y 4} {:x 0 :y 2} {:x 2 :y 0} {:x 2 :y 2} {:x 4 :y 0}}
                    (get-quadrant 0 0))
                #{{:x 0 :y 2} {:x 2 :y 0} {:x 2 :y 2}}))}
  [board row column]
  (let [row-base (* 3 row)
        column-base (* 3 column)
        row-set #{row-base (+ row-base 1) (+ row-base 2)}
        column-set #{column-base (+ column-base 1) (+ column-base 2)}]
    (reduce (fn [quad-set cell]
              (if (and (contains? row-set (:x cell))
                       (contains? column-set (:y cell)))
                (conj quad-set cell)
                quad-set))
            #{}
            board)))

(defn from-row
  {:test (fn []
           (is= (from-row 0 "12")
                #{{:x 0 :y 0 :v 1} {:x 1 :y 0 :v 2}}))}
  [y row]
  (->> row
       (map-indexed (fn [x c]
                      {:x x :y y :v (- (int c) (int \0))}))
       (into #{})))
;
(defn from
  {:test (fn []
           (is= (from "12" "34")
                #{{:x 0 :y 0 :v 1} {:x 1 :y 0 :v 2}
                  {:x 0 :y 1 :v 3} {:x 1 :y 1 :v 4}})
           (is= (from "172549683"
                      "645873219"
                      "389261745"
                      "496327851"
                      "813456972"
                      "257198436"
                      "964715328"
                      "731682594"
                      "528934167")
                #{{:x 2, :y 1, :v 5} {:x 8, :y 6, :v 8} {:x 5, :y 7, :v 2} {:x 7, :y 2, :v 4} {:x 5, :y 4, :v 6}
                  {:x 8, :y 4, :v 2} {:x 4, :y 1, :v 7} {:x 8, :y 7, :v 4} {:x 7, :y 0, :v 8} {:x 1, :y 4, :v 1}
                  {:x 5, :y 0, :v 9} {:x 7, :y 8, :v 6} {:x 1, :y 5, :v 5} {:x 3, :y 4, :v 4} {:x 6, :y 7, :v 5}
                  {:x 7, :y 5, :v 3} {:x 4, :y 8, :v 3} {:x 0, :y 7, :v 7} {:x 6, :y 3, :v 8} {:x 4, :y 6, :v 1}
                  {:x 1, :y 7, :v 3} {:x 2, :y 4, :v 3} {:x 4, :y 4, :v 5} {:x 3, :y 1, :v 8} {:x 4, :y 0, :v 4}
                  {:x 3, :y 3, :v 3} {:x 2, :y 6, :v 4} {:x 7, :y 3, :v 5} {:x 0, :y 5, :v 2} {:x 0, :y 3, :v 4}
                  {:x 2, :y 0, :v 2} {:x 3, :y 8, :v 9} {:x 6, :y 6, :v 3} {:x 6, :y 4, :v 9} {:x 5, :y 1, :v 3}
                  {:x 2, :y 7, :v 1} {:x 0, :y 4, :v 8} {:x 0, :y 2, :v 3} {:x 3, :y 0, :v 5} {:x 4, :y 5, :v 9}
                  {:x 2, :y 8, :v 8} {:x 4, :y 7, :v 8} {:x 4, :y 3, :v 2} {:x 7, :y 7, :v 9} {:x 0, :y 8, :v 5}
                  {:x 3, :y 2, :v 2} {:x 4, :y 2, :v 6} {:x 1, :y 3, :v 9} {:x 8, :y 8, :v 7} {:x 6, :y 8, :v 1}
                  {:x 8, :y 5, :v 6} {:x 0, :y 0, :v 1} {:x 6, :y 2, :v 7} {:x 7, :y 1, :v 1} {:x 0, :y 6, :v 9}
                  {:x 2, :y 2, :v 9} {:x 1, :y 2, :v 8} {:x 7, :y 6, :v 2} {:x 1, :y 6, :v 6} {:x 0, :y 1, :v 6}
                  {:x 6, :y 1, :v 2} {:x 5, :y 3, :v 7} {:x 8, :y 1, :v 9} {:x 2, :y 5, :v 7} {:x 3, :y 5, :v 1}
                  {:x 5, :y 6, :v 5} {:x 7, :y 4, :v 7} {:x 8, :y 3, :v 1} {:x 1, :y 8, :v 2} {:x 1, :y 1, :v 4}
                  {:x 5, :y 5, :v 8} {:x 6, :y 5, :v 4} {:x 3, :y 6, :v 7} {:x 8, :y 2, :v 5} {:x 5, :y 8, :v 4}
                  {:x 2, :y 3, :v 6} {:x 5, :y 2, :v 1} {:x 8, :y 0, :v 3} {:x 6, :y 0, :v 6} {:x 3, :y 7, :v 6}
                  {:x 1, :y 0, :v 7}}))}
  [& rows]
  (loop [y 0
         r rows
         result #{}]
    (if (empty? r)
      result
      (recur (inc y)
             (rest r)
             (set/union result (from-row y (first r)))))))
