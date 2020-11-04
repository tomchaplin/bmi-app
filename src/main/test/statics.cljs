(ns test.statics)

(def height-modes 
  [{:button "m"
    :placeholders ["metres"]
    :to-m (fn [[m]] m)
    :from-m (fn [m] [m])}
   {:button "ft+in"
    :placeholders ["feet" "inches"]
    :to-m (fn [[ft in]]
            (* 0.0254
               (+ in (* 12 ft))))
    :from-m (fn [m]
              (let [total-inches  (/ m 0.0254)
                    ft            (quot total-inches 12)
                    in            (rem total-inches 12)]
              [ft in]))}])

(def weight-modes
  [{:button "kg"
    :placeholders ["kg"]
    :to-kg (fn [[kg]] kg)
    :from-kg (fn [kg] [kg])}
   {:button "st+lbs"
    :placeholders ["stone" "pounds"]
    :to-kg (fn [[st lbs]]
             (* 0.45359237
                (+ lbs (* 14 st))))
    :from-kg (fn [kg]
               (let [total-lbs    (/ kg 0.45359237)
                     st           (quot total-lbs 14)
                     lbs          (rem total-lbs 14)]
                 [st lbs]))}
   {:button "lbs"
    :placeholders ["pounds"]
    :to-kg (fn [[lbs]] (* 0.45359237 lbs))
    :from-kg (fn [kg] [(/ kg 0.45349237)])}])

(defn myParseFloat [s]
  (if (= s "") 0 (js/parseFloat s)))

