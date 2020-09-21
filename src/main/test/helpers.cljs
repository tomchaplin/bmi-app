;; Helpers

(defn height-in-m
  [height]
  (case (:mode height)
    "m" (get-in height [:value 0])
    "ft:in" (cvt/ft:in-to-m (:value height))
  ))

(defn weight-in-kg
  [weight]
  (js/parseFloat (get-in weight [:value 0])))

(defn squared [x] (* x x))

(defn compute-bmi
  [height weight]
  (/ (weight-in-kg weight) (squared (height-in-m height))))

