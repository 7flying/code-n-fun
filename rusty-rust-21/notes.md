# Rusty Rust'21

Using [The Rust Programming
Language](https://doc.rust-lang.org/book/title-page.html) book.

For reference: [The Rustonomicon](https://doc.rust-lang.org/nomicon/intro.html)

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

We can return multiple values using a tuple:

```rust
fn main() {
    let s1 = String::from("hello");
    let (s2, len) = calculate_length(s1);
    println!("Length of {} is {}", s2, len);
}

fn calculate_length(s: String) -> (String, usize) {
    let length = s.len();
    (s, len)
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
2. [References and borrowing](#references-and-borrowing)
3. [The slice type](#the-slice-type)

## What is ownership

Ownership is a feature of Rust to make memory safety guarantees without needing
a garbage collector.

Ownership rules:
1. Each value in Rust has a variable that's called its owner.
2. There can only be one owner at a time
3. When the owner goes out of scope, the value will be dropped.

* In Rust memory is automatically allocated and it is returned once the
  variable that owns it goes out of scope.
  
  The internal function that Rust calls to return memory is
  [`drop`](https://doc.rust-lang.org/std/ops/trait.Drop.html#tymethod.drop) 
  
  
* Handling memory when moving data.

  A `String` is made up of three parts: a pointer to the memory that holds the
  contents of the String, its length (how much memory in bytes the contents of
  the String is using) and its capacity (the total amount of memory, in bytes,
  that the allocator has has given to the String)
  
  So, when we do:
  ```rust
  let s1 = String::from("hello");
  let s2 = s1;
  ```
  we aren't copying the String, `s1` and `s2` point to the same data.
  
  So, when `s1` and `s2` go out of scope they will **both** try to free the
  same memory, this is a *double free* error, thereby after `let s2 = s1;`
  statement, Rust considers `s1` to be no longer valid. So if we do this:
  ```rust
  let s1 = String::from("hello");
  let s2 = s1;
  println!("{}", s1); // --> compiler error: borrow of moved value
  ```
  we will get a compiler error, since it is like a *shallow copy* (copies the
  pointer but not the contents) plus an invalidation of the previous pointer.
  

* Cloning data

  Rust has the `clone` method that allows us to make a *deep copy* of the
  data that it is on the heap.
  
  ```rust
  let s1 = String::from("hello");
  let s2 = s1.clone();
  println!("{}, {}", s1, s2);
  ```
   
   This makes sense for data stored in the heap, data types that have a known
   size at compile time are stored on the stack, obviously.

* Ownership and functions
  
  When we pass a variable on the heap to a function its ownership remains on
  the function, thereby it is invalidated and we cannot use it anymore:
  
  ```rust
  fn main() {
    let s = String::from("Hello");
    takes_ownership(s);
    println!("{}", s); // s is no longer valid here since
                       // its owner is now the function
    let x = 10;
    makes_copy(x);
    println!("{}", x); // this is OK since x in on the stack and
                       // a copy of it is made
  }

  fn takes_ownership(some_string: String) {
    println!("{}", some_string);
  }

  fn makes_copy(some_integer: i32) {
    println!("{}", some_integer);
  }
  ```
* Ownership and return values

  When we return a value the ownership is transferred to the caller.
  
  If we want to return the ownership of a moved value (passed as a parameter)
  we need to return it.
  
  ```rust
  fn main() {
      let s = String::from("hello");
      let s = takes_and_gives_back(s);
      println!("{}", s);
  }
  fn takes_and_gives_back(a_string: String) -> String {
      a_string
  }
  ```
  
So, in conclusion:
- Assigning a value to anther variable transfers ownership of a variable (in
  the heap).
- When the variable goes out of scope the value is cleaned by `drop` unless the
  data has been moved and thus, is now owned by another variable.

## References and borrowing

``&`` (ampersands) are *references* and allow us to refer to some value without
taking ownership of it.

```rust
fn main() {
    let s1 = String::from("hello");
    let len = calculate_length(&s1);
    println!("The length of {} is {}", s1, len);
}

fn calculate_length(s: &String) -> usize {
    s.len()
}
```

* The action of creating a reference is called *borrowing*.
* References are **immutable by default** if we want to modify a borrowed value
  we need to declare it mutable: `&mut`
  ```rust
  fn main() {
    let mut s = String::from("hello");
    change(&mut s);
    println!("{}", s);
  }

  fn change(s: &mut String) {
    s.push_str(" world!");
  }
  ```
* We can only have **one** mutable reference to a particular piece of data at a
  time. This is made to avoid data race errors.
* We cannot combine mutable and immutable references in the same scope.
  So:
  ```rust
  let mut s = String::from("hello");
  let r1 = &s;
  let r2 = &s;
  let r3 = &mut s; // this will cause a compile time error
  ```
  
  However, remember the ownership rules:
  ```rust
  let mut s = String::from("hello");
  let r1 = &s;
  let r2 = &s;
  println!("{}, {}", r1, r2); // here println! takes ownership of r1 and r2

  let r3 = &mut s; // this is OK since r1 and r2 have been dropped
  ```

## The slice type

Slices let us reference a contiguous sequence of elements in a collection.

A slice is a reference. 

```rust
let s = String::from("hello world");
let hello = &s[0..5];
let world = &s[6..11];
```

A string slice type is written as: `&str`

```rust
fn first_word(s: &String) -> &str {
    let bytes = s.as_bytes();
    for (i, &item) in bytes.iter().enumerate() {
        if item == b' ' {
            return &s[0..i];
        }
    }
    &s[..]
}
```

* String literals are slices:

  ```rust
  let s = "Hello";
  ```
  The type of `s` is ``&str``.

* `fn first_word(s: &str) -> &str` this signature allows us to pass Strings and
  string literals as its parameter. TODO: *deref coercions*
  
  We can convert a String to a slice: `&my_string[..]`

* We can also make slices of arrays:
  ```rust
  let a = [1, 2, 3, 4, 5];
  let slice = &a[1..3];
  ```
# 5. Structs

1. [Defining and instanciating structs](#defining-and-instanciating-structs)
2. [Method syntax](#method-syntax)

## Defining and instanciating structs


* We define a strut like this:
  ```rust
  struct User {
      active: bool,
      username: String,
      email: String,
      sign_in_count: u64,
  }
  ```
* We create an instance of the struct like this:
  ```rust
  let user1 = User {
        email: String::from("some.email@email.com"),
        username: String::from("someusername"),
        active: true,
        sign_in_count: 1,
    };
  ```
  
  We do not need to specify the fields in the same order, but we need to
  specify all the fields.

* To get a specific value from a struct we can use the dot notation.
* If we are creating a struct from the values of function parameters, we can
  avoid typing again the field if the field name is the same.

  This is called *field init syntax*:
  ```rust
  fn build_user(email: String, username: String) -> User {
      User {
        email: email,
        username: username,
        active: true,
        sign_in_count: 1,
      }
  }
  // This is equivalent to:
  fn build_user(email: String, username: String) -> User {
      User {
        email,
        username,
        active: true,
        sign_in_count: 1,
      }
  }
  ```
* If we are updating a struct from the values of another struct we can specify
  only the fields that need changes, the others will be copied.
  
  This is called *struct update syntax*:
  ```rust
  let user2 = User {
      active: user1.active,
      username: user1.username,
      email: String::from("anotheremail@email.com"),
      sign_in_count: user1.sign_in_count,
  };
  // This is equivalent to:
  let user2 = User {
      email: String::From("anotheremail@email.com"),
      ..user1
  };
  ```
  
  Note, that because we have a field that is a String, we cannot use
  `user1` anymore since it has been invalidated.

*  We can also create *tuple structs*, which do not name fields, only their
   types.
   ```rust
   struct Color(i32, i32, i32);
   struct Point(i32, i32, i32);
   let black = Color(0, 0, 0);
   let origin = Point(0, 0, 0);
   ```
   Tuple struct instances behave like tuples.

* We can also define structs that don't have fields. These are called
  *unit-like structs*, they behave similar to the unit type `()`.
  ```rust
  struct AlwaysEqual;
  let subject = AlwaysEqual;
  ```
## Method syntax

Methods are defined within the context of a struct (or a enum or a trait
object), and their first parameter is always `self`.

```rust
#[derive(Debug)]
struct Rectangle {
    width: u32,
    height: u32,
}

impl Rectangle {
    fn area(&self) -> u32 {
        self.width * self.height
    }
}
```

* To define the methods within the context of a struct we use an `impl` block.
* The `&self` that we are using is a shorthand for `self: &Self`.
  Methods must have a parameter named `self` of type `Self` for their first
  parameter.
  We have used `&self` because we want to borrow ownership, not take it. If we
  wanted to modify the value we would use `&mut self`.
* Each struct is allowed to have multiple `impl` blocks.
* We can define a method with the same name as one of the struct fields. If
  they return the value of the field, these methods are usually named
  *getters*.
  

* Rust does *automatic referencing and dereferencing*. When we call a method
  with object.something() Rust adds `&`, `&mut` or `*` so that the object
  matches the signature of the method.

* *Associated functions*: all functions defined within an `impl` block.

  We can define associated functions that don't take `self` as the first
  parameter (thereby they're not methods). These functions are often used as
  constructors that will return a new instance of the struct.
  ```rust
  impl Rectangle {
      fn square(size: u32) -> Rectangle {
        Rectangle {
            width: size,
            height: size,
        }
      }
  }
  ```

# 6. Enums and pattern matching

1. [Defining an enum](#defining-an-enum)
2. [The match control flow operator](#the-match-control-flow-operator)
3. [Concise control flow with if let](#concise-control-flow-with-if-let)


## Defining an enum

Enums allow us to define a type by enumerating all its possible variants.

* We define an enum like this:
  ```rust
  enum IpAddrKind {
      V4,
      V6,
  }
  ```
  
  We can create instances of each of the two variants of the enum like this:
  ```rust
  let four = IpAddrKind::V4;
  let six = IpAddrKind::V6;
  ```

* We can use enums in conjunction with structs:
  ```rust
  enum IpAddrKind {
    V4,
    V6,
  }

  struct IpAddr {
      kind: IpAddrKind,
      address: String,
  }

  let home = IpAddr {
        kind: IpAddrKind::V4,
        address: String::from("127.0.0.1"),
    };
  ```
  
  We can also put data directly into each enum variant:
  ```rust
  enum IpAddr {
      V4(String),
      V6(String),
  }
  
  let home = IpAddr::V4(127, 0, 0, 1);
  ```

* Each variant can have different types and amounts of associated data:
  ```rust
  enum IpAddr {
      V4(u8, u8, u8, u8),
      V6(String)
  }
  let home = IpAddr::V4(127, 0, 0, 1);
  let loopback = IpAddr::V6(String::from("::1"));
  ```

* We can define methods on enums using `impl`:
  ```rust
  enum Message {
      Quit,
      Move { x: i32, y: i32 },
      Write(String),
      ChangeColor(i32, i32, i32),
  }
  
  impl Message {
      fn call(&self) {
        // something here
      }
  }
  
  let m = Message::Write(String::from("hello"));
  m.call();
  ```
* `Option` is a enum defined by the standard library and it is used in the
  scenario when a value could be something or nothing. Since Rust doesn't have
  the `null` feature, we can use `Option<T>`:
  ```rust
  enum Option<T> {
      None,
      Some(T),
  }
  ```
  
  `Option<T>` is included in the prelude as well as its variants `Some` and
  `None`, moreover we can use them without the `Option::` prefix.
  
  ```rust
  let some_number = Some(5); // type: Option<i32>
  let some_string = Some("a string"); // type: Option<&str>
  let absent_number : Option<i32> = None; // we need to annotate the type
  ```
  
  `Option<T>` and that same `T` are in fact different types, for instance
  `Option<i32>` is not the same as `i32`. If we need to make operations with
  them we would need to convert `Option<T>` to that `T` (see
  [documentation](https://doc.rust-lang.org/std/option/enum.Option.html)).
  
  In general we want to have code that handles stuff only when we have a
  `Some<T>` value, and to do something else when we have a `None` value.
  See the `match` expression in the next section.


## The match control flow operator
  
`match` allows us to compare a value against a series of patterns and then
execute code based on which pattern matches. The patterns are evaluated in
order.

Since `match` is an expression, remember that it returns something.

```rust
enum Coin {
    Penny,
    Nickel,
    Dime,
    Quarter,
}

fn value_in_cents(coin: Coin) -> u8 {
    match coin {
        Coin::Penny => 1,
        Coin::Nickel => 5,
        Coin::Dime => 10,
        Coin::Quarter => 25,
    }
}
```

The expression next to the `match` keyword doesn't need to return a Boolean,
like `if` expression does.

* We can use the match expression to match `Option<T>`:
  ```rust
  fn plus_one(x: Option<i32>) -> Option<i32> {
      match x {
        None => None,
        Some(i) => Some(i + 1),
      }
  }
  let five = Some(5);
  let six = plus_one(five);
  let none = plus_one(None);
  ```

* Matches are exhaustive, we need to cover *every possible case* or we will get
  a compiler error.
* If we don't want to cover all the cases we have the `other` keyword which
  must appear last because (remember) the patterns are evaluated in order:
  ```rust
  let dice_roll = 9;
  match dice_roll {
      3 => some_function(),
      7 => some_other_function(),
      other => else_function(other),
  }
  ```
  Note that `other` takes the value, when we don't want to use the value in the
  catch-all pattern we must use `_`:
  ```rust
  let dice_roll = 9;
  match dice_roll {
      3 => some_function(),
      7 => some_other_function(),
      _ => else(),
  }
  ```
  
  If we want to express that nothing happens in the last case, we can use the
  unit value (empty tuple):
  ```rust
  let dice_roll = 9;
  match dice_roll {
      3 => some_function(),
      7 => some_other_function(),
      _ => (),
  }
  ```
## Concise control flow with if let

The `if let` syntax is used to handle values that match one pattern while
ignoring the rest:

```rust
let config_max = Some(3u8);
match config_max {
    Some(max) => println!("The max is configured to be {}", max),
    _ => (),
}

// becomes:

let config_max = Some(3u8);
if let Some(max) = config_max {
    println!("The max is configured to be {}", max);
}
```

`if let` takes a pattern (`Some(max)` in the example) and an expression
(`config_max`) separated by an equal sign. 

We can include an `else` in the `if let`, it is the code that would go on the
`_` case in the `match` expression:

```rust
let mut count = 0;
match coin {
    Coin::Quarter(state) => println!("State quarter from {:?}", state),
    _ => count += 1,
}

// becomes:

let mut count = 0;
if let Coin::Quarter(state) = coin {
    println!("State quarter from {:?}", state);
} else {
    count += 1;
}

```

# 7. Managing growing projects

* `pub` makes something public.

* We define a module with the `mod` keyword followed by the name of the
  module.
  
* `crate` is the root folder.
  
* `super` allows us to go to the parent module.

* When `pub` is used in structs we need to additionally add `pub` to each field
  that we want public too.

* When `pub` is used in an enum, all of its variants are then public.

* `use` allows us to bring a path into scope.
  
  When bringing structs, enums and other items it's idiomatic to specify the
  full path: `use std::collections::HashMap;`
  
  When we want to bring a function into scope we must bring the parent and use
  the function specifying its parent:
  
  ```rust
  use something::parent;
  ...
  parent:some_function();
  ```
* We can give new names to things that we have brought into scope with `use`
  using the `as` keyword:
  ```rust
  use std::fmt::Result;
  use std::io::Result as IoResult;
  ```
  
* We can re-export names with `pub use`.
  When we bring a name into scope it is private for this current scope, if we
  want to make it available for other scopes that bring our scope into scope we
  need to use `pub use`.

* We can group use lists together:
  ```rust
  use std::cmp::Ordering;
  use std::io;
  
  /// becomes:
  
  use std::{cmp::Ordering, io};
  ```
  
  And:
  ```rust
  use std::io;
  use std::io::Write;
  
  // becomes:
  
  use std::io::{self, Write};
  ```

* If we want to bring *all* the public items defined in a path into scope we
  use the star `*` (glob operator):
  ```rust
  use std::collections::*;
  ```

# 8. Common collections

Unlike the built-in array and tuple types, data of these collections is stored
on the heap: vector, string, hash map.

Reference about other collections
[here](https://doc.rust-lang.org/std/collections/index.html).

1. [Vectors](#vectors)
2. [Strings](#strings)
3. [Hash Maps](#hash-maps)

## Vectors

* We create a new vector with the `Vec::new` function or with the `vec!` macro:
  ```rust
  let v: Vec<i32> = Vect::new();
  ```
  In the case above type annotation is needed since we aren't adding any values
  and the compiler needs to know their type.
  
  ```rust
  let v = vect![1, 2, 3];
  ```
  When we use the macro the compiler can infer what type the vector has.
  
* To add items to a vector we use `push`.
  ```rust
  let mut v = Vec::new(),
  v.push(1);
  ```
  Here Rust infers `v`'s type so we don't need a type annotation.
  
* Dropping a vector drops its elements. When a vector goes out of scope its
  elements and the vector are deleted.
  
* Accessing elements.
  
  We can use the index syntax `[ ]` , which returns a reference:
  ```rust
  let v = vec![1, 2, 3, 4, 5];
  let third: &i32 = &v[2]; // type annotation here is optional
  ```
  We can use the `get` method, which returns an `Option<&T>`:
  ```rust
  let v = vec![1, 2, 3, 4, 5];
  match v.get(2) {
      Some(third) => println!("{}", third);
      None => println!("nothing there");
  }
  ```

* Iterating over the values.
  ```rust
  let v = vec![100, 2];
  for i in &v {
      // bla bla
  }
  ```
  Remember that if the variable is mutable, the reference must be mutable too:
  ```rust
  let mut v = vec![100, 3];
  for i in &mut v {
      *i += 1; // here we modify v
  }
  ```

* When we need to store different types inside a vector we need to use an enum
  to wrap them:
  ```rust
  enum SpreadsheetCell {
      Int(32),
      Float(f64),
      Text(String),
  }
  
  let row = vec![
      SpreadsheetCell::Int(3),
      SpreadsheetCell::Text(String::from("blue")),
      SpreadsheetCell::Float(10.02),
  ];
  ```

## Strings

Rust has the string slice `str` type (usually seen as borrowed ``&str``), and
in the Rust standard library we have the `String` type. Both are UTF-8
encoded. 

* Creating an empty `String`:
  ```rust
  let mut s = String::new();
  ```

* To load data into a `String` we use the `to_string` method:
  ```rust
  let data = "some data";
  let s = data.to_string();
  let s = "some_data".to_string();
  ```
  
  We also have ``String::from``:
  ```rust
  let s = String::from("Some data");
  ```

* We can push new content into a string with the `push_str` method:
  ```rust
  let mut s = String::from("foo");
  s.push_str("bar");
  ```
  We also have `push` which takes a single character (`push('a')`);

* To concatenate strings we have the `+` operator: (takes a String and a
  &String, thereby lhs is invalidated)
  ```rust
  let s1 = String::from("Hello ");
  let s2 = String::from("world!");
  let s3 = s1 + &s2; // s1 is invalidaded
  ```
   `+` is implemented using `fn add(self, s: &str) -> String {`, Rust converts
  the `&String` that we are passing (&s2) into a `&str`.
  
* We can also use the `format!` macro to concatenate Strings, which uses
  references, so we don't need to care about variables being invalidated.
  ```rust
  let s1 = String::from("tic");
  let s2 = String::from("tac");
  let s3 = String::from("toe");
  let s = format!("{}-{}-{}", s1, s2, s3);
  ```
* Rust's Strings do not support indexing (`[]`) since they are a wrapper of
  `Vec<u8>` and they're encoded in UTF-8 (this means that each 'character' does
  not take the same storage size).
  
* We can iterate strings as Unicode scalar values using the `chars` method or
  as raw bytes with the `bytes` method.
  
  To obtain grapheme clusters (aka something like letters) we need an external
  crate.
  
* String slicing is made over bytes.

## Hash Maps

The type `HashMap<K, V>` stores a mapping of keys of type `K` to values of type
`V`. 

* We create a hash map with `new`:
  ```rust
  use std::collections::HashMap;
  let mut scores = HashMap::new();
  ```
  
  Note that `HashMap` is not automatically included in the prelude, so we must
  import it.

* We add stuff with ``insert``:
  ```rust
  scores.insert(String::from("Blue"), 10);
  ```

* We can also construct a hash map using iterators and the `collect` method on
  a vector of tuples (key, value).
  ```rust
  let teams = vec![String::from("Blue"), String::from("Yellow")];
  let initial_scores = vec![10, 30];
  
  let mut scores: HashMap<_, _> = teams.into_iter().zip(initial_scores.into_iter()).collect();
  ```
  
  Since we are using `collect` we need to specify which target type we are
  collecting (`HashMap`), but we don't need to specify the types of the keys
  and values of the hash map since the compiler can infer them.

* Ownership in hash maps.
  For types that implement the `Copy` trait (`i32` and simple types) the values
  are copied into the hash map, for owned values like `String` the values will
  be moved and the hash map will become the owner.
  
  If we insert references to values, the values won't be moved, but we need to
  make sure that the values must be valid for at least as long as the hash map
  is valid. 

* Accessing values.

  We can get a value providing its key to the `get` method (takes a reference)
  and it will return an `Option<&V>`
  ```rust
  let team_name = String::from("Blue");
  let score = scores.get(&team_name);

  match score {
      Some(&value) => println!("Team {} has {}", &team_name, value),
      None => println!("Team {} not found", &team_name),
  }
  ```
  
  We can iterate over each key-value pair wit a `for` loop:
  ```rust
  for (key, value) in &scores {
      println!("{}: {}", key, value);
  }
  ```
  
* Overwriting a value.
  Simply `insert` into the same key.

* Insert if the key has no value.
  Hash maps have an special API called `Entry` that we can check:
  
  ```rust
  scores.entry(String::from("yellow")).or_insert(50);
  ```
  
  `on_insert` returns a mutable reference to the value of the `Entry` if the
  key exists, if not, it inserts the parameter.

* Update a value based on its old value.

  ```rust
  let mut map = HashMap::new();
  let text = "hello world";
  for word in text.split_whitespace() {
      let count = map.entry(word).or_insert(0);
      *count += 1;
  }
  ```
  Since the `or_insert` method returns a mutable reference we need to
  dereference it `*`.

# 9. Error handling

1. [Unrecoverable errors](#unrecoverable-errors)
2. [Recoverable errors](#recoverable-errors)
3. [To panic or not to panic](#to-panic-or-not-to-panic)

Rust groups errors into recoverable (handled by the type `Resut<T, E>`) and
unrecoverable errors (with the `panic!` macro).

## Unrecoverable errors

When a `panic!` is thrown Rust *unwinds* the stack (aka walks back it and
cleans the data from each function it encounters). This takes time. We can tell
Rust to abort without panicking (doesn't clean up the stack) by adding in the
appropriate Cargo  `[profile]`  `panic = 'abort'`.
``

* We can examine backtraces with: `RUST_BACKTRACE=! cargo run`.

## Recoverable errors

The `Result` enum is defined having two variants: `Ok` and `Err`, it is brought
into scope in the prelude.

It looks like this:
```rust
enum Result<T, E> {
    Ok(T),
    Err(E),
}
```
where T and E are generic type parameters.

```rust
let f = File::open("hello.txt");
let f = match f {
    Ok(file) => file,
    Err(error) => panic!("problem opening file: {:?}", error),
};
```

* We can match on different types of errors:
  ```rust
  use std::io::ErrorKind;
  //...
  Err(error) => match error.kind() {
      ErrorKind::NotFound => match File::create("hello.txt") {
          Ok(fc) => fc,
          Err(e) => panic!(" error creating the file {:?}", e),
      },
      other_error => {
          panic!(" problem opening the file: {:?}", other_error)
      },
  },
  //...
  ```
* Since matching all the options of a `Result` is is very verbose, we can use
  `unwrap` instead. Which is implemented like a `match` expression and will
  return the value inside the `Ok` or call to `panic!`:
  ```rust
  let f = File::open("hello.txt").unwrap();
  ```
  
  We also have `expect`, which allows us to choose the `panic!` error message:
  ```rust
  let f = File::open("hello.txt").expect("shit happened");
  ```
  
* We can propagate errors by returning it:
  ```rust
  use std::fs::File;
  use std::io::{self, Read};

  fn read_username_from_file() -> Result<String, io::Error> {
      let f = File::open("hello.txt");
      let mut f = match f {
          Ok(file) => file,
          Err(e) => return Err(e),
      };
      let mut s = String::new();
      match f.read_to_string(&mut s) {
        Ok(_) => Ok(s),
        Err(e) => Err(e),
      }
  }
  ```
  
  And there is another shortcut for doing this using the `?` operator:
  ```rust
  use std::fs::File;
  use std::io;
  use std::io::Read;
  
  fn read_username_from_file() -> Result<String, io::Error> {
    let mut f = File::open("hello.txt")?;
    let mut s = String::new();
    f.read_to_string(&mut s)?;
    Ok(s)
  }
  ```
  
  We can also call on the `?`:
  ```rust
  fn read_username_from_file() -> Result<String, io::Error> {
      let mut s = String::new();
      File::open("hello.txt")?.read_to_string(&mut s)?;
      Ok(s)
  }
  ```
  
  We can also use `?` with `Option<T>`, if the value is `None` it will be
  returned early from the function at that point, if the value is `Some` it
  will be the resulting value of the expression and the execution will continue.
  ```rust
  fn last_char_of_first_line(text_ &str) -> Option<char> {
      text.lines().next()?.chars().last()
  }
  ```
  
* In `main` functions we usually return `()`, but we can also return
  `Result<(), E>`:
  ```rust
  use std::error::Error;
  use std::fs::File;
  fn main() -> Result<(), Box<dyn Error>> {
      let f = File::open("hello.txt")?;
      Ok(())
  }
  ```
  
# 10. Generic types, traits and lifetimes

1. [Generic data types](#generic-data-types)
2. [Traits](#traits)
3. [Lifetimes](#lifetimes)

## Generic data types

We can use them in functions, structs, enums, method definitions
Generics are implement in such a way that code using generic types is as fast
as code using concrete types. Rust performs *monomorphization* during compile
time to turn generic code into specific code by filling in the concrete types.


Structs:
```rust
struct Point<T, U> {
    x: T,
    y: U,
}
```

Enums:
```rust
enum Option<T> {
    Some(T),
    None,
}
```

```rust
enum Result<T, E> {
    Ok(T),
    Err(E),
}
```

Method definitions:
```rust
struct Point<T> {
    x: T,
    y: T,
}

impl<T> Point<T> {
    fn x(&self) -> &T {
        &self.x
    }
}
```
In the above example, we could also implement methods just on  `impl
Point<f32>{` for instance, to specify that the methods will only be available
for floating point types.


```rust
fn largest_i32(list: &[i32]) -> i32 {
    let mut largest = list[0];

    for &item in list {
        if item > largest {
            largest = item;
        }
    }

    largest
}
```
Becomes:

```rust
fn largest<T: PartialOrd + Copy>(list: &[T]) -> T { .. }
```

## Traits

We use traits to define shared behaviour and use trait bounds to specify that a
generic type can be any type that has that certain behaviour.

A type's behaviour consists of the methods we call call on that type. Trait
definitions group method signatures together to define a set of behaviours.

* We define traits like this:
  ```rust
  pub trait Summary {
      fn summarize(&self) -> String;
  }
  ```

* Implementing a trait is similar to implementing methods using `impl` but we
  need to put the trait and who is going to implement it like this:
  `impl Trait for TypeThatImplementsTheTrait`.
  
  ```rust
  // once that the trait is declared...
  pub trait Summary {
      fn summarize(&self) -> String;
  }
  // we have a type where the trait needs to be implemented
  pub struct Something {
      // bla bla
  }
  // so we implement it using impl ... for ...
  impl Summary for Something {
      fn summarize(&self) -> String {
          // bla bla
      }
  }
  ```
  
  We can implement traits in another crates as long as if at least one of the
  trait or the type is local to our crate. We can't implement external traits
  on external types e.g. we can't implement the standard library's `Display`
  into `Vec<T>` within our crates because they are both external.
  
* Default implementation of traits.
  When we declare a trait instead of just providing the signature we can put
  some default implementation:
  ```rust
  pub trait Summary {
      fn summarize(&self) -> String {
          String::from("(default stuff)")
      }
  }
  ```
  
  In order to use this default implementation, we need to implement the trait
  with `impl Trait for TypeThatImplementsTheTrait { }`, but the block will be
  empty.
  
  Default implementations can call other methods in the same trait, even if
  those other methods don't have a default implementation:
  ```rust
  pub trait Summary {
      fn summarize(&self) -> String {
          String::from("(default stuff and call to other fun in trait {}", self.summarize_author())
      }
      
      fn summarize_author(&self) -> String;
  }
  ```
  
* Traits as parameters.
  To pass a trait as a parameter we use the `impl Trait` syntax:
  ```rust
  pub fn notify(item: &impl Summary) {
      // bla bla
  }
  ```
  
  This is a shorter form of the *trait bound* which looks like this:
  ```rust
  pub fn notify<T: Summary>(item: &T) {
      // bla bla
  }
  ```
  
  When we have more than one parameter it looks like this:
  ```rust
  pub fn notify(item1: &impl Summary, item2: &impl Summary) { .. }
  
  pub fn notify<T: Summary>(item1: &T, item2: &T) { .. }
  ```
  
  When we have multiple traits we need to use the `+` syntax:
  ```rust
  pub fn notify(item: &imp(Summary + Display)) { .. }
  
  pun fn notify<T: Summary + Display>(item: &T) { .. }
  ```

* There is an alternate syntax for specifying trait bounds inside a `where`
  clause, this is used when there are a lot of traits:
  ```rust
  // without where syntax:
  fn some_function<T: Display + Clone, U: Clone + Debug>(t: &T, u: &u) -> i32 { .. }
  // this becomes:
  fn some_function<T, U>(t: &T, u: &U) -> i32
      where T: Display + Clone,
            U: Clone + Debug,
  {  .. }
  ```
  
* We can return types that implement traits using the `impl Trait` syntax:
  ```rust
  fn returns_summarizable() -> impl Summary {
      Tweet { .. }
  }
  ```
  however, we must only return the same type in all branches of the function,
  meaning that if we have `Tweet` and `NewsArticle`, which both implement the
  `Summary` trait, we could only return one or the other, not both depending on
  the branch that the program takes.
  
## Lifetimes


