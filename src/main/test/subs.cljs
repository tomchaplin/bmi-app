(ns test.subs
  (:require
    [test.statics :as statics]
    [re-frame.core :as rf]
    [goog.string :as gstring]
    [goog.string.format]
    ))

(defn myParseFloat [s]
  (if (= s "") 0 (js/parseFloat s)))

(rf/reg-sub
  ::height-vec
  (fn [db [_]]
    (get-in db [:height :value])))

(rf/reg-sub
  ::height
  (fn [_] [(rf/subscribe [::height-vec])])
  (fn [[hv] [_ k]]
    (get hv k)))

(rf/reg-sub
  ::height-mode
  (fn [db]
    (get-in db [:height :mode])))

; Return height as number in metres or NaN
(rf/reg-sub
  ::height-in-m
  (fn [_] [(rf/subscribe [::height-vec])
           (rf/subscribe [::height-mode])])
  (fn [[hv hm] _]
    (let [hv-floats (mapv myParseFloat hv)
          converter (get-in statics/height-modes [hm :to-m])]
      (if
        (some js/isNaN hv-floats)
        ##NaN
        (converter hv-floats)))))

(rf/reg-sub
  ::weight-vec
  (fn [db [_]]
    (get-in db [:weight :value])))

(rf/reg-sub
  ::weight
  (fn [_] [(rf/subscribe [::weight-vec])])
  (fn [[wv] [_ k]]
    (get wv k)))


(rf/reg-sub
  ::weight-mode
  (fn [db]
    (get-in db [:weight :mode])))

; Return weight as a number in kilograms or NaN
(rf/reg-sub
  ::weight-in-kg
  (fn [_] [(rf/subscribe [::weight-vec])
           (rf/subscribe [::weight-mode])])
  (fn [[wv wm] _]
    (let [wv-floats (mapv myParseFloat wv)
          converter (get-in statics/weight-modes [wm :to-kg])]
      (if
        (some js/isNaN wv-floats)
        ##NaN
        (converter wv-floats)))))


(rf/reg-sub
  ::bmi

  (fn [query-v]
    [(rf/subscribe [::height-in-m])
     (rf/subscribe [::weight-in-kg])
     ])
  
  ; Check for NaN then compute and return formatted string
  (fn [[h w] _]
    (if (or (some js/isNaN [h w]) (= h 0))
      "..."
      (gstring/format "%.3f" (/ w (* h h))))))
