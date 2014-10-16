;;;; Ex. 182

; Returns T if its parameter is a list of two numbers like (2 3), and nil otherwise

(defun numberpair (lst)
	(if (and (listp lst)
			 (= 2 (length lst))
			 (numberp (first lst))
			 (numberp (second lst)))
		T
		nil))
