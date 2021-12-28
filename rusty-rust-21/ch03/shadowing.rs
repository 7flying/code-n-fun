fn main() {
    let x = 5;
    let x = x + 1;
    {
        let x = x * 2;
        println!("x in the inner scope is {}", x);
    }
    println!("x in the outer scope is {}", x);

    let spaces = "   ";
    let spaces = spaces.len();
    println!("{}", spaces);
    let mut spaces2 = "  ";
    //spaces2 = spaces2.len(); // compile time error
}
