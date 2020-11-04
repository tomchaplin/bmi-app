(ns test.events
  (:require
    [goog.string :as gstring]
    [goog.string.format]
    [re-frame.core :as re-frame]
    [test.db :as db]
    [test.statics :as statics]))

(defn get-clean-height-value [mode]
  (mapv 
    (fn [_] "")
    (get-in statics/height-modes [mode :placeholders])))

(defn get-clean-weight-value [mode]
  (mapv 
    (fn [_] "")
    (get-in statics/weight-modes [mode :placeholders])))

(defn format-with-0-empty [my-num]
  (if (= my-num 0) 
    ""
    (gstring/format "%.2f" my-num)))

(re-frame/reg-event-db
  ::initialize-db
  (fn [_ _]
    db/default-db))

(re-frame/reg-event-db
  ::change-height
  (fn [db [_ k v]]
    (assoc-in db [:height :value k] v)))

(re-frame/reg-event-db
  ::change-height-mode
  (fn [db [_ new-mode]]
    (let [old-mode      (get-in db [:height :mode])
          old-mode-info (get statics/height-modes old-mode)
          new-mode-info (get statics/height-modes new-mode)
          to-m          (old-mode-info :to-m)
          from-m        (new-mode-info :from-m)
          old-values    (get-in db [:height :value])
          ov-floats     (mapv statics/myParseFloat old-values)]
      (if (some js/isNaN ov-floats)
      ; Can't convert so just get clean values
      (-> db
          (assoc-in [:height :mode] new-mode)
          (assoc-in [:height :value] (get-clean-height-value new-mode)))
      (-> db
          (assoc-in [:height :mode] new-mode)
          (assoc-in [:height :value] 
                    (mapv format-with-0-empty (from-m (to-m ov-floats)))))))))

(re-frame/reg-event-db
  ::change-weight
  (fn [db [_ k v]]
    (assoc-in db [:weight :value k] v)))

(re-frame/reg-event-db
  ::change-weight-mode
  (fn [db [_ new-mode]]
    (let [old-mode      (get-in db [:weight :mode])
          old-mode-info (get statics/weight-modes old-mode)
          new-mode-info (get statics/weight-modes new-mode)
          to-kg         (old-mode-info :to-kg)
          from-kg       (new-mode-info :from-kg)
          old-values    (get-in db [:weight :value])
          ov-floats     (mapv statics/myParseFloat old-values)]
      (if (some js/isNaN ov-floats)
      ; Can't convert so just get clean values
      (-> db
          (assoc-in [:weight :mode] new-mode)
          (assoc-in [:weight :value] (get-clean-weight-value new-mode)))
      (-> db
          (assoc-in [:weight :mode] new-mode)
          (assoc-in [:weight :value] 
                    (mapv format-with-0-empty (from-kg (to-kg ov-floats)))))))))

(re-frame/reg-event-db
  ::clear-inputs
  (fn [db _]
    (-> db
        (update-in [:height :value] #( mapv (fn [_] "") %))
        (update-in [:weight :value] #( mapv (fn [_] "") %)))))
