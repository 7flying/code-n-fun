package binarytree

import (
	"fmt"
)

// IntTree defines a binary tree holding ints
type IntTree struct {
	val int
	left, right *IntTree
}

func (it *IntTree) Insert(val int) *IntTree {
	if it == nil {
		return &IntTree{val: val}
	}
	if val < it.val {
		it.left = it.left.Insert(val)
	} else if val > it.val {
		it.right = it.right.Insert(val)
	}
	return it
}

func (it *IntTree) Contains(val int) bool {
	switch {
	case it == nil:
		return false
	case val < it.val:
		return it.left.Contains(val)
	case val > it.val:
		return it.right.Contains(val)
	default:
		return true
	}
}

func main() {
	var it *IntTree
	it = it.Insert(5)
	it = it.Insert(3)
	fmt.Println(it.Contains(2))
	fmt.Println(it.Contains(5))
}
