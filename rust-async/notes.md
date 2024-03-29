# Asynchronous Programming in Rust

Using [Asynchronous Programming in
Rust](https://rust-lang.github.io/async-book/01_getting_started/01_chapter.html)
at this
[commit](https://github.com/rust-lang/async-book/commit/73cdf319ab07d7c7d4013568ce0d778370fae216).

# 1. Async/.await primer

`async` transforms a block of code into a state machine that implements a trait
called `Future`. Blocked `Future`s yield control of the thread, allowing other
`Future`s to run.

```rust
use futures::executor::block_on;

async fn hello_world() {
    println!("Hello world!");
}

fn main() {
    let future = hello_world();
    block_on(future);
}
```

The value returned from an `async fn` is a `Future`. `Future`s need to be run
in executors. There are different types of executors based on the behaviour
that we want from them, `block_on` simply blocks the current thread until the
provided future has completed.

Inside an `async fn` we can use `.await` to wait for the completion of another
type that implements the `Future` trait, such as the output of another `async
fn`. `.await` does not block the current thread, it asynchronously waits for
the future to complete.

`async` bodies and other futures are lazy: they do nothing until they're
run. The most common way to run a `Future` is to `.await` it.

# 3. Async/.await

We can use `async` in `async fn` or `async` blocks, they both return a value
that implements the `Future` trait.

```rust
async fn foo() -> u8 {
    5
}

fn bar() -> impl Future<Output = u8> {
    async {
        let x: u8 = foo().await;
        x + 5
    }
}
```

## async lifetimes

`async fn`s which take reference or other non-`'static` arguments return a
`Future` which is bounded by the lifetime of the arguments:

So this function:
```rust
async fn foo(x: &u8) -> u8 { *x }
```

is equivalent to:
```rust
async fn foo<'a>(x: &'a u8) -> impl Future<Output = u0 + 'a {
    async move { *x }
}
```

Which implies that the future returned from the async function must be
`.await`ed while the non-`'static` arguments are still valid. This may be an
issue when we are storing the future or sending it over to another task or
thread.

A workaround for transforming an async function with references as arguments
into a `'static' future is to bundle the arguments with the call to the async
function inside an async block:

```rust
fn bundle() -> impl Future<Output = u8> {
    async {
        let x = 5;
        borrow_x(&x).await
    }
}
```
