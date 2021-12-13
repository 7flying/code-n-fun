package callbyvalue

import "fmt"

type person struct {
	age  int
	name string
}

func modifyFails(i int, s string, p person) {
	i *= 2
	s = "Goodbye"
	p.name = "Bob"
}

func modMap(m map[int]string) {
	m[2] = "hello"
	m[3] = "goodbye"
	delete(m,1)
}

func modSlice(s []int) {
	for k, v := range s {
		s[k] = v * 2
	}
	s = append(s, 10)
}

func main() {
	p := person{} // the fields are set to the zero value of each type
	i := 2
	s := "Hello"
	modifyFails(i, s, p)
	fmt.Println(i, s, p)

	m := map[int]string {
		    1: "first",
			2: "second",
	}
	modMap(m)
	fmt.Println(m)

	sl := []int{1, 2, 3}
	modSlice(sl)
	fmt.Println(sl)
}
