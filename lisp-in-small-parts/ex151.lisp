;;;; Ex. 151

(defun swap (lst)
	(cons (second lst) (cons (first lst) (rest (rest lst)))))
