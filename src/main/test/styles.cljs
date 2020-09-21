(ns test.styles
  (:require
    ["react-native" :as rn]
    ))

(def styles
  ^js (-> {:container
           {:flex 1
            :backgroundColor "#ccccee"
            :justifyContent "center"
            }
           :title
           {:fontWeight "bold"
            :fontSize 24
            :textAlign "center"
            :marginBottom 15}
           :userInput
           {:fontWeight "bold"
            :fontSize 24
            :borderColor "#000"
            :borderStyle "solid"
            :borderWidth 1
            :borderRadius 5
            :marginRight 10
            :width "40%"
            }
           :shrinkable {:flex 1}
           :oneLine {:flexDirection "row"
                     :width "100%"
                     :justifyContent "flex-start"
                     :alignItems "center"}
           :flexRow {:flexDirection "row"
                     :alignItems "center"}
           }
          (clj->js)
          (rn/StyleSheet.create)))

