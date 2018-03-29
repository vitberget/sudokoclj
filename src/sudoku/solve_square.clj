(ns sudoku.solve-square
  (:require [clojure.set :as set]
            [sudoku.board :as board]
            [sudoku.check :as check]
            [ysera.test :refer [is is-not is=]]))

(defn fill-one-missing-digit
  {:test (fn []
           (is= (fill-one-missing-digit (board/from "123"
                                                    "456"
                                                    "789")
                                        0 0 2)
                (board/from "123"
                            "456"
                            "789"))
           (is= (fill-one-missing-digit (board/from "1 3"
                                                    "456"
                                                    "789")
                                        0 0 2)
                (board/from "123"
                            "456"
                            "789"))
           (is= (fill-one-missing-digit (board/from "123"
                                                    "456"
                                                    "78 ")
                                        0 0 9)
                (board/from "123"
                            "456"
                            "789")))}
  [board row column digit]
  (let [row-base (* 3 row)
        column-base (* 3 column)]
    (loop [i 0]
      (if (> i 8)
        board
        (let [x (+ row-base (mod i 3))
              y (+ column-base (quot i 3))
              cell (board/get-cell board x y)]
          (if (nil? (:v cell))
            (conj board (merge cell {:x x :y y :v digit}))
            (recur (inc i))))))))

(defn solve-square
  {:test (fn []
           (is= (solve-square (board/from "123"
                                          "456"
                                          "789")
                              0 0)
                (board/from "123"
                            "456"
                            "789"))
           (is= (solve-square (board/from "1 3"
                                          "456"
                                          "789")
                              0 0)
                (board/from "123"
                            "456"
                            "789")))}
  [board row column]
  (let [missing (set/difference #{1 2 3 4 5 6 7 8 9} (->> (board/get-square board row column)
                                                          (map :v)))
        missing-count (count missing)]
    (cond
      (or (= 0 missing-count)
          (= 9 missing-count))
      board

      (= 1 missing-count)
      (fill-one-missing-digit board row column (first missing))

      :else
      "NOOOO"
      )))
