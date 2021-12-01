package commaokidiom

import "fmt"

func main() {
	m := map[string]int{
    "hello": 5,
    "world": 0,
	}
	v, ok := m["hello"]
	fmt.Println(v, ok)
	v, ok = m["go"]
	fmt.Println(v, ok)
}
