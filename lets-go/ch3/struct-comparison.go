package structcomparison

import "fmt"

func main() {
	type firstPerson struct {
		name string
		age int
	}
	var g struct {
		name string
		age int
	}

	f := firstPerson {
		name: "Bob",
		age: 50,
	}

	g = f
	fmt.Println(f == g) // true
}
