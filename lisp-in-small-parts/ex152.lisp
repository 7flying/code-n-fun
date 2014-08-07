;;;; Ex. 142

; Duplicates the first item on a list
(defun dup (lst)
	(cons (first lst) (cons (first lst) (rest lst))))
