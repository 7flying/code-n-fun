;;;; Ex. 211

; Reverse each string in a list of strings
(reverse-list (mylst)
  (if (null mylst) nil
    (cons (reverse (first mylst)) (reverse-list (rest mylst)))))
