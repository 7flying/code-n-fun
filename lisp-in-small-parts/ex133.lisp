;;;; Ex. 133

(defun dice()
	(+ 1 (random 6)))

(defun two-dice()
	(+ (dice) (dice)))

(defun two-dice-two ()
  (+ (+ 1 (random 6)) (+ 1 (random 6))))
