
## Links

* [Effective Go](https://go.dev/doc/effective_go)
* [Code Review Comments](https://github.com/golang/go/wiki/CodeReviewComments)

## Tools

* Code format checker: ``go fmt``, also use ``goimports -l -w``
* Linter: ``golint ./...``
* Code checker: ``go vet ./...``
* Detecting shadowed variables: ``shadow ./..``
  Get it at: ``$ go install golang.org/x/tools/go/analysis/passes/shadow/cmd/shadow@latest``

## The semicolon insertion rule

The lexer inserts a semicolon after these tokens: identifiers (``int``), basic
literals (numbers or string constants), break, continue, fallthrough, return,
++, --, ), (, }.

# Primitive types and declarations

1. [Built-in types](#built-in-types)
    1. [Literals](#literals)
    2. [Booleans](#booleans)
    3. [Numeric types](#numeric-types)
    4. [Strings and Runes](#strings-and-runes)
    5. [Explicit type conversion](#explicit-type-conversion)
2. [var VS :=](#var-vs-)
3. [Using const](#using-const)
4. [Typed and untyped constants](#typed-and-untyped-constants)
5. [Unused variables](#unused-variables)
6. [Naming variables and constants](#naming-variables-and-constants)

## Built-in types

Go inserts a default *zero value* to any variable that is declared but not
assigned a value.

Built-in types: literals, booleans, numeric types, strings, runes.

### Literals

Refer to numbers, characters or strings.

* *Integer literals*: 0b binary, 0o octal (or just with a leading 0 with no
  letter), 0x hexadecimal.
  
  You can write a ``_`` between numbers that has no meaning, but may make
  things easier to read. ``1234`` is the same as ``1_234, 1_2_3_4`` etc.

* *Floating point literals*: have decimal points, we can use ``e`` to indicate an
  exponent. ``6.03e123``

* *Rune literals*: are surrounded by single quotes ``'a'``, ``'\141'`` (8-bit
  octal), ``'\x16'`` (8-bit hexadecimal), ``'\u0061'`` (16-bit hexadecimal),
  ``'\U00000061'``(16-bit Unicode number).

* *String literals*: two ways 1) *interpreted string literals*, which use double
  quotes ``"interpreted string linteral"``. To make these multi-line use
  ``\n``. 2) *raw string literals*, which use backquotes `` `raw string literal`
  ``. To make these multi-line just write them in the way that you want them.


Literals are untyped and can interact with any variable compatible with the
literal (aka assign an integer literal to a floating point variable).

When the type is not explicitly declared Go uses the *default type* for a
literal.

### Booleans

Zero value is ``false``.

```go
var flag bool
var isAwesome = true
```

### Numeric types

* Integer types: zero value is zero.

  Types of integers: ``int8-16-32-64`` and ``uint8-16-32-64``.
  
  Special types:
  
  * ``byte``: an alias of ``uint8``. 
  * ``int``: is ``int32`` on 32-bit CPUs and ``int64``on 64-bit CPUs.
  
      It is a compile-time error to assign, compare, or perform mathematical ops
      between ``int``, ``int32`` or ``int64`` without a type conversion.
  * ``uint``: same rules as ``int``
  
  Integer literals default to being of ``int`` type.
  
 TODO: Complete notes further on related to ``rune`` and ``uintptr``.
  
  **-->** If a function needs to work on any integer type, we need two
  functions, one with ``int64`` for its parameters and variables and another
  one with ``uint64``. In any other case just use ``int``.
  
  * The result of an integer division is an integer, if we need a floating
  point result a type conversion is needed. 

* Floating point types: zero value is zero.

  Two floating point types: ``float32``and ``float64``.
  
  Floating point literals have a default type of ``float64``.
  
  **-->** by default use ``float64``
  
  Floating point types are stored using the [IEEE
  754](https://en.wikipedia.org/wiki/IEEE_754) specification.
  
  You can use all the standard mathematical and comparison operators with
  floats except ``%``.
  
  If you divide a nonzero float by 0 it will return ``+Inf`` or
  ``-Inf``. Dividing a zero float by 0 returns ``NaN``.
  
  **-->** Do not use floats to represent a decimal value exactly.
  
  **-->** Do not use ``==`` and ``!=`` to compare floats.

* Complex types. ``complex64`` and ``complex128``. The use ``float32``and
  ``float64`` underneath.

### Strings and Runes

Zero value for a string is the empty string ``""``.

Strings are immutable, you can reassign the value of a string variable but you
cannot change the value.

The ``rune`` type is an alias for ``int32`` type.

TODO: Complete notes further on.

### Explicit type conversion

Go doesn't allow *automatic type promotion*, we must use *type conversion* when
variables do not match.

## var VS :=

* ``var`` declarations

    1. ``var`` explicit type and an assignment:
    
        ```go
        var x int = 10
        ```
        To declare it and assign it the zero value we can skip the ``=``
        ```go
            var x int
        ```
        
    2. If the right hand side is the expected default type we can ignore the type:
    
        ```go
        var x = 10
        ```
    3. We can declare multiple variables at once:
       ```go
       var x, y int = 10, 20
       ```
       and assign them the zero value at once:
       ```go
       var x, y int
       ```
       they can also be of different types:
       ```go
       var x, y = 10, "hello"
       ```
    4. We can declare multiple variables at once wrapping them in a *declaration
    list*:
        ```go
        var (
            x int
            y = 20
            z int = 30
            d, e = 40, "hello"
            f, g string
            )
        ```
* ``:=`` declarations
    1. Within a function use the ``:=``operator to replace a ``var``
       declaration that uses type inference.
       ```go
       var x = 10
       x:= 10
       ```
    2. We can declare multiple variables at once:
       ```go
       var x, y = 10, "hello"
       x, y := 10, "hello"
       ```
    3. It allows us to *assign values* to existing variables:
       ```go
       x := 10
       x, y := 30, "hello"
       ```
    **-->** it is not legal to use ``:=``at package level, so if we are
       declaring a variable there, we must use ``var``.

**-->** The most common declaration style within functions is ``:=``.

**-->** Avoid ``:=`` when: 1) initializing a variable to its zero value use
``var x int``; 2) assigning an untyped constant or a literal to a variable and
the default type is not the type that we want, use the long ``var`` declaration
form e.g. ``var x byte = 20``.
     
## Using const

``const`` is used to declare a value immutable. They can only hold values that
the compiler can figure out at compile time.

They can be: numeric literals, ``true`` and ``false``, strings, runes, built-in
functions ``complex``, ``real``, ``imag``, ``len`` and ``cap``, expressions
that consist of operators and the preceding values (``const x = 20 * 10``).

**-->** Go doesn't have a way to specify that a value calculated at runtime is
immutable.

**-->** The compiler allows us to create unread constants (see Unused
variables) with ``const`` since constants in Go are calculated at compile
time.

## Typed and untyped constants

Constants can be typed or untyped. Untyped constants have no type of their own
but they do have a default type that is used when no other type can be
inferred.

Leaving a constant untyped gives us more flexibility since a typed constant can
only be directly assigned to a variable of that type.

Untyped constant and assignments:
```go
const x = 10
var y int = x
var z float64 = x
var d byte = x
```

## Unused variables

Is a compile-time error to declare a local variable and not reading its value.

## Naming variables and constants

Go uses ``camelCase`` for variable names

**-->** Go uses the case of the first letter in the name of a package-level
declaration to determine if the item is accessible outside the package.

# Composite types

1. [Arrays](#arrays)
2. [Slices](#slices)
   1. [len](#len)
   2. [append](#append)
   3. [Capacity](#capacity)
   4. [make](#make)
   5. [Declaring slices](#declaring-slices)
   6. [Slicing slices](#slicing-slices)
   7. [Converting arrays to slices](#converting-arrays-to-slices)
   8. [copy](#copy)
3. [Strings and runes and bytes](#strings-and-runes-and-bytes)
4. [Maps](#maps)
   1. [The comma ok idiom](#the-comma-ok-idiom)
   2. [Using maps as sets](#using-maps-as-sets)
5. [Structs](#structs)
   1. [Anonymous structs](#anonymous-structs)
   2. [Comparing and converting structs](#comparing-and-converting-structs)

## Arrays

Different ways to declare an array:

1. ``var x[3]int``: array of three ``int``s, they're initialized to the zero
   value for an ``int`` (zero)
2. *Array literal*: This specifies the initial
   values of the array.
   ```go
   var x = [3]int{10, 20, 30}
   ```
   
   In this case we can skip specifying the number of elements:
   ```go
   var x = [...]int{10, 20, 30}
   ```
3. *Sparse array*: aka an array with most of its elements set to their zero
   value, we only need to specify which values are not zero. To do so we
   specify the index and its value.
   
   ``var x = [12]int{1, 5: 4, 6, 10: 100, 15}`` which will give us ``[1, 0, 0,
   0, 0, 4, 6, 0, 0, 0, 100, 15]``

We can use ``==`` and ``!=`` to compare arrays.
```go
var x = [...]int{1, 2, 3}
var y = [3]int{1, 2, 3}
fmt.Println(x == y) // true
```

Go only has one-dimensional arrays but we can simulate multidimensional arrays:
``var x[2][3]int``: x is an array of length 2 whose type is an array of
``int``s of length 3.

We read and write arrays using bracket syntax:
```go
x[0] = 10
fmt.Println(x[2])
fmt.Println(len(x))
```

**-->** Go considers the *size* of the array to be part of the *type*, so an
array of type ``[3]int`` is different from an array of type ``[2]int``.

**-->** Thereby, we cannot use a variable to specify the size of an array,
since types must be resolved at compile time, not at runtime.

**-->** We cannot use a type conversion to convert arrays of different sizes to
identical types.

## Slices

In slices the length is not part of the type, removing the limitation that
arrays had.

The zero value for a slice is ``nil``. It is an identifier that represents the
lack of value for some types. ``nil`` has no type, so it can be assigned or
compared against values of different types.

Declaring slices:

1. *Slice literal*:
   ```go
   var x = []int{10, 20, 30}
   ```
   Note that there are no ``[...]`` inside the  brackets, if there were it would be an array instead of a slice.

   As in arrays, we can also specify the indexes in which the values are not
   the zero value:
   ```go
   var x = []int{1, 5: 4, 6, 10: 100, 15}
   ```
   The slice has 12  ``int``s with the following values: ``[1, 0, 0, 0, 0, 4, 6, 0, 0, 0, 100,
   15]``
2. We can declare a slice without using a literal:
   ```go
   var x []int
   ```
   Since no values are assigned, ``x`` is assigned the zero value for a slice,
   which was ``nil``.
  
We can also simulate multidimensional slices: 
```go
var x[][]int
```

Read and write slices using the bracket syntax:

```go
x[0] = 10
fmt.Println(x[0])
```

A slice *is not comparable*. It is a compile-time error to use ``==`` or
``!=`` in slices. You can only compare a slice with ``nil``.

```go
var x []int
fmt.Println(x == nil) // true
```

### len

When you pass a ``nil`` slice to ``len`` it returns 0.

### append

``append`` is used to grow slices, we can use it with ``nil`` slices or slices that already
have elements:

```go
var x []int
x = append(x, 10)
// or:
var x = []int{1, 2, 3}
x = append(y, 4)
```
We can append more than one value at a time: ``x = append(x, 5, 6, 7)``

We can append one slice onto another using the ``...``operator to expand the
source slice into individual values:

```go
y := []int{20, 30, 40}
x = append(x, y...)
```

**-->** It is a compile-time error if we do not assign the value returned from ``append``.

### Capacity

The built-in ``cap`` function returns the current capacity of the slice, which
is the number of consecutive memory locations reserved.

The capacity can be larger than the length, when the length reaches the
capacity and we try to ``append`` more values, the Go runtime will allocate a
new slice with larger capacity. The Go runtime doubles the size of the slice
when the capacity is less than 1024, and it grows it by at least 25%
afterwards. 

### make

The ``make`` function allows us to specify the type, length and optionally the
capacity of the slice:

```go
x := make([]int, 5) // length of 5, capacity of 5
```

The elements 0-4 are present and are all initialized to the zero value.

We can also specify an initial capacity:
```go
x := make([]int, 0, 10) // length of 0, capacity of 10
```
In this example the slice is non-nil and a capacity of 10. We can append values
to it but we cannot index into it unless we add values.

### Declaring slices

**-->** Goal: minimise the number of times the slice needs to grow.

1. To declare a ``nil`` slice (e.g. when returning from a function):
    ```go
    var data[] int
    ```
2. Empty slice:
   ```go
   var x = []int{}
   ```
    This is a non-nil zero-length slice.
3. Slice literal when we have some starting values:
    ```go
    data := []int{2, 3, 4, 5}
    ```
4. When we know the size of the data, but we don't know what data it is going
   to be in the slice we would use ``make``.
   
   Then, we might specify a nonzero length or a zero length with nonzero
   capacity.
   
   - If the slice is going to be a buffer: nonzero length.
   - In other cases use ``make`` with zero length and the required
     capacity. Then we can use ``append`` to add items to the slice.
     ```go
     x := make([]int, 0, 10)
     x = append(x, 5,6,7,8)
     ```
### Slicing slices

A *slice expression* creates a slice from another slice, we need to use the
``[x:y]`` operator. If ``x`` is not specified, its default value is 0; if ``y``
is not specified, its default value is the end of the slice. ``x`` is taken
from the slice up to ``y - 1``

**->** When we make a slice from a slice **memory is shared**, we **are not
making a copy**.

The capacity of a sub-slice is: ``cap(original) - x ``

Never use ``append`` with a sub-slice or make sure that it doesn't cause an
overwrite by using a *full slice expression*: ``[x:y:z]``, where ``z``
indicates the last position in the parent's capacity that is available for the
sub-slice. The capacity of the sub-slice is ``z-x``.

### Converting arrays to slices

You can take a slice from an array using a slice expression. Taking a slice
from an array has the same memory-sharing properties as taking a slice from a
slice.

```go
x := [4]int{5, 6, 7, 8}
y := x[:2]
z := x[2:]
x[0] = 10
// x: 10 6 7 8
// y: 10 6
// z: 7 8
```

### copy

To create a slice that is independent of the original we use: ``copy(dest,
src)`` (returns the number of elements copied). It is limited by whichever
slice is smaller.
```go
x := []int{1, 2, 3, 4}
y := make([]int, 4)
num := copy(y, x)
fmt.Println(y, num)
```

So for instance, due to the size limitation here we would only copy two
elements (since destination is smaller):
```go
x := []int{1, 2, 3, 4}
y := make([]int, 2)
copy(y, x)
```

We can also copy from the middle of the source:
```go
x := []int{1, 2, 3, 4}
y := make([]int, 2)
copy(y, x[2:])
```

And also overlapping sections (first is read, and then written):
```go
x := []int{1, 2, 3, 4}
copy(x[:3], x[1:]) // x now has: 2 3 4 4
```

We can also use ``copy`` with arrays specifying a slice of the array.

## Strings and runes and bytes

Go uses a sequence of bytes to represent a string, by default it is assumed
that the string is composed of a sequence of UTF-8-encoded *code points*.

**-->** **UTF-8 info.** A *code point* is what we use to represent a character,
and in UTF-8 we (usually) use one byte (8 bits) for that. For Unicode
characters whose values are below 128 (letters, numbers and punctuation symbols
used in English) we need only 1 byte, but UTF-8 might expand to a maximum of 4
bytes to represent some Unicode code points that have larger values. This means
that we cannot randomly access a string encoded with UTF-8 since each code
point might be between 1 and 4 bytes long.

```go
var s string = "Hello there"
var b byte = s[6]
var s2 string = s[2:4]
```

Strings *are immutable*, so when making substrings we won't have the memory
sharing problems that we had with slices. But, we need to take into account
that *a string is a sequence of bytes*, while a code point in UTF-8 might be
from 1 to 4 bytes long, thereby when dealing with other languages other than
English, emojis or other stuff we might run into problems since each code point
might have a different size.

A single ``rune`` or ``byte`` can be converted to a string:

```go
var a rune = 'A'
var ss string = string(a)
var b byte = 'A'
var sss string = string(b)
fmt.Println(a, ss, b, sss) // 65 A 65 A
```
**-->** Remember that ``rune`` is an alias for the ``int32`` type.


Example: let's convert the integer 65 to a string "65"
```go
var x int = 65
var y = string(x)
fmt.Println(y) // This will print "A", not "65"
var rSixtyfive rune = rune(sixtyFive)
fmt.Println(rSixtyfive) // this prints "65"
```
**-->** To extract substrings and code points from strings we need to use the
functions in the ``strings`` and ``unicode/utf8`` packages in the standard
library.

## Maps

Map types are written as ``map[keyType]valueType``.

The zero value for a map is ``nil`` and a ``nil`` map has a length of zero. We
can read a ``nil`` map, it will return the zero value for the map's value type,
but writing to a ``nil`` map will cause a panic.

1. ``var`` declaration; sets the map to its zero value (``nil``)
   ```go
   var nilMap map[string]int
   ```
2. *Empty map literal*, with a ``:=`` declaration:
   ```go
   totalWins := map[string]int{}
   ```
    This an empty map literal, it has a length of 0, but we can read and write
   into it.
   
   This a *nonempty map literal*:
   
   ```go
   // this map takes a string as key and has a slice of strings 
   // for its value
   teams := map[string][]string {
       "One": []string{"Player 1", "Player 2"},
       "Two": []string{"Player 3"},
   }
   ```
3. Map with a default size
   ```go
   ages := make(map[int][]string, 10)
   ```
    This map has a length of 0, and it can grow to hold more than the initial
   size.
   
**-->** Maps are not comparable. We can check if they're equal to ``nil``, but
that's it.

**-->** The key type must have an ``==`` (equality operator), otherwise it
cannot be the key of the map (no maps, slices or funcs as keys).

**-->** The ``map`` that Go uses it is a *hash map*.

We read and write to a map like so:

```go
totalWins := map[string]int{}
totalWins["Team A"] = 1
totalWins["Team B"] = 2
fmt.Println(totalWins["Team A"])
totalWins["Team C"]++ // the default value of int is 0, we can do this
fmt.Println(totalWins["Team C"])
``` 

We delete key-value pairs with the built-in ``delete`` function, it takes the
key of the key-value pair that we want to delete. If the key doesn't exist or
if the map is ``nil`` nothing happens.
```go
m := map[string]int {
    "hello": 4,
    "world": 5,
}
delete(m, "hello")
```

### The comma ok idiom

Since maps return the zero value when we ask for a key that it is not in the
map, we need to differentiate if a key is in a map (and it could be the zero
value for that type of value) or not. To do this we use the *comma ok idiom*.
```go
m := map[string]int{
    "hello": 5,
    "world": 0,
}
v, ok = m["hello"]

```

### Using maps as sets

We can use maps to simulate sets (a data type that ensures there is at most one
of a value).

We use the key of the map for type that we would put into the set, and use a
``bool`` for the value.

For example:
```go
intSet := map[int]bool{}
vals := []int{5, 10, 2, 5, 8, 7, 3, 9, 1, 2, 10}
for _, v := range vals { //_:index, v: copy of the element at index
    intSet[v] = true
}
fmt.Println(len(vals), len(intSet))
fmt.Println("5?", intSet[5])
fmt.Println("500?", intSet[500])
if intSet[10] {
    fmt.Println("10 is in the set")
}
```

We can also use ``struct{}`` for the value instead of a ``bool``, since
booleans use one byte and an empty struct uses zero bytes. However, using a
``struct{}`` makes the code less obvious.

```go
intSet := map[int]struct{}{}
vals := []int{5, 10, 2, 5, 8, 7, 3, 9, 1, 2, 10}
for _, v := range vals {
    intSet[v] = struct{}{}
}
if _, ok := intSet[10]; ok {
    fmt.Println("10 is in the set")
}
```

## Structs

We can defined a struct inside or outside a function, we do it like this:

```go
type person struct {
    name string
    age int
    pet string
}
```

We can define variables of that type once the struct has been declared:

1. ``var`` declaration. It gets the zero value for the ``person`` struct type,
   which sets the zero value for each field type.
   ```go
   var fred person
   ```
2. *Struct literal*. It also assigns the zero value to each field.
   ```go
   bob := person{}
   ```
   *Nonempty struct literal*, there are two styles to do this:
   
   -With a comma-separated list of values (which requires *every* field in the
   struct to be specified, and the values are assigned to the fields in the
   order they were declared):
   ```go
   julia := person{
       "Julia",
       40,
       "cat",
   }
   ```
   
   -With a style that looks like a map literal (we can specify the fields in
   any order and we do not need to fill-in every field):
   ```go
   beth := person{
       age: 30,
       name: "Beth",
   }
   ```

We access fields in a struct using the dotted notation:
```go
bob.name = "Bob"
fmt.Println(bob.name)
```

### Anonymous structs

We can declare a variable that implements a struct type without first giving
the struct type a name:

```go
var person struct {
    name string
    age int
    pet string
}

person.name = "Bob"
person.age = 50
person.pet = "dog"
```

Another way would be:
```go
pet := struct {
    name string
    kind string
}{
    name: "Fido",
    kind: "dog",
}
```

### Comparing and converting structs

Whether or not a struct is comparable depends on the struct's fields, if all
the fields are comparable types, then the struct is comparable, else it is not
(we can't compare structs of different types even if all the fields are the
same).

We can perform a type conversion from one struct type to another if the fields
of both structs have the same names, order and types.

Given these two structs:
```go
type firstPerson struct {
    name string
    age int
}

type secondPerson struct {
    name string
    age int
}
```
* We can use a type conversion to convert an instance of ``firstPerson`` to
  ``secondPerson`` and vice versa. 
* We can't use ``==``to compare an instance of ``firstPerson`` to
  ``secondPerson`` and vice versa, because they are different types.

Given an anonymous struct and a non-anonymous struct:
```go
type firstPerson struct {
    name string
    age int
}
var g struct {
    name string
    age int
}

f := firstPerson {
    name: "Bob",
    age: 50,
}

g = f
fmt.Println(f == g) // true
```

* We can compare them without type conversion if the fields of both structs
  have the same names, order and types.
* We can assign between named and anonymous struct types if the fields of both
  structs have the same names, order and types.

# Blocks, shadows, and control structures

1. [Blocks](#blocks)
   1. [Shadowing variables](#shadowing-variables)
2. [if](#if)
3. [for four ways](#for-four-ways)
4. [switch](#switch)
5. [Blank switches](#black-switches)

## Blocks

We have the *package* block, *file* block, *function* block, within a function
every set of braces defines another block and we also have the *universe*
block. The universe block hosts the *predeclared identifiers* that are used as
keywords, but in fact they're not keywords.

These are the 25 keywords of Go: 
```
break        default      func         interface    select
case         defer        go           map          struct
chan         else         goto         package      switch
const        fallthrough  if           range        type
continue     for          import       return       var
```

So, other important *identifiers*, such as ``int, string, true, false`` and
functions (``make``, ``close``) are defined in the *universe block*, which is
the block that contains all other blocks.

### Shadowing variables

We need to be careful of these cases:

```go
func main() {
    x := 10  // x is declared in the function block
    if x > 5 {
        fmt.Println(x) // 10
        x := 5         // this statement defines and shadows the previous x
        fmt.Println(x) // 5
    }
    fmt.Println(x) // 10
}
```

We would also shadow a variable using multiple assignments since ``:=`` only
reuses variables that are declared in the current block.

```go
x := 10
if x > 5 {
    x, y := 5, 20 // this shadows x again
}
```

## if

* General if-else:
  ```go
  n := rand.Intn(10) // don't use this as secure random number generator
  if n == 0 {
      fmt.Println(" is 0")
  } else if n > 5 {
      fmt.Println(" greater than 5")
  } else {
      fmt.Println (" less than 0 or less than 5, but not 0")
  }
  ```
  
* We can scope a variable to an if statement: 
  ```go
  if n := rand.Intn(10); n == 0 {
      // etc
  } else if n > 5 {
      // etc
  }
  // n is not defined out of the if scope
  ```
## for, four ways

Go only has the ``for`` keyword for looping, but it allows to use it in four
different ways:

1. *The complete C-style ``for``*:
   ```go
   for i := 0; i < 10; i++ {
       fmt.Println(i)
   }
   ```
   In this case is a *must* to use ``:=`` to initialise variables, we cannot
   use ``var``.
   
2. *The condition-only ``for``*. This is like a ``while`` statement in C or
   other languages: 
   ```go
   i := 1
   for i < 100 {
       fmt.Println(i)
       i = i * 2
   }
   ```

3. *The infinite ``for`` statement*:
   ```go
   for {
       // something what will be done until the infinity and beyond
   }
   ```
   We can use the ``break`` and ``continue`` statements to manipulate these
   endless loops:
   ```go
   i := 1
   for {
       fmt.Println(i)
       i += 1
       if i == 99 {
           continue
       }
       if i == 100 {
           break
       }
   }
   ```

4. *The ``for-range`` statement*
   ```go
   evenVals := []int{2, 4, 6, 8, 10}
   for i, v := range evenVals {
       fmt.Println(i, v)
   }
   ```
   We can leave out the index using ``_``:
   ```go
   for _, v := range evenVals {
       fmt.Println(v)
   }
   ```
   We can also get rid of the value, we don't need to put it in the range and
   that's it:
   ```go
   uniqueNames := map[string]bool{"Fred": true, "Raul": true, "Wilma": true}
   for k := range uniqueNames {
       fmt.Println(k)
   }
   ```

    * **->** When we are iterating over the runes of the string, we are in fact
    iterating over *the runes*, not the *bytes*. So if we have special characters
    we will still iterate one character at a  time.

    * **->** When we use ``for-range`` we are iterating *over a copy*. Thereby,
     modifying the value will not modify the source:
      ```go
      evenVals := []int{2, 4, 6, 8}
      for _, value := range evenVals {
          v *= 2 
      }
      fmt.Println(evenVals) // unmodified
      ```

## switch

We have *expression switch* statements and *type switch* statements in go
(TODO: later). We are discussing *expression switch* statements.

On ``switch`` statements:

- We can switch on any type that can be compared with ``==``, this is, all the
  built-in types except slices, maps, channels, functions and structs that
  contain fields of these types.

- We can have multiple lines inside a ``case`` or ``default`` clause, and
  they're all considered to be part of the same block. There is no need to put
  parenthesis.

- Any variables defined in a ``case`` clause's block are only visible within
  that block.
  ```go
  case 5:
      wordLen := len(word) // only visible within this case block
  ```

- There is no need for ``break`` statements in ``switch``, by default cases in
  ``switch`` statements *do not fall through*.

- We can put multiple matches in a ``case`` separated by comas:
  ```go
  case 1, 2, 3, 4:
  ```
- When we have an empty case *nothing happens*:
   ```go
  case 6, 7, 8, 9: // in the 6, 7, 8 and 9, nothing happens
  case 10:
      fmt.Println(word, "is a long word")
  ``` 

- Go has the ``fallthrough``keyword, which lets one case continue to the next one, but it is discouraged.
- If we have a ``switch`` statement inside a ``for`` loop, if we want to break
  out of the ``for`` loop, a label is required. Otherwise Go will assume that
  we want to break out of the ``case``.
  ```go
  loop: // this label is required
      for i := 0; i < 10; i++ {
        switch {
        case i%2 == 0:
            fmt.Println(i, "is even")
        case i%3 == 0:
            fmt.Println(i, "is divisible by 3 but not 2")
        case i%7 == 0:
            fmt.Println("exit the loop!")
            // break : this won't exit the loop unless the loop has a label!
            break loop // this breaks out of the loop
        default:
            fmt.Println("deafult")
        }
      }
  ```

## Blank switches

Go allows us to write a ``switch`` statement that doesn't specify the value
that we're comparing against, this is called a *blank switch*. These switches
allow us to use any boolean comparison for each ``case``.
```go
// this is a silly example
a := 20
switch {
case a == 2:
    // something
case a > 10:
    // something
}
```

A more interesting example:

```go
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
```

# Functions

1. [Declaring and calling functions](#declaring-and-calling-functions)

## Declaring and calling functions

```go
func div(numerator int, denominator int) int {
    if denominator == 0 {
        return 0
    }
    return numerator / denominator
}
```

* The types of the parameters are always needed. 
  If we have multiple input parameters of the same type, we might group them
  together like this:
   ```go
   func div(numerator, denominator int) int {
   ```
  
* If a function returns a value we *must* supply a return. If it returns
  nothing, a ``return`` statement is not needed.

* Go *doesn't* have named and optional input parameters but we can simulate
  them using ``structs``:
  ```go
  type MyFuncOpts struct {
      FirstName string
      LastName string
      Age int
  }

  func MyFunC(opts MyFuncOpts) {
      fmt.Println("MyFunC was called")
  }

  func main() {
      MyFunC(MyFuncOpts {
		  LastName: "Patel",
		  Age: 50,
      })
      MyFunC(MyFuncOpts {
          FirstName: "Joe",
          LastName: "Smith",
      })
  }
  ```

* Go supports *variadic parameters*. The variadic parameter *must* be the last
  or only parameter in the input parameter list, we indicate the variadic
  parameter with ``...`` (three dots) before the type:
  ```go
  func variadicFunction(a int, vals ...int) []int {
  . . .
  }
  // we would call this function like this:
  variadicFunction(3)
  variadicFunction(3, 2, 1)
  a := []int{3, 4}
  variadicFunction(1, a...) // note that we need '...' here
  variadicFunction(1, []int{1, 2, 3}...) // as well as here
  ```
  We can count the values of ``vals`` using ``len(vals)``, and we can access
  those values with a for-range: ``for _, v := range vals {``
  
  The variadic parameter is converted to a slice, so we can pass a slice to
  that parameter, but we *must* put ``...`` (three dots) after the variable or
  slice literal.

* Go allows for multiple return values:
  ```go
  fuc divAndRemainder(numerator int, denominator int) (int, int, error) {
      if denominator == 0 {
          return 0, 0, errors.New("cannot divide by zero")
      }
      return numerator / denominator, numerator % denominator, nil
  }
  ```
90
