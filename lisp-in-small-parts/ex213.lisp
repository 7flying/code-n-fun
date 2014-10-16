;;;; Ex. 213

; Find whether each number in a list is even or odd
(defun evenp-list (numbers)
  (if (null numbers) nil 
      (cons (evenp (first numbers)) (evenp-list (rest numbers)))))