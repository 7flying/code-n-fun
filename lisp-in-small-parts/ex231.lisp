;;;; Ex. 231

; Count the items on a tree

(defun count-tree-rec (tree i)
  (if (null tree) i
    (progn
      (+ i 1)
      (count-tree-rec (second tree) i)
      (count-tree-rec (third tree) i))))

(defun count-tree (tree)
  (count-tree-rec (tee 0)))
