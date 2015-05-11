extern crate rand;

// Use the io library from the standard (std) lib
// Rust imports some things to every program, this is the 'prelude'
// TODO see which things are imported in the prelude.
use std::io;
use std::cmp::Ordering;
use rand::Rng;

// Entry point for the program, fn declares a new function, () means that
// there aren't arguments, and since we didn't include a return type, by
// default it is assumed to be 'empty tuple' or ().
fn main() {
    println!("Guess the number!");
    let secret_number = rand::thread_rng().gen_range(1, 101);
    //println!("The secret number is: {}", secret_number);
    loop {
        println!("Please input your guess.");
        // let statement: creates variable bindings. In Rust variables are
        // IMMUTABLE by default.
        // let foo = 5;     -> immutable
        // let mut bar = 5; -> (mut) mutable
        // Let does not take a name on the left side, it takes a 'pattern'
        // TODO investigate what 'patterns' are
        // String::new() : String is a type, given by the std lib, it is UTF-8
        // encoded.
        // new() creates an empty String.
        let mut guess = String::new();
        // io::stdin() : we are using it this way because we used std::io earlier,
        // otherwise we would be using std::io::stdin()
        // read_line(&mut guess): we are passing to the function the reference
        // to the previously declared 'mut guess', since references are immutable
        // by default too, '&mut guess' must be placed instead of '&guess'.
        io::stdin().read_line(&mut guess)
            // read_line returns an io::Result, which has an 'ok()' method which
            // says 'we want to assume this value as ok'.
            .ok()
            // 'ok()' then returns a value with the 'expect()' method, it takes the
            // value it is called on.
            // If it isn't a successful one, 'panic!' will appear with the message
            // passed as a parameter.
            .expect("Failed to read line");

        let guess: u32 = match guess.trim().parse() {
            Ok(num) => num,
            Err(_) => continue,
        };
            
        // {} is a placeholder, for multiple values use multiple {} like
        // println!("Hello I am {} years old, I was {} years old", 22, 21);
        println!("You guessed: {}", guess);

        match guess.cmp(&secret_number) {
            Ordering::Less    => println!("Too small!"),
            Ordering::Greater => println!("To big!"),
            Ordering::Equal   => {
                println!("You win!!");
                break;
            }
        }
    }
}
