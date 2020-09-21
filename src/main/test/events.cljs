(ns test.events
  (:require
    [re-frame.core :as re-frame]
    [test.db :as db]
    [test.statics :as statics]))

(defn get-new-height-value [mode]
  (mapv 
    (fn [x] "")
    (range (count (get-in statics/height-modes [mode :placeholders])))))

(defn get-new-weight-value [mode]
  (mapv 
    (fn [x] "")
    (range (count (get statics/weight-modes [mode :placeholders])))))

(re-frame/reg-event-db
  ::initialize-db
  (fn [db event]
    db/default-db))

(re-frame/reg-event-db
  ::change-height
  (fn [db [_ k v]]
    (assoc-in db [:height :value k] v)))

(re-frame/reg-event-db
  ::change-height-mode
  (fn [db [_ new-mode]]
    (-> db 
      (assoc-in [:height :mode] new-mode)
      (assoc-in [:height :value] (get-new-height-value new-mode)))))

(re-frame/reg-event-db
  ::change-weight
  (fn [db [_ k v]]
    (assoc-in db [:weight :value k] v)))

(re-frame/reg-event-db
  ::change-weight-mode
  (fn [db [_ new-mode]]
    (-> db 
      (assoc-in [:weight :mode] new-mode)
      (assoc-in [:weight :value] (get-new-height-value new-mode)))))

(re-frame/reg-event-db
  ::clear-inputs
  (fn [db _]
    (-> db
        (update-in [:height :value] #( mapv (fn [x] "") %))
        (update-in [:weight :value] #( mapv (fn [x] "") %))
        )))
