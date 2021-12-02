package declaringcallingfunctions

import "fmt"

// MyFuncOpts is bla bla
type MyFuncOpts struct {
	FirstName string
	LastName string
	Age int
}

func myFunC(opts MyFuncOpts) {
	// so something here
	fmt.Println("myFunC was called")
}

func addTo(base int, vals ...int) []int {
	out := make([]int, 0, len(vals))
	for _, v := range vals {
		out = append(out, base + v)
	}
	return out
}

func main() {
	myFunC(MyFuncOpts {
		LastName: "Patel",
		Age: 50,
	})
	myFunC(MyFuncOpts {
		FirstName: "Joe",
		LastName: "Smith",
	})

	fmt.Println(addTo(3, 2))
	fmt.Println(addTo(3, 2, 1))
	a := []int{2, 3}
	fmt.Println(addTo(3, a...))
	fmt.Println(addTo(3, []int{1, 2, 3, 4, 5}...))
}
