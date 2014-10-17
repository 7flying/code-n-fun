;;;; Ex. 214

; Find the maximum element of a list
(defun max-list (numbers)
  (let* ((x (first numbers))))
    (dolist (i (rest numbers)) (if (> i x) (setf x i))) x)
