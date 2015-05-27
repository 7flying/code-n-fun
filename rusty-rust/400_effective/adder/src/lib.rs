//! The `adder` crate provides functions that add numbers to other numbers.
//!
//! # Examples
//!
//! ```
//! assert_eq!(4, adder::add_two(2));
//! ```

/// This function adds two to its argument.
///
/// # Examples
///
/// ```
/// use adder::add_two;
///
/// assert_eq!(4, add_two(2));
/// ```
pub fn add_two(a: i32) -> i32 {
    a + 2
}

// Only compiles test code if we are running the tests
#[cfg(test)]
mod tests {
    // We are in an inner module, to use external functions we must include them
    use super::add_two;
    // If we had more tests, we could have used: use super::*;

    #[test]
    // with this attribute if a test fails, the test passes
    // #[should_panic(expected = "assertion failed")]
    fn it_works() {
        assert_eq!("hello", "hello");
    }

}

