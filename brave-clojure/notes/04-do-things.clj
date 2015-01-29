;;;
;;; 04 - Clojure Crash Course
;;;

;;; Control Flow

;; if
; (if boolean-form
;  then-form
;  optional-else-form)
(if true
  "abra cadabra"
  "hocus pocus")

;; do: wraps up multiple forms
(if true
  (do (println "Success!")
      "abra cadabra")
  (do (println "Failure")
      "hocus pocus!"))

;; when: combination of if and do. No else form.
(when true
  (println "Success")
  "abra cadabra!")

;;; Naming things

;; def: to bind a name to a value
(def starter-pokemons
  ["Charmander"
   "Bulbasaur"
   "Squirtle"])

;;; Data Structures
; All of Clojure's data structures are inmutable

;; -nil: no value
; to check if something is nil
(nil? 1) ;-> false
(nil? nil) ;-> true

; nil and false represent false
; everything else is true

; = equality operator
(= 2 2)
(= nil nil)
(= nil false)

;; -Numbers
67   ; integer
5.5  ; float
1/4  ; ratio

;; -Strings
"Lord Voldemort"
"\"He who must not be named\""

; Concatenate strings
(str "Supercalifrastilistico" " " "espiralidoso")

;; -Maps
(get {:a 0 :b 1} :a) ;-> 0
(get {:a 0 :b 1} :c) ;-> nil

; get vs get-in
(get {:a 0 :b {:c "Thunderbolt!"}} :c) ;-> {:c "Thunderbolt!"}
(get-in {:a 0 :b {:c "Thunderbolt!"}} [:b :c]) ;-> "Thunderbolt!"

; It is better to treat the map as a function and the wished key as an argument
({:a 0 :b 1} :a) ;-> 0
({:a 0 :b {:c "Thunderbolt!"}} :b) ; -> {:c "Thunderbolt!"}
(({:a 0 :b {:c "Thunderbolt!"}} :b) :c) ;-> "ThunderBolt!"

; Keywords
; Keywords may be used as functions which look up the corresponding value
; in a data structure

; Look up :a in map
(:a {:a 2 :b 2}) 

; Provide a default value
(:c {:a 2 :b 2} "KHALEESI")

;* Map creation
(hash-map :a 1 :b 2)

;; -Vectors
[3 2 1]
(get [3 3 4] 0) ;-> 3

; Elements of a vector can be of any type, an types can be mixed
(get ["Pikachu" 3 {:a "Hello"}] 0)

;* Create vectors using vector function
(vector "Pichu" "Pikachu" "Raichu")

; Add an element to a vector -> elements are added to the END
(conj [1 2 3] "Surprise!") ;-> [1 2 3 "Surprise!"]

;; -Lists
'(1 2 3 4) ; Like Common Lisp

;* Create a list with the list function
(list 1 2 3 4) ;-> (1 2 3 4)

