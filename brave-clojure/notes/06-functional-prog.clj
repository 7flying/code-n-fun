;;;
;;; 06 - Functional Programming
;;;

;; Pure functions

; Pure functions: a function is pure if:
; 1- It has referential transparency (aka it always returns the same result
;    given the same arguments)
; 2- It doesn't cause any side effects.

; Example: referential transparent function
(defn wisdom
  [words name]
  (str words ", " name "-san"))
(wisdom "Always bathe on Fridays" "Misty")

; Examples: not referential transparent functions
; does not yield the same result with the same arguments
(defn year-end-evaluation
  []
  (if (> (rand) 0.5)
    "You get a raise!"
    "Better luck next year!"))
; analysis is referentially transparent
(defn analysis
  [text]
  (str "Character count: " (count text)))
; analyze-file is not, the file can change
(defn analyze-file
  [filename]
  (analysis (slurp filename)))


;; Living with Immutable Data Structures

; Immutable data structures ensure that your code won't have side effects.

; -Recursion instead of for/while
; In Clojure there is no assignment operator, you can't associate a new value
; with a name within the same scope
(defn no-mutation
  [x]
  (println x)
  ; Create a new scope
  (let [x "Kafka Raichu"]
    (println x))
  ; Exit the let scope, x is the same
  (println x))
(no-mutation "Existential Angs Pikachu")

; The Lispy way
(defn sum
  ([vals] (sum vals 0))
  ([vals accumulating-total]
   (if (empty? vals)
     accumulating-total
     (sum (rest vals) (+ (first vals) accumulating-total)))))
(sum [1 2 3 4 5])

; The Clojure way: use recur for performance reasons since Clojure does not
; provide tail call optimization
(defn opti-sum
  ([vals] (sum vals 0))
  ([vals accumulating-total]
   (if (empty? vals)
     accumulating-total)
   (recur (rest vals) (+ (first vals) accumulating-total))))
(opti-sum [1 2 3 4 5])

; Check- space optimization vs time ?¿
(time (sum [1 2 3 4 5]))
(time (opti-sum [1 2 3 4 5]))

; -Functional Composition instead of Attribute Mutation
; Apply a chain of functions to an immutable value
(require '[clojure.string :as s])
(defn clean
  [text]
  (s/replace (s/trim text) #"lol" "LOL"))
(clean "My boa constructor is so sassy lol!")


;; Cool Things to do With Pure Functions

; In pure functions you only need to worry about input/output
; It is a common practice to compose a function with other funcions.

; Example:
((comp clojure.string/lower-case clojure.string/trim) " Unclean string")

; comp: (comp f), (comp f g), (comp f g h), (comp f1 f2 f3 % fs): takes a set
; of functions and returns a fn that is the composition of whose fs.

; comp implementation for two functions
(defn two-comp
  [f g]
  (fn [& args]
    (f (apply g args))))

; Memoization
; Takes advantage of referential transparency by storing the arguments passed to
; a function and the return value of the function.
(defn sleeping-identity
  "Returns the given valie after 1 second"
  [x]
  (Thread/sleep 1000)
  x)
(sleeping-identity "Hello world!")

(def memo-sleeping-identity (memoize sleeping-identity))
(memo-sleeping-identity "Hello") ; takes 1 second
(memo-sleeping-identity "Hello") ; right away
(time (memo-sleeping-identity "Hello"))

