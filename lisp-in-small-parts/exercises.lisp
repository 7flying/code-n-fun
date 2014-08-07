(defun swap (list)
  (cons (nth 1 list)
        (cons (first list)
              (rest (rest list)))))
