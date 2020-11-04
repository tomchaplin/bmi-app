(ns test.views 
  (:require
    [test.styles :refer [styles colored-title-style]]
    [test.subs :as subs]
    [test.events :as events]
    [test.statics :as statics]
    [re-frame.core :as rf]
    ["react-native" :as rn]
    ["react-native-elements" :refer (Card Input ButtonGroup Button)]
    ))

;; Views

(defn height-card []
  (let [current-mode  @(rf/subscribe [::subs/height-mode])
        mode-info     (get statics/height-modes current-mode)]
  [:> Card
   [:> (. Card -Title) "Height"]
   [:> (. Card -Divider)]
   [:> rn/View {:style (.-oneLine styles)}
    (doall (map-indexed
      (fn [index holder]
        ^{:key index}
        [:> rn/View
         {:style (.-shrinkable styles)}
         [:> Input
          {:placeholder     holder
           :defaultValue    @(rf/subscribe [::subs/height index])
           :onChangeText    #(rf/dispatch [::events/change-height index %])
           :keyboardType    "phone-pad"}]])
      (mode-info :placeholders)))]
   [:> ButtonGroup
    {:buttons       (map :button statics/height-modes)
     :selectedIndex current-mode
     :onPress       #(rf/dispatch [::events/change-height-mode %])}]]))

(defn weight-card []
  (let [current-mode  @(rf/subscribe [::subs/weight-mode])
        mode-info     (get statics/weight-modes current-mode)]
  [:> Card
   [:> (. Card -Title) "Weight"]
   [:> (. Card -Divider)]
   [:> rn/View {:style (.-oneLine styles)}
    (doall (map-indexed
      (fn [index holder]
        ^{:key index}
        [:> rn/View
         {:style (.-shrinkable styles)}
         [:> Input
          {:placeholder     holder
           :defaultValue    @(rf/subscribe [::subs/weight index])
           :onChangeText    #(rf/dispatch [::events/change-weight index %])
           :keyboardType    "phone-pad"}]])
      (mode-info :placeholders)))]
   [:> ButtonGroup
    {:buttons       (map :button statics/weight-modes)
     :selectedIndex current-mode
     :onPress       #(rf/dispatch [::events/change-weight-mode %])}]]))

(defn bmi-card []
  (let [bmi @(rf/subscribe [::subs/bmi])]
  [:> Card
   [:> (. Card -Title) "BMI"]
   [:> (. Card -Divider)]
   [:> rn/View 
    [:> rn/Text 
     {:style (colored-title-style (bmi :color))}
     (bmi :string)]]
   [:> (. Card -Divider)]
   [:> Button 
    {:title "Clear Inputs"
     :onPress #(rf/dispatch [::events/clear-inputs])}]]))

(defn root []
  [:> rn/View {:style (.-container styles)}
   [height-card]
   [weight-card]
   [bmi-card]])
