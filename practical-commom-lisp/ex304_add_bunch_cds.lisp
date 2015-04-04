;;;; 3. Add a bunch of CDs

(defun add-cds()
	(loop (add-record (prompt-for-cd))
		(if (not (y-or-n-p "Another? [y/n]: ")) (return))))