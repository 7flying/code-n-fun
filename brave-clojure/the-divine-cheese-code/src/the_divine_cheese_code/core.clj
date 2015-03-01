(ns the-divine-cheese-code.core)

; Load svg code
(require 'the-divine-cheese-code.visualization.svg)
; refer it so we don't have to use the fully-qualified name to reference svg
; functions
(refer 'the-divine-cheese-code.visualization.svg)

(def heists [{:location "Cologne, Germany"
              :cheese-name "Archbishop Hildebold's Cheese Pretzel"
              :lat 50.95
              :lng 6.97}
             {:location "Zurich, Switzerland"
              :cheese-name "The Standard Emmental"
              :lat 47.37
              :lng 8.55}
             {:location "Marseille, France"
              :cheese-name "Le Fromage de Cosquer"
              :lat 43.30
              :lng 5.37}
             {:location "Zurich, Switzerland"
              :cheese-name "The Lesser Emmental"
              :lat 47.37
              :lng 8.55}
             {:location "Vatican City"
              :cheese-name "The Cheese of Turin"
              :lat 41.90
              :lng 12.45}])

;; Notes: 
;; require
; Reads and evaluates the contents of the file, Clojure expects the file to
; declare a nemespace corresponding to its path
; require lets you alias a namespace when you require it:
(require '[the-divine-cheese-code.visualization.svg :as svg])

; which is the same as:
(require 'the-divine-cheese-code.visualization.svg)
(alias 'svg 'the-divine-cheese-code.visualization.svg)

; then you can use the aliased namespace
; (svg/points heists)

; require & refer

; Instead of calling require and then refer, the function use does both:
(require 'the-divine-cheese-code.visualization.svg)
(refer 'the-divine-cheese-code.visualization.svg)
; equivalent to :
(use 'the-divine-cheese-code.visualization.svg)

; We can alias a namespace with use the same way we did with require
(use '[the-divine-cheese-code.visualization.svg :as svg])
; equivalent to
(require 'the-divine-cheese-code.visualization.svg)
(refer 'the-divine-cheese-code.visualization.svg)
(alias 'svg 'the-divine-cheese-code.visualization.svg)

; So we can use
(= svg/points points)
; and
(= svg/latlng->point latlng->point)



(defn -main
  [& args]
  (println (points heists)))
