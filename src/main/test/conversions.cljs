(ns test.conversions)

(defn m-to-ft:in 
  [[m]]
  (let [total-inches  (/ m 0.0254)
        feet          (mod total-inches 12)
        inches        (rem total-inches 12)]
    [feet inches]))

(defn ft:in-to-m
  [[ft in]]
  (* 0.0254 (+ in (* 12 ft))))
