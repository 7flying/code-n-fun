;;;; Ex. 216

; Eliminate consecutive duplicates in a list
(defun compress (elements)
  (if (null elements) nil
    (if (eq (first elements) (first (rest elements)))
      (compress (rest elements))
      (cons (first elements) (compress (rest elements))))))
