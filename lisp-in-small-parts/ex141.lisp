;;;; Ex. 141

(defparameter *kilmile* 0.621371192)

(defun convert-km (miles)
 	(/ miles *kilmile*))

(defun convert-miles (km)
	(* km *kilmile*))