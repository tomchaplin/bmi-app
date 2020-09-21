(ns test.views 
  (:require
    [test.styles :refer [styles]]
    [test.subs :as subs]
    [test.events :as events]
    [test.statics :as statics]
    [re-frame.core :as rf]
    ["react-native" :as rn]
    ["react-native-elements" :refer (Card Input ButtonGroup Button)]
    ))

;; Views

(defn mode-switcher [sub event modes]
  [:> rn/View {:style (.-flexRow styles)} (for [mode modes]
                                            ^{:key mode}
    [:> rn/TouchableOpacity
     {:style {:backgroundColor "#dddddd"}
      :onPress #(rf/dispatch [event mode])}
     [:> rn/Text {:style ( .-title styles)} mode]])])

(defn user-input [subscription event]
  (let [value (rf/subscribe [subscription])]
   [:> rn/TextInput {:style (.-userInput styles)
                      :keyboardType "phone-pad"
                      :defaultValue @value
                      :onChangeText #(rf/dispatch [event %])}]))

(defn height-dialog []
  (case @(rf/subscribe [::subs/height-mode])
    "m" [:> rn/View {:style (.-oneLine styles)}
         [user-input ::subs/height-0 ::events/change-height-0]
         [mode-switcher ::subs/height-mode ::events/change-height-mode ["m" "ft:in"]]]
   "ft:in" [:> rn/View {:style (.-oneLine styles)}
             [user-input ::subs/height-0 ::events/change-height-0]
             [user-input ::subs/height-1 ::events/change-height-1]
             [mode-switcher ::subs/height-mode ::events/change-height-mode ["m" "ft:in"]]]))

(defn weight-dialog []
  (case @(rf/subscribe [::subs/weight-mode])
    "kg" [:> rn/View {:style (.-oneLine styles)}
           [user-input ::subs/weight-0 ::events/change-weight-0]
           [:> rn/Text {:style (.-title styles)} "kg"]] )
  )

(defn bmi-dialog []
  (let [bmi (rf/subscribe [::subs/bmi])]
  [:> rn/View {:style (.-oneLine styles)}
   [:> rn/Text {:style (.-title styles)}
    (str "BMI: " @bmi)]]))

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
           }]])
      (mode-info :placeholders)))
    ]
   [:> ButtonGroup
    {:buttons       (map :button statics/height-modes)
     :selectedIndex current-mode
     :onPress       #(rf/dispatch [::events/change-height-mode %])}]
   ]))

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
           }]])
      (mode-info :placeholders)))
    ]
   [:> ButtonGroup
    {:buttons       (map :button statics/weight-modes)
     :selectedIndex current-mode
     :onPress       #(rf/dispatch [::events/change-weight-mode %])}]
   ]))

(defn bmi-card []
  [:> Card
   [:> (. Card -Title) "BMI"]
   [:> (. Card -Divider)]
   [:> rn/View 
    [:> rn/Text 
     {:style (.-title styles)}
     @(rf/subscribe [::subs/bmi])]]
   [:> (. Card -Divider)]
   [:> Button 
    {:title "Clear Inputs"
     :onPress #(rf/dispatch [::events/clear-inputs])}]])

(defn root []
  [:> rn/View {:style (.-container styles)}
   [height-card]
   [weight-card]
   [bmi-card]])
