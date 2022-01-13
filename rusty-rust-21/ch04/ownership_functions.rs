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