; Add an element to a list -> elements are added to the BEGINNING
(conj '(1 2 3 4) 0)

;; -Sets
; Sets are collections of unique values
#{"Pichu" "Pikachu" "Raichu"}

; Add an element to a set
(conj #{:a :b} :b)

; Check whether a value exists in a set
(get #{:a :b} :a)

; The same as
(:a #{:a :b})

; If the element does't exist we get a nil
(:pichu #{:pikachu :raichu})

;* Create sets from existing vectors using the set function
(set [3 3 4 4 5]) ;-> #{4 3 5}

; We can use this to check if an element exists in a collection
(get (set [2 3 4 4 "Pikachu"]) "Pikachu") ;-> "Pikachu"
(get (set [2 3 4 5 "Raichu"]) "Pikachu") ;-> nil

;* Create a hash-set
(hash-set 2 3 4 4 5 6) ;-> #{4 6 3 2 5}

;* Create sorted set
(sorted-set :n :a :c) ;-> #{:a :c :n}

;; -Symbols and naming
; def associates the value [ ... ] with the symbol some-digimons
(def some-digimons ["Metalgreimon" "Gatomon" "Inventedmon"])

; Lips allow to manipulate symbols as data. Functions can return symbols
; as wel as take them as arguments
(identity 'test) ; em, ok?

;; -Quoting
; Giving Clojure a symbol returns the "object" it refers to
; some-digimons -> run in the interpreter (gives) -> ["Metalgreimon" ...]
(first some-digimons) ;-> "Metalgreimon"

; Quoting a symbol tells Clojure to use the symbol itself as a data structure
; not the object the symbol refers to
; 'some-digimons -> run in the interpreter (gives) -> some-digimons
(eval 'some-digimons) ;-> ["Metalgreimon" ...]
(first 'some-digimons) ;-> exception, first needs an object not a symbol
(first (eval 'some-digimons)) ; -> this is okay ^^
(first ['some-digimons 'invented-symbol]) ;-> some-digimons

; When we quote lists, maps and vectors all symbols within the collection will
; be unevaluated
(first '(some-digimons "an string" 0)) ;-> some-digimons

;;; Functions
(first [1 2 3 4])
; A function can be an operand
((or + -) 1 2 3)
((and (= 2 2) +) 1 2 3 4)
((first [+ -]) 1 2 3 4)
((second [+ -]) 1 2 3 4)

; inc: increments a number by 1
(inc 1.1)
; map: creates a LIST by applying a function to each member of a collection
(map inc [0 1 2])

; Clojure evaluates all function arguments recursively before passing them to
; the function:
(+ (inc 199) (/ 100 (- 7 2)))
(+ 200 (/ 100 (- 7 2)))
(+ 200 (/ 100 5))
(+ 200 20) ; final evaluation 

;; Defining functions
; A funtion has: defn, a name, (optional) docstring, parameters, function body
(defn i-choose-you 
  "Choose a Pokemon" ; You can view the docstring by (doc function-name)
  [name]             ; Takes a parameter. [] for no parameters
  (println (str name ", I CHOOSE YOU!")))
(i-choose-you "Pidgey")

; Functions can be overloaded by arity. A different function body will run
; depending on the number of arguments passed to a function
(defn multi-choose-pokemon
  ([first-pk second-pk]
   (println (str first-pk " and " second-pk ", I CHOOSE YOU!")))
  ([first-pk]
   (i-choose-you first-pk)))
(multi-choose-pokemon "Gengar" "Butterfly")
(multi-choose-pokemon "Arcanine")

; Variable-arity functions can be defined using a "rest-param" or &
; & means: put the rest of these arguments in a list with the following name
(defn i-choose-you-all
  [& cool-pokemons]
  (map i-choose-you cool-pokemons))
(i-choose-you-all "Gengar" "Arcanine" "Pidgey" "Butterfly")

; We can mix rest-params with normal params, but the rest-param must come last
(defn i-choose-you-all-again
  [pokemon & other]
  (do (i-choose-you pokemon)
      (println "and also")
      (map i-choose-you-all other)))
(i-choose-you-all-again "Gengar" "Arcanine" "Pidgey" "Butterfly")

; Destructuring: lets you bind symbols to values within a collection
; Return the first element of a collection
; 1. Until now
(defn my-first
  [collection]
  (first collection))
(my-first ["There" "is"])

; 2. Destructuring
(defn my-first-destruc
  [[first-thing]] ; note: it is within a vector
  first-thing)
(my-first-destruc ["There" "is"])

; This is best understood with another example
(defn chooser
  [[first-thing second-thing & everything-else]]
  (println (str "The first thing I found is: " first-thing))
  (println (str "The second thing I found is: " second-thing))
  (println (str "And everythin else is: "
                (clojure.string/join ", " everything-else))))
(chooser ["Book" "plane" "another" "other things"])
(chooser ["Book", "plane", "train", "else"])

;* Destructure a list or a vector providing vector as a parameter
;* Destructure a map providing a map as a parameter

(defn announce-treasure-location
; associates symbol some-lat with value of the incoming key :lat
  [{some-lat :lat some-lng :lng}]
  (println (str "Treasure lat: " some-lat))
  (println (str "Treasure lng: " some-lng)))
(announce-treasure-location {:lat 20 :lng 65.89})

(defn announce-treasure-lazy
; just take keywords and break them up of a map
  [{:keys [lat lng]}]
  (println (str "Treasure lat: " lat)
  (println (str "Treasure lng: " lng))))

; Retain access to the original map argument by using :as keyword
(defn receive-treasure-loc
  [{:keys [lat lng] :as treasure-loc}]
  (println (str "Lat: " lat))
  (println (str "Lng: " lng))
  (println treasure-loc))
(receive-treasure-loc {:lat 88 :lng 55})

; Remember that Clojure returns the last form evaluated
(defn some-example-function
  []
  (+ 1 2)
  "hey!")
(some-example-function)

;; Anonymous functions
; Functions without a name can be created in two ways:

; 1- Use fn form
;(fn [param-list]
;  body)

(map (fn [name] (str name " levels up!"))
     ["Oddish" "Machop"])
((fn [x] (* x 3)) 5)

; We cam associate an anonymous function with a name
(def my-super-special-multiplier (fn [x] (* x 3)))
(my-super-special-multiplier 24)

; 2- Use the compact weird way
#(* % 3)
; apply the compact weird way
(#(* % 3) 4)

(map #(str % " levels up!")
     ["Oddish" "Machop"])

; % indicates the argument passed to the function.
; if multiple arguments are given you can distinguish them with: %1 %2 %3 ...
; % is equal to %1
; We can also pass a rest param
(#(identity %&) 1 "blarrg" :yip)

;; Returning functions
; The returned functions are closures, they can access all the variables that
; were in the scope when the function was created
(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by)) ; inc-by is in scope so the returned function has acces to it
(def inc3 (inc-maker 3))
(inc3 4)

;;; Pulling it all together
;; The hobbit program

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn needs-matching-part?
  [part]
  (re-find #"^left-" (:name part)))

(defn make-matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps which have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts final-body-parts
            (conj final-body-parts part)]
        (if (needs-matching-part? part)
          (recur remaining (conj final-body-parts (make-matching-part part)))
          (recur remaining final-body-parts))))))

(symmetrize-body-parts asym-hobbit-body-parts)

(s)
