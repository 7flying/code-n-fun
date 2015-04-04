;;;; Ex. 143

; Returns a random element of a list
(defun random-elt (lst)
	(nth (random (length lst)) lst))
