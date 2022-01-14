fn main() {
    let s1 = String::from("Hello ");
    let s2 = String::from("world!");
    let s3 = s1 + &s2;
    //println!("{} + {} = {}", s1, s2, s3);// -> s1 has been invalidaded,
                                           // + takes 'self' aka String, and &str
                                           // and returns a String
                                           // (the compiler transforms &String to &str)
    println!("{}", s3);
    println!("{}", s3);

    for c in s3.chars() {
        println!("{}", c);
    }

    for b in s3.bytes() {
        println!("{}", b);
    }
}
