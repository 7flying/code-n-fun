;;;; Ex. 211

; Count the number of elements in a list
(defun count-list (lst)
  (if (null lst) 0
  	(+ 1 (count-list (rest lst)))))

