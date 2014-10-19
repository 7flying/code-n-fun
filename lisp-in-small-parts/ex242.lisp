;;;; Ex. 242

; Combine a list of numbers using a binary operator
(defun combine (operator items)
  (if (null (third items))
    (funcall operator (first items) (second items))
    (funcall operator (first items) (combine operator (rest items)))
