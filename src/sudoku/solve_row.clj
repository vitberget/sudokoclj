(ns sudoku.solve-row
  (:require [clojure.set :as set]
            [sudoku.board :as board]
            [sudoku.check :as check]
            [ysera.test :refer [is is-not is=]]))

(defn fill-one-missing-digit
  {:test (fn []
           (is= (fill-one-missing-digit (board/from "123456789") 0 2)
                (board/from "123456789"))
           (is= (fill-one-missing-digit (board/from "1 3456789") 0 2)
                (board/from "123456789"))
           (is= (fill-one-missing-digit (board/from "12345678 ") 0 9)
                (board/from "123456789")))}
  [board row digit]
  (loop [x 0]
    (cond
      (> x 8)
      board

      (nil? (:v (board/get-cell board x row)))
      (conj board {:x x :y row :v digit})

      :else
      (recur (inc x)))))

(defn solve-row
  {:test (fn []
           (is= (solve-row (board/from "123456789") 0)
                (board/from "123456789"))
           (is= (solve-row (board/from "1 3456789") 0)
                (board/from "123456789")))}
  [board row]
  (let [missing (set/difference #{1 2 3 4 5 6 7 8 9} (->> (board/get-row board row)
                                                          (map :v)))
        missing-count (count missing)]
    (cond
      (or (= 0 missing-count)
          (= 9 missing-count))
      board

      (= 1 missing-count)
      (fill-one-missing-digit board row (first missing))

      :else
      "NOOOO"
      )))
