package confusingslices

import "fmt"

func main() {
	x := make([]int, 0, 5)
	x = append(x, 1, 2, 3, 4)
	fmt.Println(x)
	fmt.Println("x - len:", len(x), "cap:", cap(x))
	y := x[:2]
	z := x[2:]
	fmt.Println("y - len:", len(y), "cap:", cap(y))
	fmt.Println("z - len:", len(z), "cap:", cap(z))
	y = append(y, 30, 40, 50)
	fmt.Println("y:", y, " - len:", len(y), "cap:", cap(y))
	fmt.Println("z:", z, " - len:", len(z), "cap:", cap(z))
	fmt.Println("x:", x)
	x = append(x, 60)
	fmt.Println("x:", x)
	fmt.Println("y:", y)
	z = append(z, 70)
	fmt.Println("x:", x)
	fmt.Println("y:", y)
	fmt.Println("z:", z)
}
