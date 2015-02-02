(ns fwpd.core
  (:require [clojure.string :as s]))

(def filename "suspects.csv")

;; We are going to convert each row in the CSV into a map
(def headers->keywords {"Name" :name
                        "Glitter Index" :glitter-index})

(defn str->int
  [str]
  (Integer. str))

;; Convert string numeric to integer
(def conversions :name identity
  :glitter-index str->int)

(defn parse
  "Convert a csv into rows of columns"
  [string]
  (map #(s/split % #",")
       (s/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (let [;; headers become the seq (:name :glitter-index)
        headers (map #(get headers->keywords %) (first rows))
        ;; unmapped-rows become the seq
        ;; (["Edward Cullen" "10"] ...)
        unmapped-rows (rest rows)]
    ;; Return a seq of {:name X :glitter-index 10}
    (map (fn [unmapped-row]
           ;; Use a map to associate each header with its column. Map returns
           ;; a seq, use "into" to convert it into a map
           (into {}
                 ;; we are passing multiple collections to a map
                 (map (fn [header column]
                        ;; associate the header with the converted column
                        [header ((get conversions header) column)])
                      headers
                      unmapped-row)))
         unmapped-rows)))

(defn glitter-filter
  [minimun-glitter records]
  (filter #(>= (:glitter-index %) minimun-glitter) records))
