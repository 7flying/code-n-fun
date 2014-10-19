;;;; Ex. 232

; Find an item on a tree
(defun find-tree (tree item)
  (if (null tree) nil
    (if (string= (first tree) item) t
      (if (find-tree (second tree) item) t
        (find-tree (third tree) item)))))
