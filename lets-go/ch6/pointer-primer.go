package pointerprimer

import "fmt"

func changeValue(p *int) {
	fmt.Println("At changeValue")
	if p != nil {
		*p = 10
	} else {
		fmt.Println("Supplied pointer is nil")
	}
	
}

func main() {
	x := 10
	pointerToX := &x
	fmt.Println("&x:", &x)
	fmt.Println("pointerToX:", pointerToX)
	fmt.Println("&pointerToX:", &pointerToX)
	fmt.Println("*pointerToX:", *pointerToX)
	z := 5 + *pointerToX
	fmt.Println(z)

	type person struct {
		FirstName string
		MiddleName *string
		LastName string
	}
	p := person{
		FirstName: "Pat",
		//MiddleName: "Perry", // this won't compile
		LastName: "Peterson",
	}
	pMiddleName := "Perry"
	p.MiddleName = &pMiddleName
	fmt.Println(p)
	fmt.Println(*p.MiddleName)

	fmt.Println()
	fmt.Println("Changing value experiments")
	var pInt *int
	fmt.Println(pInt)
	// fmt.Println(*pInt): panic: runtime error: invalid memory address or nil pointer dereference
	changeValue(pInt)
	fmt.Println(pInt)
}
