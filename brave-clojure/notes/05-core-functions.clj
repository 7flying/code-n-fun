;;;
;;; 05 - Core Functions in Depth
;;;

;;; Programming to Abstractions

; map creates a lazy sequence by applying a function to every element on a
; collection
(defn titleize
  [topic]
  (str topic " for the Brave and True"))
(map titleize ["Hamsters" "Ragnarok"])
(map titleize '("Empathy" "Decorating"))
(map titleize #{"Elbows" "Soap Carving"})

; map treats each key-val as an element
(defn label-key-val
  [[key val]]
  (str "key: " key ", val: " val))
(map label-key-val {:name "Edward" :occupation "perennial high-schooler"})

(map (fn [[key val]] [key (inc val)])
     {:max 30 :min 10})

; into -> convert the list into a map
(into {}
      (map (fn [[key val]] [key (inc val)])
           {:max 30 :min 10}))

;;; The Sequence Abstraction

; Rememeber that map calls seq on its collection arguments
; identity: returns whatever was passed to it
(identity "Stefan Salvatore from Vampire Diaries")
(map identity {:name "Ash Ketchup" :occupation "Pokemon Trainner"})

; seq: converts a map data structure into a sequence of vectors, each of which
; is a key-value pair
(seq {:name "Ash Ketchup" :occupation "Pokemon Trainer"})

;; map: examples
; map operating on one collection
(map inc [2 3 4])

; map operating on multiple collections
(map str ["a" "b" "c"] ["A" "B" "C"])
;  -> The elements of the first collection will be passed as the first argument of
; the mapping function, the elements of the second collection will be passed as
; the second argument and so on

; Crazy algorithm to unify the data for both human and critter feeding
(def human-consumption [8.1 7.3 6.6 5.0])
(def critter-consumption [0.0 0.2 0.3 1.1])
(defn unify-diet-data
  [human critter]
  {:human human :critter critter})
(map unify-diet-data human-consumption critter-consumption)

; you can pass a collection of functions to map

; We are going to apply sum and avg functions to each collection
(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))
(stats [3 4 20])

;; reduce: examples
; reduce processes each element in a sequence to build a result

; use reduce on a map data structure to "update" its values
(reduce (fn [new-map [key val]]
          (assoc new-map key (inc val)))
        {}
        {:max 30 :min 10})

; filter out keys from a map based on their value
(reduce (fn [new-map [key val]]
          (if (> val 4)
            (assoc new-map key val)
            new-map))
        {}
        {:human 4.1 :critter 3.9})

;; take, drop, take-while, drop-while: examples

; take: returns the first n elements of a sequence
(take 3 [0 1 2 3 4 5 6 7 8 9 10])
; drop: returns the sequence but with the first n elements removed
(drop  3 [0 1 2 3 4 5 6 7 8 9 10])

; take-while and drop-while

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

; take-while: (take-while pred coll) returns the items of a sequence while the
; pred function returns true
(take-while #(< (:month %) 3) food-journal)

