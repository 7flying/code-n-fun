;;;; Ex. 181

; Check whether a string is a palindrome

(defun palindrome? (word)
	(if (string= word (reverse word))
		T
		nil))
