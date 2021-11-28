package complexnums

import (
	"fmt"
	"math/cmplx"
)

func main() {
	x := complex(2.5, 3.1)
	y := complex(10.2, 3)
	fmt.Println(x + y)
	fmt.Println(x - y)
	fmt.Println(real(x))
	fmt.Println(imag(y))
	fmt.Println(cmplx.Abs(x))
}
