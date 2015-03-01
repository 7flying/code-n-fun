(ns the-divine-cheese-code.visualization.svg)

(defn latlng->point
  "Convert a lat/ong map to comma-separated string"
  [latlng]
  (str (:lat latlng) "," (:lng latlng)))

(defn points
    [locations]
    (clojure.string/join " " (map latlng->point locations)))
