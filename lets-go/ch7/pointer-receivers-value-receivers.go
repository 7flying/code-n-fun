package pointerreceivers

import (
	"fmt"
	"time"
)

type Counter struct {
	total int
	lastUpdated time.Time
}

func (c *Counter) Increment() {
	c.total++
	c.lastUpdated = time.Now()
}

func (c Counter) String() string {
	return fmt.Sprintf("total: %d, last updated: %v", c.total, c.lastUpdated)
}

func main() {
	var c Counter // c is a value type, it is coverted to (&c).Increment()
	fmt.Println(c.String())
	c.Increment()
	fmt.Println(c.String())
}
