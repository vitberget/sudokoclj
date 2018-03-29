(ns sudoku.solve-column
  (:require [clojure.set :as set]
            [clojure.string :as str]
            [sudoku.board :as board]
            [sudoku.check :as check]
            [ysera.test :refer [is is-not is=]]))

(defn board-col-from
  {:test (fn []
           (is= (board-col-from "123456789")
                (board/from "1" "2" "3" "4" "5" "6" "7" "8" "9")))}
  [input]
  (->> (str/split input #"")
       (map str)
       (board/from)))

(defn fill-one-missing-digit
  {:test (fn []
           (is= (fill-one-missing-digit (board-col-from "123456789") 0 2)
                (board-col-from "123456789"))
           (is= (fill-one-missing-digit (board-col-from "1 3456789") 0 2)
                (board-col-from "123456789"))
           (is= (fill-one-missing-digit (board-col-from "19342678 ") 0 5)
                (board-col-from "193426785")))}
  [board col digit]
  (loop [y 0]
    (if (> y 8)
      board
      (let [cell (board/get-cell board col y)]
        (if (nil? (:v cell))
          (conj board (merge cell {:x col :y y :v digit}))
          (recur (inc y)))))))

(comment
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
        ))))
