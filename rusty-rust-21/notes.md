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
if let Coin::Quarter(state) == coin {
    println!("State quarter from {:?}", state);
} else {
    count += 1;
}

```
