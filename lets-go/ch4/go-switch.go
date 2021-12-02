package main

import "fmt"

func main() {
	//                1      3         5       6            7
	words := []string{"a", "cow", "smile", "gopher", "octopus",
		"anthropologist"} //14
	for _, word := range words {
		fmt.Println("- Handling", word, "len:", len(word))
		switch size := len(word); size {
		case 1, 2, 3, 4:
			fmt.Println(word, "is a short word!")
		case 5:
			wordLen := len(word)
			fmt.Println(word, "is exactly the right lenght:", wordLen)
		case 6, 7, 8, 9:
		default:
			fmt.Println(word, "is a long word")
		}
	}
	// blank switch example
	fmt.Println()
	fmt.Println("Blank switch example")
	for _, word := range words {
		switch wordLen := len(word); { // take note of the ; there
		case wordLen < 5:
			fmt.Println(word, "is a short word")
		case wordLen > 10:
			fmt.Println(word, "is a long word")
		default:
			fmt.Println(word, "is the right lenght")
		}
	}
}
