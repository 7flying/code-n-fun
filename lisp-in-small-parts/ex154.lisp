;;;; Ex. 154

; Returns the last item in a list
(defun last-elt (lst)
	(nth (- (length lst) 1) lst))
