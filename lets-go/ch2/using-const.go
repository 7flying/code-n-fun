package usingconst

import "fmt"

const x int64 = 10

const (
	idkey = "id"
	namekey = "name"
)

const z = 20 * 10

func main() {
	const y = "hello"

	fmt.Println(x)
	fmt.Println(y)

	x = x + 1 // error here
	y = "bye" // error here
	fmt.Println(x)
	fmt.Println(y)
}
