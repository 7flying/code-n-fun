;;;; Ex. 217

; Interleave two lists
(defun interleave (lone ltwo)
  (if (null lone) nil
    (cons (first lone)
      (cons (first ltwo)
        (interleave (rest lone) (rest ltwo))))))
