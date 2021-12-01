package mapsassets

import "fmt"

func main() {
	intSet := map[int]bool{}
	vals := []int{5, 10, 2, 5, 8, 7, 3, 9, 1, 2, 10}
	for _, v := range vals { //_:index, v: copy of the element at index
		intSet[v] = true
	}
	fmt.Println(len(vals), len(intSet))
	fmt.Println("5?", intSet[5])
	fmt.Println("500?", intSet[500])
	if intSet[10] {
		fmt.Println("10 is in the set")
	}

}
