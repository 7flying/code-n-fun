fn main() {
    let s1 = gives_ownership(); // gives_ownership moves its return value into s1

    let s2 = String::from("hello");
    let s3 = takes_and_gives_back(s2);
}

fn gives_ownership() -> String {
    let some_string = String::from("some");
    some_string // some_string is returned and moves out to the callee
}

fn takes_and_gives_back(a_string: String) -> String {
    a_string
}
