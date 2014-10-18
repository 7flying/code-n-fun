;;;; Ex. 234

; Find a number on Pascal's triangle 
; (gives the nth number in the rth row)
(defun pascal (number row)
  (if (= number 1) 1
    (if (= number row) 1
      (+ (pascal (- number 1) (- row 1)) (pascal number (- row 1))))))
