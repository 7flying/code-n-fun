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
(refer 'cheese.taxonomy)
cheddars ; yep!
bries

; refer can have the filters :only, :exclude and :rename.
(refer 'cheese.taxonomy :only ['bries])
(refer 'cheese.taxonomy :exclude ['bries])
(refer 'cheese.taxonomy :rename {'bries 'yummy-bries})

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

;; use takes the same options as refer
(use '[the-divine-cheese-code.visualization.svg :as svg :only [points]])
; equivalent to:
(require 'the-divine-cheese-code.visualization.svg)
(refer 'the-divine-cheese-code.visualization.svg :only ['points])
(alias 'svg 'the-divine-cheese-code.visualization.svg)

; So we can use:
(= svg/points points)

;; With alias we reach latlng->point
svg/latlng->point
; => no exception

;: But we can't use the bare name
latlng->point ; expection

; 'require' and 'use' load files and optionally 'alias' or 'refer'
; their namespaces

; 3- The ns macro

; ns is like calling in-ns, ns also defaults to referring the clojure.core
; namespace
; (that's why in the the-divine-cheese-code example we can use println without
; its fully-qualified name: clojure.core/println)

; We can control what gets referred from clojure-core with :refer-clojure
(ns the-divine-cheese-code.core
  (:refer-clojure :exclude [println])) ; this makes us use clojure.core/println
; within ns, the form (:refer-clojure) is called a reference
; that code is equivalent to:
(in-ns 'the-divine-cheese-code.core)
(refer 'clojure.core :exclude ['println])


; there are six possible kinds of references within ns:
; (:refer-clojure) (:require) (:use) (:import) (:load) (:gen-class)

; 1- (:require) : similar to the require function
(ns the-divine-cheese-code.core 
  (:require the-divine-cheese-code.visualization.svg))
; equivalent to:
(in-ns 'the-divine-cheese-code.core)
(require 'the-divine-cheese-code.visualization.svg)

; we can alias a lib that we require with ns
(ns the-divine-cheese-code.core
  (:require [the-divine-cheese-code.visualization.svg :as svg]))
; equivalent to:
(in-ns 'the-divine-cheese-code.core)
(require ['the-divine-cheese-code.visualization.svg :as 'svg])
; which is also equivalent to:
(in-ns 'the-divine-cheese-code.core)
(require 'the-divine-cheese-code.visualization.svg)
(alias 'svg 'the-divine-cheese-code.visualization.svg)

; we can require multiple libs in a (:require) reference
(ns the-divine-cheese-code.core
  (:require [the-divine-cheese-code.visualization.svg :as svg]
            [clojure.java.browse :as browse]))
; equivalent to:
(in-ns 'the-divine-cheese-code.core)
(require ['the-divine-cheese-code.visualization.svg :as 'svg])
(require ['clojure.java.browse :as 'browse])

; the main difference between the (:require) reference and the require function
; is that the reference allows us to refer names:
(ns the-divine-cheese-code.core
  (:require [the-divine-cheese-code.visualization.svg :refer [points]]))
; equivalent to:
(in-ns 'the-divine-cheese-code.core)
(require 'the-divine-cheese-code.visualization.svg)
(refer 'the-divine-cheese-code.visualization.svg :only ['points])

; we can also refer all symbols (notice the :all keyword):
(ns the-divine-cheese-code.core
  (:require [the-divine-cheese-code.visualization.svg :refer :all]))

; equivalent to:
(in-ns 'the-divine-cheese-code.core)
(require 'the-divine-cheese-code.visualization.svg)
(refer 'the-divine-cheese-code.visualization.svg)

;; That is the preferred way to require code, alias namespaces and refer symbols
;; It's recommended not to use (:use), but here is an example:

(ns the-divine-cheese-code.core
  (:use clojure.java.browse))
; equivalent to:
(in-ns 'the-divine-cheese-code.core)
(use 'clojure.java.browse)

; This other thing:
(ns the-divine-cheese-code.core
 ; when using a vector, the first symbol is the base and then calls use the base
 ; with each symbol that follows
  (:use [clojure.java browse io]))
; equivalent to
(in-ns 'the-divine-cheese-code.core)
(use 'clojure.java.browse)
(use 'clojure.java.io)
