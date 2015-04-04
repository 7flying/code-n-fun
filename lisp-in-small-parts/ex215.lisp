;;;; Ex. 215

; Duplicate each element in a list
(defun dupli (elements)
  (if (null elements) nil
    (append (cons (first elements) (list (first elements)))
      (dupli (rest elements))))))
