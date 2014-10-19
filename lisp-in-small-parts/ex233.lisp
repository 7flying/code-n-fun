;;;; Ex. 233

; Find the nth Fibonacci number
(defun fib (n)
  (if (< n 3) 1)
   (+ (fib (- n 1))
     (fib (- n 2)) ))
