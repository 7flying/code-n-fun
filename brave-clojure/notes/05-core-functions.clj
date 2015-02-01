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

; drop-while: (drop-while pred coll) drops elemens until one tests true, returns
; the remaining elements
(drop-while #(< (:month %) 2) food-journal)


; take data for just February and March
(take-while #(< (:month %) 4)
            (drop-while #(< (:month %) 2) food-journal))

;; filter, some: examples

; filter: returns all elements of a sequence which test true for a predicate
; function
; take data just for February and March
(filter #(and (> (:month %) 1) (< (:month %) 4)) food-journal)

; some: (some pred coll) to test if the collection coll has values that test
; true for the pred function. Returns true or nil
(some #(> (:critter %) 5) food-journal)
(some #(> (:critter %) 0) food-journal)

; take the food-journal entries where the vampire consumed more than 3 liters
; from critter sources
(some #(and (> (:critter %) 3) %) food-journal)

;; sort, sort-by: examples

; sort: sorts in ascending order
(sort [3 1 2])

; sort-by: takes a function to determine the sort order and a collection
(sort-by count ["aaaaa" "asdadeedeafefef" "b"])

;; concat: examples

; concat: appends the members of a sequence to the end of another
(concat [1 2 ] [3 4])

;;; Lazy seqs
; A lazy seq is a seq whose members aren't computed until you try to access them

; Demonstrating the lazy seq efficiency with vampires
(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true  :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true  :name "McMackson"}
   2 {:makes-blood-puns? true,  :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true,  :has-pulse? true  :name "Mickey Mouse"}})

(defn vampire-related-details
  "Takes a second to look up an entry from the database"
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  "Returns a record if it passes the vampire test, otherwise returns false"
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))))

(defn identity-vampire
  "Maps social sequrity numbers to database records and then returns the first
   record which indicates vampirism"
  [social-security-numbers]
  (first (filter vampire?
                 (map vampire-related-details social-security-numbers))))

; we are going to use the function time to display how much time it takes to
; find the vampire
(time (identity-vampire (range 0 1000))) ;-> ~30 secs (?¿?¿?¿?¿?¿?)

; understanding lazy evaluation
(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spiderman" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}
   {:alias "alias 5", :real "real 5"}
   ; ... Just pretend that there are actually maps here for 6-30
   {:alias "alias 31", :real "real 31"}
   {:alias "alias 32", :real "real 32"}
   {:alias "alias 33", :real "real 33"}
   {:alias "alias 34", :real "real 34"}])

(defn snitch
  "Announce real identity to the world"
  [identity]
  (println (:real identity))
  (:real identity))

(def revealed-identities (map snitch identities))
; All identities get printed on the first time!
(first revealed-identities)
; Clojure doesn't realize a lazy list until you try
; to read a value from it. It usually realizes 32 members at a time
; re run (first revealed-identities)
; to get => "Bruce Wayne"
; Clojure caches the values of the lazy seq, this way it doesn't have to
; re-compute them when you try to access them again

; When lazy sequences are produced via functions that have side effects, any
; effects other than those needed to produce the first element in the seq do not
; occur until tue seq is consumed.
; doall can be used to force any effects. It walks through the successive nexts
; of que seq, retains the head and returns it, thus causing the entire seq to
; reside in memory at one time. (Clojuredocs.org/clojure.core/doall)
(doall revealed-identities)

;;; Infinite sequences
; lazy seqs gives the ability to construct "infinite" sequences

; 1- repeat: creates a sequence whose every member is the argument you pass
(concat (take 8 (repeat "na")) ["Batman!"])

; 2- repeatedly: calls the provided function to generate each element in the seq
(take 3 (repeatedly (fn [] (rand-int 10))))

; * Lazy seqs are like "recipes" for how to generate each element in a sequence
; and these recipes don't have to specify and endpoint
; Functions like first and take, realize the lazy seq and have no way of knowing
; what will comoe next in a seq. So if new elements are provided they just take
; them

(defn even-numbers
  ([] (even-numbers 0))
  ([n] (cons n (lazy-seq (even-numbers (+ n 2))))))

(take 10 (even-numbers)) ; (0 2 4 6 8 10 12 14 16 18)

(cons 0 '(2 4 6))

;;; The Collection Abstraction

;; count, empty? and every? are about the whole

;; into: (into to from) returns a new coll consisting of to-coll with all of the
; items of from-coll conjoined

; (Remember) identity returns its argument
(map identity {:sunlight-reaction "Glitter"}) ;-> ({:sunlight-reaction ...."})
; convert it back to a map
(into {} (map identity {:sunlight-reaction "Glitter"}))

; into works well with other data structures
; to vector
(map identity [:garlic :sesame-oil :fried-eggs]) ;-> map returns a seq
(into [] (map identity [:garlic :sesame-oil :fried-eggs]))

; to list
(map identity [:garlic :sesame-oil :fried-eggs])

; to set
(into #{} (map identity [:garlic :sesame-oil :fried-eggs]))
(into #{} (map identity [:hello :hello])) ; sets only contain unique values

; into can have a non empty first argument
(into {:favourite-emotion "gloomy"} [[:sunlight-reaction "Glitter"]])
(into ["Cherry"] '("pine" "spruce"))

; both arguments can be of the same type
(into {:favourite-animal "kitty"} {:least-favourite-animal "dog"})

; -> into is great at taking two collections and adding all the elements from
; the second to the first

;; conj: also adds elements to a collection

(conj [0] [1]) ;-> [0 [1]]
; vs
(into [0] [1]) ;-> [0 1]
; so 
(conj [0] 1) ; -> [0 1
; we can supply as many elements to add as we want:
(conj [0] 1 2 3 4) ;-> [0 1 2 3 4]
; we can also add to maps
(conj {:time "midnight"} [:place "ye olde cemetarium"])

; We can define conj in terms of into
(defn my-conj
  [target & additions]
  (into target additions))
(my-conj [0] 1 2 3)

; We will see two functions which do the same thing but
; one takes a rest-param (conj)
; and the other takes a "seqable" data structure (into)


;;; Function functions

;; apply: explodes a "seqable" data structure so that it can be passed to a
;; function that expects a rest-param
(defn my-into
  [target additions]
  (apply conj target additions))
(my-into [0] [1 2 3])
(conj [0] 1 2 3)

; max: takes a rest param, so if you want to find the max element of a vector
; you have to explode it with apply
(max [0 1 2]) ;-> [0 1 2]
(apply max [0 1 2]) ;-> 2

;; partial: takes a function and any number of arguments. Returns a new function
;; When you call the returned function, it calls the original function with the
;; original arguments you supplied it along with the new arguments
(def add10 (partial + 10))
(add10 4) ;-> 14

(def add-missing-elements
  (partial conj ["water" "earth" "air"]))
(add-missing-elements "fire" "light" "darkness")

; We can define our partial
(defn my-partial
  [partialized-fn & args]
  (fn [& more-args]
    (apply partialized-fn (into more-args (reverse args)))))
(def add20 (my-partial + 20))
(add20 2)

;; complement: the complement of a boolean function
; previously we wrote the identity-vampire function, now we want identity-humans
(defn identity-humans
  [social-security-numbers]
  (filter #(not (vampire? %))
          (map vampire-related-details social-security-numbers)))

; using complement
(def not-vampire? (complement vampire?))
(defn identity-humans-complement
  [social-security-numbers]
  (filter not-vampire?
          (map vampire-related-details social-security-numbers)))

; implement complement
(defn my-complement
  [fun]
  (fn [& args]
    (not (apply fun args))))

(def my-pos? (my-complement neg?))
(my-pos? 2)
(my-pos? -1)
