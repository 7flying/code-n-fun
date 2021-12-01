package sliceswithoverlappingstorage

import "fmt"

func main() {
	x := []int{1, 2, 3, 4}
	y := x[:2] // 1, 2
	z := x[1:] // 2, 3, 4
	x[1] = 20 // 1, 20, 3, 4
	y[0] = 10  // 10, 2
	z[1] = 30 // 2, 30, 4
	// x = 10, 20, 30, 4
	// y = 10, 20
	// z = 20, 30, 4
	fmt.Println("x:", x)
	fmt.Println("y:", y)
	fmt.Println("z:", z)
}
