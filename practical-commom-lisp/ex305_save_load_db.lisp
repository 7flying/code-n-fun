;;;; 3. Save and load the database

(defun save-db (filename)
	(with-open-file (out filename
						:direction :output
						:if-exists :supersede)
		(with-standard-io-syntax
			(print *db* out))))

(defun load-db (filename)
	(with-open-file (in filename)
		(with-standard-io-syntax
			(setf *db* (read in)))))