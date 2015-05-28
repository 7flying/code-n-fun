// step_by is an unstable feature, to avoid a compile error add this attribute
// and switch to the nightly version
#![feature(step_by)]


fn main() {
    // A simple for loop can be translated to
    for x in 0..10 {
        println!("{}", x);
    }
    // the same thing but using 'foo.next()'
    let mut range = 0..10;
    loop {
        match range.next() {
            Some(x) => {
                println!("{}", x);
            },
            None => { break }
        }
    }
    // Iterate over the contents of a vector
    let nums = vec![1, 2, 3];
    for i in 0..nums.len() {
        println!(" We are at {}", nums[i]);
    }
    // But this is rubish, because it has an extra bounds checking when indexing
    // the vector; so use this instead:
    for num in &nums {
        println!(" We are at {}", num);
    }
    // However, num is a &i32 (reference to an i32); println does the
    // dereferencing, but we could have used this:
    for num in &nums {
        // *num: explicitly dereferences num
        println!(" We are at {}", *num);
    }
    // Why does &nums give a reference? Because if we wanted the data by itself
    // we would have to make a copy

    // There are:  - iterators: these give a sequence of values
    //             - iterator adapters: operate over a iterator, producing a new
    //              one with a different output sequence
    //             - consumers: operate on an iterator producing some values
    // -- Consumers --
    // 1- The most common one is 'collect()'
    //  let one_to_hundred = (1..101).collect(); -> this won't compile,
    // since Rust cannot make up its type
    let one_to_hundred = (1..101).collect::<Vec<i32>>();
    // sometimes with a hint is enough:
    let another_one_to_hundred = (1..101).collect::<Vec<_>>();
    // 2- There are others: find()
    // find: takes a closure, and works with a reference to each element
    // of an iterator
    let greater_than_forty_two = (0..100)
                                  .find(|x| *x > 42);
    match greater_than_forty_two {
        Some(_) => println!("We got some numbers!"),
        None => println!("No numbers found")
    }
    // 3- Fold: fold(base, |accumulator, element| ...)
    let sum = (1..4).fold(0, |sum, x| sum + x);
    println!("Sum: {}", sum);
    //  base is the initial value, | ...| ... is a closure, at every iteration
    // the closure is applied to 'element' and stored on 'accumulator'. The
    // first time 'element' has the 'base' value
    // -- Iterators --
    // Are something where we can use 'next()', see the examples above
    // -- Iterator adapters --
    // Take an iterator and produce a new one
    // 1. map
    (1..100).map(|x| println!("{}", x));
    // 2. take n (like Clojure!)
    for i in (1..).step_by(5).take(5) {
        println!("{}", i);
    }
    // 3. filter (again like Clj)
    // since it does not consume the elements that are being iterated over
    // in the closure we are passing a reference to each element
    for i in (1..100).filter(|&x| x % 2 == 0) {
        println!("{}", i);
    }
    // And of course the iterators, iterator adapters and stuff are chainable
    (1..1000)
        .filter(|&x| x % 2 == 0)
        .filter(|&x| x % 3 == 0)
        .take(5)
        .collect::<Vec<i32>>();
}
