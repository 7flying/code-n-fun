;;;; 3. dump-db in a better way

(defun dump-db ()
	(format t "~{~{~a:~10t~a~%~}~%~}"))