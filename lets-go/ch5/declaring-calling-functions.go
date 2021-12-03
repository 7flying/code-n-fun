package declaringcallingfunctions

import (
	"errors"
	"fmt"
	"os"
	"time"
	"math/rand"
)

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

func divAndRemainder(numerator int, denominator int) (int, int, error) {
	if denominator == 0 {
		return 0, 0, errors.New("cannot divide by zero")
	}
	return numerator / denominator, numerator % denominator, nil
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

	
	s := rand.NewSource(time.Now().UnixNano())
	r := rand.New(s)

	result, remainder, err := divAndRemainder(r.Intn(10), r.Intn(10))
	if err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
	fmt.Println(result, remainder)
}
