package iftests

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	if n := rand.Intn(10); n == 0 { // this will always return 1 since the seed is hardcoded
		fmt.Println("Too low", n)
	} else if n > 5 {
		fmt.Println("Too high", n)
	} else {
		fmt.Println("Good number", n)
	}
	fmt.Println("Now with a random number!")
	s := rand.NewSource(time.Now().UnixNano())
	r := rand.New(s)
	if n := r.Intn(10); n == 0 {
		fmt.Println("Too low", n)
	} else if n > 5 {
		fmt.Println("Too high", n)
	} else {
		fmt.Println("Good rumber", n)
	}
}
