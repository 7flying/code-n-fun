
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

### var VS :=

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
   
**-->** When initializing a variable to its zero value use ``var x int``
**-->** When assigning an untyped constant or a literal to a variable and the
default type is not the type that we want, use the long ``var`` declaration
form e.g. ``var x byte = 20``.
     
### Using const

``const`` is used to declare a value immutable. They can only hold values that
the compiler can figure out at compile time.

They can be: numeric literals, ``true`` and ``false``, strings, runes, built-in
functions ``complex``, ``real``, ``imag``, ``len`` and ``cap``, expressions
that consist of operators and the preceding values (``const x = 20 * 10``).

**->** Go doesn't have a way to specify that a value calculated at runtime is
immutable.

### Typed and untyped constants

31
