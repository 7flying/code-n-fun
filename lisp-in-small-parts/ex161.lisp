;;;; Ex. 161

; Reverse all but the first and last letters of a word
(defun midverse (word)
	(concatenate 'string
		(subseq word 0 1)
		(subseq (reverse (subseq word 1)) 1)
		(subseq (reverse word) 0 1)))

(defun midverse2 (word)
	(concatenate 'string
		(subseq word 0 1)
		(reverse (subseq word 1 (- (length word) 1)))
		(subseq word (- (length word) 1))))