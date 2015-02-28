;;;
;;; 07 - Organizing Your Project: a Librarian's Tale
;;;

;;; Your project as a library

; Clojure maps human-friendly symbols and references to vars using namespaces.
; Namespaces are objects of type clojure.lang.Namespace.

; Get current namespace
(ns-name *ns*)

;;; Storing Objects with def
;; Aka- Interning a Var
(def great-books ["East of Eden" "The Flass Bead Game"])

; 1- find a var named great-books, if it doesn't exist, create it and update
; the current namespaces map ('#user/great-books)
; 2- find free storage and put ["East of Eden" "The Flass Bead Game"] onto it
; 3- wirte the address of the selected free space on the Var
; 4- return the var ('#user/great-books)

; Interact with a namespace's map of symbols-to-interned-Vars:
(ns-interns *ns*) ;-> {great-books #'user/great-books)

; Get a specific Var
(get (ns-interns *ns*) 'great-books)

; Get the map the namespace uses for looking up a Var when given a symbol
(ns-map *ns*)

; Get the symbol 'great-books from the current namespace
(get (ns-map *ns*) 'great-books)

; #'user/great-books is the reader form of a Var.
; #' can be used to grab hold of the Var corresponding to the symbol that
; follows. So #'user/great-books lets me use the Var associated with the
; symbol great-books within the user namespace

; To get the object a var points to, use deref
(deref #'user/great-books)
; This tells Clojure: get the address from the Var, go to that address and get
; what's inside, give it to me
; which the same as just using the symbol
great-books
; This tells Clojure: get the var associated with great-books, deref it.

; When we def again with the same symbol:
(def great-books ["The Power of Bees" "Journey to Upstairs"])
great-books
; the Var gets updated with the address of the vector -> Name collision, use
; namespaces to avoid those collisions

;;; Creating and Switching to Namespaces

; Clojure thas three tools for creating namespaces:
; 1- The function create-ns: takes a symbol and creates a namespace with that
; name if it doesn't exist already, then returns the namespace
(create-ns 'cheese.taxonomy) ; => #<Namespace cheese.taxonomy>

; You can pass the returned namespace as an argument
(ns-name (create-ns 'cheese.taxonomy))

; 2- in-ns: creates the namespace if it doesn't exist and switches to it.
(in-ns 'cheese.analysis)

; To use things from other namespaces use a fully qualified symbol:
; namespace/name
(in-ns 'cheese.taxonomy)
(def cheddars ["mild" "medium" "strong" "sharp"])
(def bries ["Wisconsin" "Somerset"])

(in-ns 'cheese.analysis)
cheddars ; exception here
cheese.taxonomy/cheedars

; 3- The macro ns (later)

;;; refer and alias

;; refer
; Calling refer with a symbol allows to refer to the corresponding namespace's
; objects without hacing to use their fully-qualified names
(in-ns 'cheese.analysis)
(clojure.core/refer 'cheese.taxonomy)
cheddars ; yep!
bries

; refer can have the filters :only, :exclude and :rename.
(clojure.core/refer 'cheese.taxonomy :only ['bries])
(clojure.core/refer 'cheese.taxonomy :exclude ['bries])
(clojure.core/refer 'cheese.taxonomy :rename {'bries 'yummy-bries})

; Private functions: when you want a function to be available to other functions
; withing the same namespace:
(in-ns 'cheese.analysis)
; See the - after defn
(defn- private-function
  "Just an example function that does nothing \o/"
  [])

;; alias
; It lets us use a shorter namespace name when using a fully-qualified name
(clojure.core/alias 'taxonomy 'cheese.taxonomy)
taxonomy/bries
