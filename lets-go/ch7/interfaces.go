package main

import "fmt"

type LogicProvider struct {}

func (lp LogicProvider) Process(data string) string {
	return "bla bla"
}

type Logic interface {
	Process(data string) string
}

type Client struct{
	L Logic
}

func (c Client) Program() {
	data := "stuff"
	c.L.Process(data)
}

func main() {
	c := Client{
		// since it has the Proces(data string) method, it implements the interface
		// and thus, it can be assigned to the type of the interface
		L: LogicProvider{},
	}
	c.Program()

	var s *string
	fmt.Println(s == nil)
	var i interface{}
	fmt.Println(i == nil)
	i = s
	fmt.Println(i == nil)
}
