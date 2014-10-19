;;;; 3. Read from user and make a cd

(defun prompt-read (prompt) ; Returns string
	(format *query-io* "~a: " prompt)
	(force-output *query-io*)
	(read-line *query-io*))

(defun promt-for-cd ()
	(make-cd
		(prompt-read "Tittle")
		(prompt-read "Artist")
		(or (parse-integer (prompt-read "Rating") :junk-allowed t) 0)
		(y-or-n-p "Ripped [y/n]: ")))