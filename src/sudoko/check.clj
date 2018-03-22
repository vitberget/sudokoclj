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
           (is (solved? (board/from "172549683"
                                    "645873219"
                                    "389261745"
                                    "496327851"
                                    "813456972"
                                    "257198436"
                                    "964715328"
                                    "731682594"
                                    "528934167")))
           (is-not (solved? (board/from "172549683"
                                    "645873219"
                                    "389261745"
                                    "496327851"
                                    "81345 972"
                                    "257198436"
                                    "964715328"
                                    "731682594"
                                    "528934167")))
           (is-not (solved? #{{:x 0 :y 0 :v 3}})))}
  [board]
  (and
    ; Full board
    (= (* 9 9) (count board))

    ; Column check
    (loop [x 0]
      (if (= x 9)
        true
        (when (solved-part? (board/get-column board x))
          (recur (inc x)))))

    ; Row check
    (loop [y 0]
      (if (= y 9)
        true
        (when (solved-part? (board/get-row board y))
          (recur (inc y)))))

    ;Square check
    (loop [n 0]
      (if (= n 9)
        true
        (when (solved-part? (board/get-square board (int (/ n 3)) (mod n 3)))
          (recur (inc n)))))
    ))
