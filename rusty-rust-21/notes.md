# Rusty Rust'21

Using [The Rust Programming
Language](https://doc.rust-lang.org/book/title-page.html) book.

## Tools

* ``rustup``: the Rust toolchain installer. ``rustup update``.
* ``rustc``: the Rust compiler.
* ``cargo``: package manager. ``cargo new blabla``, ``cargo build``, ``cargo
  check``, ``cargo run``, ``cargo doc --open``.

# 3. Common Programming Concepts

1. [Variables and mutability](#variables-and-mutability)
2. [Data types](#data-types)
   1. [Scalar types](#scalar-types)
   2. [Compound types](#compound-types)
3. [Functions](#functions)
   1. [Statements and expressions](#statements-and-expressions)
   2. [Functions with return values](#functions-with-return-values)
4. [Control flow](#control-flow)

## Variables and mutability

* Variables are immutable only by default, we make them mutable with the ``mut``
keyword added in front of the variable name.
  ```rust
  let x = 5; // immutable
  let mut y = 6; // mutable
  ```
* Like immutable variables, constants are values bound to a name and are not
  allowed to change.
  
  We aren't allowed to use ``mut`` with constants. Constants are always
  immutable.

* We declare constants with the ``const`` keyword.
  ```rust
  const THREE_HOURS_IN_SECONDS: u32 = 60 * 60 * 3;
  ```
* We can declare a new variable name with the same name as a previous variable,
  this is called *shadowing*. To shadow a variable we need to use the ``let``
  keyword again re-declaring it.
  ```rust
  let x = 5;
  let x = x + 1;
  {
      let x = x * 2;
      println!("x in the inner scope is {}", x); // 12
  }
  println!("x in the outer scope is {}", x); // 6
  ```

* Shadowing is different from marking a variable as ``mut``, when we use
  ``let`` we can perform some transformations on the value, but we leave the
  variable immutable after those changes.
  
  When we shadow a variable we can also change its type since we are
  effectively creating a new variable.
  
  ```rust
  let spaces = "   ";
  let spaces = spaces.len();
  ```
  However, when we declare a variable as mutable we cannot change its type:
  ```rust
  let mut spaces = "   ";
  spaces = spaces.len(); // this yields a compile time error
  ```
  
## Data types

Rust is a statically typed language, it must know the types of all variables at
compile type. The compiler can usually infer the type that we want to use but
in the remaining cases we must ad a type annotation:

```rust
let guess: u32 = "33".parse().expect("not a number!");
```
### Scalar types

Scalar types represent a single value, Rust has four primary scalar types:
integers, floating-point numbers, Booleans and characters.

* Integers can be signed (``iSomething``) or unsigned (``uSomething``). From
  ``i8`` and ``u8`` to ``i128`` and ``u128`` respectively.
  
  We also have ``isize`` and ``usize``, that depend on the architecture and are
  64 or 32 bits.
  
  Signed numbers range from -(2^n-1) to 2^n-1 -1 (inclusive). Unsigned numbers
  range from 0 to 2^n-1.
  
  Signed numbers are stored using the two's complement representation.
  
  Number literals: decimal (we can use ``_`` as a visual aid), hexadecimal
  (``0xff``), octal (``0o77``), binary (``0b11100``, we also have ``_`` as a
  visual aid), byte (``b'A'``, u8 only).
  
  When an integer overflow occurs, on debug mode a panic is thrown, but on
  release Rust will perform *two's complement wrapping*. In the case of a
  ``u8``, 256 becomes 0, 257 becomes 1 and so on.
  
* Floating-Point types.

  We have ``f32`` and ``f64`` and ``f64`` is the default type.
  
  They are stored according to the IEEE-754 standard, ``f32`` has
  single-precision and ``f64`` double precision.
  
* Boolean type.

  They are one byte in size and they can have the ``true`` or ``false`` value.
  
  We can use them with explicit type annotation using the ``bool`` keyword.
  
* Character type.

  They are **four** bytes in size and represent an Unicode Scalar Value. They
  are specified with single quotes. ``let c = 'c';``
  
### Compound types

Rust has tuples and arrays.

* Tuples.

  A tuple is a general way of grouping together a number of values with a
  variety of types into a compound type.

  Tuples have fixed size, once declared they cannot grow or shrink in size.
  
  ```rust
  let tup: (i32, f64, u8) = (500, 6.4, 1);
  let tup2 = (500, 5.5, 1);
  ```
   We can access tuple elements using a period ``.`` followed by the index:
   ```rust
   let x = (500, 6.4, 1);
   let five_hundredd = x.0;
   ```
   
   We can also use pattern matching to *destructure* a tuple value:
   ```rust
   let tup = (500, 6.4, 1);
   let (x, y, x) = tup;
   println!("The value of y is {}", y);
   ```

   The tuple without any values ``()`` is a special type that has only one
   value, also written ``()``. The type is called ``unit type`` and the value
   is called the ``unit value``.

* Arrays.
  
  The are used to store collections of values of the same type. They have fixed
  length.
  ```rust
  let a = [1, 2, 3, 4];
  let a: [i32; 5] = [1, 2, 3, 4, 5];
  ```
   We can also initialize an array with the same value for each element like
  this: ``let a = [initial_value; length]``
  
   We can access array elements using indexing ``a[pos]``

## Functions

Rust uses ``snake_case`` as the conventional style for function and variable
names. 

```rust
fn main() {
    another_function(5, 6);
}

fn another_function(x: i32, y: i32) {
    println!("The value of x is {}", x);
    println!("y is {}", y);
}
```
### Statements and expressions


Function bodies contain statements and expressions.

- *Statements* are instructions that perform something and **do not** return a
value. They are finished with a semicolon ``;``.
- *Expressions* evaluate to a resulting value, they don't have a semicolon at
  the end.

  ```rust
  let y = {
      let x = 3; // statement
      x + 1 // expression, it will return a value
  }; // statement
  println!("The value of y is {}", y); // 4
  ```

### Functions with return values

Rust does not name return values, it only declares their type after an arrow
(``->``). The return value of the function is synonymous with the value of the
final expression in the block of the body of a function.

This function returns 5:

```rust
fn five() -> i32 {
    5
}
```

And we call it like this:

```rust
fn main() {
    let result = five();
    println!("The result is: {}", result);
}
```

## Control flow

* ``if`` expressions.
  ```rust
  let number = 3;
  if number < 5 {
      // bla bla
  } else if {
      // else if bla bla
  } else {
      // else bla bla
  }
  ```
  
  In Rust the condition in an `if` expression must be a `bool` otherwise we
  will get an error. For instance:
  
  ```rust
  let number = 3;
  if number {  // ---> this throws a compile time error
      // bla bla
  }
  ```
  We can use `if` expressions in the rhs of a `let` statement:
  ```rust
  let condition = true;
  let number = if condition { 5 } else { 6 };
  ```
  however, the types of the arms of the if must match since Rust needs to know
  at compile time the type that the variable will take as the expression evaluates.

* `loop` keyword.

   We can make endless loops with Rust:
   ```rust
   fn main() {
       loop {
           println!("forever young");
       }
   }
   ```
   And we can break away from them with the  `break` keyword.
   
   We can return values from loops if we put the value that we want returned
   after the `break` expression:
   ```rust
   let mut counter = 0;
   let result = loop {
       counter += 1;
       if counter == 10 {
           break counter * 2;
       }
   };
   println!("The result is: {}", result);
   ```
* The `while` construction.

  Runs the code in the body while the condition is true or we call `break`
  inside the loop.
  ```rust
  let mut number = 3;
  while number != 0 {
      number -= 1;
  }
  ```
  
  We can use it to loop over the elements of a collection, such an array:
  ```rust
  let a = [10, 20, 30, 40, 50];
  let mut index = 0;
  while index < 5 {
      println!(" at {} the value is {}", index, a[index]);
      index += 1;
  }
  ```

* `for` loop.
   ```rust
   let a = [10, 20, 30, 40, 50];
   for element in a {
       println!("{}", element);
   }
   ```
   
   We can use `Range` with a `for` loop 
   (X..Y), y is not inclusive but x is.
   
   ```rust
   for number in (1..4).rev() {
       println!("{}", number)
   }
   ```

# 4. Ownership

1. [What is Ownership?](#what-is-ownership)

## What is ownership

Ownership is a feature of Rust to make memory safety guarantees without needing
a garbage collector.

Ownership rules:
1. Each value in Rust has a variable that's called its owner.
2. There can only be one owner at a time
3. When the owner goes out of scope, the value will be dropped.

* Variable scope:
  
  
