(ns test.statics)

(def height-modes 
  [{:button "m"
    :placeholders ["metres"]
    :to-m (fn [[m]] m)}
   {:button "ft+in"
    :placeholders ["feet" "inches"]
    :to-m (fn [[ft in]]
            (* 0.0254
               (+ in (* 12 ft))))}
   ])

(def weight-modes
  [{:button "kg"
    :placeholders ["kg"]
    :to-kg (fn [[kg]] kg)}
   {:button "st+lbs"
    :placeholders ["stone" "pounds"]
    :to-kg (fn [[st lbs]]
             (* 0.453592
                (+ lbs (* 14 st))))}
   {:button "lbs"
    :placeholders ["pounds"]
    :to-kg (fn [[lbs]] (* 0.453592 lbs))}])
