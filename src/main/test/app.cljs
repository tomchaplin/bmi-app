(ns test.app
  (:require
    ["expo" :as ex]
    ["react-native" :as rn]
    ["react" :as react]
    [reagent.core :as r]
    [shadow.expo :as expo]
    [test.events :as events]
    [test.views :as views]
    [re-frame.core :as re-frame]
    ))

(defn start
  {:dev/after-load true}
  []
  (expo/render-root (r/as-element [views/root])))

(defn init []
  ; Only rebuild db on init load to avoid losing state
  (re-frame/dispatch-sync [::events/initialize-db])
  (start))

