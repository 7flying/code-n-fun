use std::io;


fn main() {
    loop {
        println!(" Which nth Fibonacci number do you want?");
        let mut input = String::new();
        io::stdin()
            .read_line(&mut input)
            .expect("Failed to read line");
        let input: u128 = match input.trim().parse() {
            Ok(num) => num,
            Err(_) => continue,
        };
        if input <= 0 {
            continue;
        }
        println!(" The {}th Fibonacci number is {}",
                 input, get_nth_fibonacci(input));
    }
}

fn get_nth_fibonacci(nth: u128) -> u128 {
    if nth == 1 {
        0
    } else {
        fibo(nth, 2, 0, 1)
    }
}

fn fibo(nth: u128, count: u128, previous: u128, current: u128) -> u128 {
    if count == nth {
        current
    } else {
        fibo(nth, count + 1, current, current + previous)
    }
}
