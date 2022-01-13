
fn main() {
    let s1 = String::from("hello");
    println!("{}", s1);

    let s2 = s1; // this invalidates s1
    //println!("{}", s1); // compiler error: borrow of moved value

    let s3 = String::from("hello");
    let s4 = s3.clone(); // this makes a deep copy
    println!("{}, {}", s3, s4);
}
