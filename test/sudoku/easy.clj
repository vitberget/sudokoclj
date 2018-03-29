(ns sudoku.easy
  (:require
    [clojure.test :refer [deftest]]
    [sudoku.solve :refer [solve]]
    [ysera.test :refer [is is-not is=]]
    [sudoku.board :refer [from]]
    [sudoku.check :refer [solved?]]))

(defonce facit (from "352476189"
                     "168952734"
                     "749813625"
                     "425697813"
                     "683241597"
                     "971538462"
                     "897365241"
                     "214789356"
                     "536124978"))

(deftest correct-facit
  (is (solved? facit)))

(deftest easy-1
  ; 3:07
  (let [board (from " 52  6   "
                    "16 9    4"
                    " 498 362 "
                    "4     8  "
                    " 832 159 "
                    "  1     2"
                    " 973 524 "
                    "2    9 56"
                    "   1  97 ")]
    (is-not (solved? board))
    (let [solved (solve board)]
      (is (solved? solved))
      (is= solved facit))))

(deftest easy-2
  ; 3:14
  (let [board (from " 524  1  "
                    "1    2 3 "
                    "   813 25"
                    "4    7 1 "
                    "683   597"
                    " 7 5    2"
                    "89 365   "
                    " 1 7    6"
                    "  6  497 ")]
    (is-not (solved? board))
    (let [solved (solve board)]
      (is (solved? solved))
      (is= solved facit))))

(deftest easy-3
  ; 3:56
  (let [board (from "3 2    89"
                    " 68 52734"
                    "  9      "

                    "4    7   "
                    " 832 159 "
                    "   5    2"

                    "      2  "
                    "21478 35 "
                    "53    9 8")]
    (is-not (solved? board))
    (let [solved (solve board)]
      (is (solved? solved))
      (is= solved facit))))