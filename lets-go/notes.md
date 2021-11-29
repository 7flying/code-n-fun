
## Links

* [Effective Go](https://go.dev/doc/effective_go)
* [Code Review Comments](https://github.com/golang/go/wiki/CodeReviewComments)

## Tools

* Code format checker: ``go fmt``, also use ``goimports -l -w``
* Linter: ``golint ./...``
* Code checker: ``go vet ./...``

## The semicolon insertion rule

The lexer inserts a semicolon after these tokens: identifiers (``int``), basic
literals (numbers or string constants), break, continue, fallthrough, return,
++, --, ), (, }.

# Primitive types and declarations

1. [Built-in types](#built-in-types)
1.1. [Literals](#literals)
1.2. [Booleans](#booleans)
1.3. [Numeric types](#numeric-types)
1.4. [Strings and Runes](#strings-and-runes)
1.5. [Explicit type conversion](#explicit-type-conversion)
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
2.1. [len](#len)
2.2. [append](#append)
2.3. [Capacity](#capacity)
2.4. [make](#make)

## Arrays

Different ways to declare an array:

1. ``var x[3]int``: array of three ``int``s, they're initialized to the zero
   value for an ``int`` (zero)
2. *Array literal*: ``var x = [3]int{10, 20, 30}``. This specifies the initial
   values of the array.
   
   In this case we can skip specifying the number of elements: ``var x=
   [...]int{10, 20, 30}``
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

1. *Slice literal*: ``var x = []int{10, 20, 30}``. Note that there are no ``[...]`` inside the
   brackets, if there were it would be an array instead of a slice.

   As in arrays, we can also specify the indexes in which the values are not
   the zero value: ``var x = []int{1, 5: 4, 6, 10: 100, 15}`` The slice has 12
   ``int``s with the following values: ``[1, 0, 0, 0, 0, 4, 6, 0, 0, 0, 100,
   15]``
2. We can declare a slice without using a literal: ``var x []int``.
   Since no values are assigned, ``x`` is assigned the zero value for a slice,
   which was ``nil``.
3. We can also simulate multidimensional slices: ``var x[][]int``.

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

39
