package forfourways

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	// C style for
	for i := 0; i < 10; i++ {
		fmt.Print(i, " ")
	}
	fmt.Println()

	s := rand.NewSource(time.Now().UnixNano())
	r := rand.New(s)

	// conditional-only for
	i := r.Intn(10)
	if i == 0 {
		i++
	}
	for i < 10 {
		fmt.Print(i, " ")
		i *= 2
	}
	fmt.Println()

	// endless loops
	i = r.Intn(100)
	for {
		fmt.Println("i is:", i)
		i += 10
		if i == 100 {
			fmt.Println("i is exactly 100")
			continue
		}
		if i > 100 {
			fmt.Println("We are done")
			break
		}
	}

	// for-range loops
	evenVals := []int{2, 4, 6, 8, 10}
	for i, v := range evenVals { // We are shadowing i here
		fmt.Println(i, v)
	}
	for _, v := range evenVals {
		fmt.Println(v)
	}
	uniqueNames := map[string]bool{"Fred": true, "Raul": true, "Wilma": true}
	for k := range uniqueNames {
		fmt.Println(k)
	}

	// iterating over maps
	fmt.Println("Ierating over maps")
	m := map[string]int{
		"a": 1,
		"c": 3,
		"b": 2,
	}
	for i := 0; i < 3; i++ {
		for k, v := range m {
			fmt.Println(k, v)
		}
		fmt.Println("")
	}

	// iterating over strings
	fmt.Println("Iterating over strings")
	samples := []string{"hello", "world", "apple_π!"}
	for _, sample := range samples {
		for i, r := range sample {
			fmt.Println(i, r, string(r))
		}
		fmt.Println()
	}
	
	//
	fmt.Println("Modifying the value does not modify the source")
	fmt.Println("- evenVals:", evenVals)
	for _, v := range evenVals {
		v *= 2
	}
	fmt.Println("- evenVals:", evenVals)
}
