package gostrings

import "fmt"

func main() {
	var s string = "Hello☀️"
	var s2 string = s[4:7]
	var s3 string = s[:5]
	var s4 string = s[6:]
	var test string = "☀️"
	fmt.Println("s:", s, len(s))
	fmt.Println("s2:", s2, len(s2))
	fmt.Println("s3:", s3, len(s3))
	fmt.Println("s4:", s4, len(s4))
	fmt.Println(test, len(test))

	var a rune = 'A'
	var ss string = string(a)
	var b byte = 'A'
	var sss string = string(b)
	fmt.Println(a, ss, b, sss)  // 65 A 65 A

	var sixtyFive int = 65
	var y = string(sixtyFive)
	fmt.Println(y) // This will print "A", not "65"
	var rSixtyFive rune = rune(sixtyFive)
	fmt.Println(rSixtyFive) // This prints "65"

	var bs []byte = []byte(s)
	var rs []rune = []rune(s)
	fmt.Println("bs:", bs)
	fmt.Println("rs:", rs)
}
