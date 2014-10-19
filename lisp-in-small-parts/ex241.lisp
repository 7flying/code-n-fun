;;;; Ex. 241

; Repeat a procedure for a range of numbers
(defun repeat-for (from to operator)
  (if (> from to) nil
    (progn
      (funcall operator from)
      (repeat-for (+ from 1) to operator))))
