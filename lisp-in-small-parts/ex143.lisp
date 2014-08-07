;;;; Ex. 143

; Cubesum:  (a + b) x (a + b) x (a + b)
(defun cubesum (a b)
	(let* ((sum (+ a b)))
		(* sum sum sum)))

(defun cubesum2 (a b)
	(let* ((sum (+ a b))
			(answer (* sum sum sum)))
	answer))
