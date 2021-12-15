package composition

import "fmt"

type Employee struct {
    Name string
    ID string
}

func (e Employee) Description() string {
    return fmt.Sprintf("%s (%s)", e.Name, e.ID)
}

type Manager struct {
    Employee // this is an embedded field
    Reports []Employee
}

func (m Manager) FindNewEmployees() []Employee {
    // bla bla
	
	return []Employee{}
}

func main() {
	m := Manager{
		Employee: Employee{
			Name: "Bob Bobston",
			ID: "123456",
		},
		Reports: []Employee{},
	}
	fmt.Println(m.ID)
	fmt.Println(m.Description())
}
