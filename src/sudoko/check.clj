(ns sudoko.check
  (:require [ysera.test :refer [is is-not is=]]
            [sudoko.board :as board]))

(defn solved-part?
  {:test (fn []
           (is (solved-part? #{{:v 1} {:v 2} {:v 3} {:v 4} {:v 5} {:v 6} {:v 7} {:v 8} {:v 9}}))
           (is (solved-part? #{{:v 1} {:v 5} {:v 9} {:v 4} {:v 2} {:v 6} {:v 7} {:v 8} {:v 3}}))
           (is-not (solved-part? #{{:v 2} {:v 6} {:v 7} {:v 8} {:v 3}})))}
  [part]
  (and (= 9 (count part))
       (->> part
            (map :v)
            (into #{})
            (= #{1 2 3 4 5 6 7 8 9}))))

(defn solved?
  {:test (fn []
           (is-not (solved? #{{:x 0 :y 0 :v 3}})))}
  [board]
  (and
    (= (* 9 9) (count board))

    (loop [x 0]
      (if (= x 9)
        true
        (when (solved-part? (board/get-column board x))
          (recur (inc x)))))
    (loop [x 0]
      (if (= x 9)
        true
        (when (solved-part? (board/get-column board x))
          (recur (inc x)))))

      ))
