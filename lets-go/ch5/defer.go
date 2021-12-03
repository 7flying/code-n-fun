package defergo

import "fmt"

func main() {
	defer fmt.Println("world") // this is the second defer
	defer fmt.Println("!") // this is the first defer
	fmt.Println("Hello ")
}
