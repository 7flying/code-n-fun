;;;
;;; 08 - Clojure Alchemy: Reading, Evaluation and Macros
;;;

;;; 1- The Philosopher's Stone

; Clojure evaluates data structures
(def addition-list (list + 1 2)) ; evaluates a list
(eval addition-list)

; Everything is a representation of lists
(+ 1 2)
(map first '([0 1] [3 4]))

; How are the representations parsed?
; - The representations are parsed by the reader, which produces data structures
; - The data structures are evaluated
; -> Making Clojute homoiconic (we write Clojure programs using representations
; of Clojure data structures)

;;; 2- The Reader
; The reader is the bridge between textual representation of a data structure
; and the data structure itself
; * Textual representation of data structure = reader form

(read-string "(+ 3 4)") ; -> (+ 3 4) ; the reader form is a list with 3 members
(eval (read-string "(+ 3 4)")) ; evaluates to a number reader form

;;; 3- Reader Macros
() ; -> list reader form
str ; -> symbol reader form
[1 2] ; -> vector reader form containing two number reader forms
{:sound "hooot"} ; -> a map reader form with a key reader form and
                 ; a string reader form

; so...
(read-string "(+ 1 2)") ; gives a list reader form with 3 members
; anonymous functions !!!
(#(+ 1 %) 3) ; -> gives 4
(read-string "#(+ 1 %)") ; -> cool stuff: (fn* [p1__3354#] (+ 1 p1__3354#))
                         ; list of: fn* symbol, a vector with a symbol and
                         ; a list with three elements
; So, what happend?
; The reader used a 'reader-macro' to transform the anonymous function.
; Reader macros are a set of rules for transforming text into data structures
; Reader macros are designated by macro characters and are used to represent
; data structures in more compact ways

; uses the quote reader macro ; -> expands to the quote special form
(read-string "'(a b c)") ; -> (quote (a b c))
; the reader sees ' and expands it to a list whose first member is the symbol
; 'quote' and whose second member is the data structure following the quote

; deref reader macro
(read-string "@var") ; -> (clojure.core/deref var)

; another example:
(read-string "; this is ignored!\n(+ 1 2)") ; -> (+ 1 2)

;;; 4- Evaluation

