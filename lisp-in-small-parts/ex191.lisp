;;;; Ex. 191

; Story-writing program

(defun story ()
  (let ((name (capi:prompt-for-string "Enter alien's name:"))
    (food (capi:prompt-for-string "Enter alien's fav. food:"))
    (colour (capi:prompt-for-string "Enter alien's dress colour:"))
    (happy (if (capi:prompt-for-confirmation "Is the alien happy?") "very" "not")))
    (capi:display-message
      "There once was an alien called ~a who danced eating ~a, always on a ~a dress and was ~a happy"
      name food colour happy)))
