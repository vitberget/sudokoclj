(ns sudoko.board
  (:require [ysera.test :refer [is is-not is=]]))

(comment
  (def example-board
    '({:v 1 :x 0 :y 0 :given true})
    ))


(defn solved-part?
  {:test (fn []
           (is (solved-part? [{:v 1} {:v 2} {:v 3} {:v 4} {:v 5} {:v 6} {:v 7} {:v 8} {:v 9}]))
           (is (solved-part? [{:v 1} {:v 5} {:v 9} {:v 4} {:v 2} {:v 6} {:v 7} {:v 8} {:v 3}])))}
  [list]
  (and (= 9 (count list))
       (->> list
            (map :v)
            (into #{})
            (= #{1 2 3 4 5 6 7 8 9}))))

(defn solved? [board]
  (and
    (= (* 9 9) (count board))

    ))