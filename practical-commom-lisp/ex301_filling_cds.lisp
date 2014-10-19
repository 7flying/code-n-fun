;;;; 3. global variables, add record and dump-db

(defvar *db* nil)

(defun add-record (cd) (push cd *db*))

(defun dump-db ()
	(dolist (cd *db*)
		(format t "~{~a:~10t~a~%~}~%" cd)))