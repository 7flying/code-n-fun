
struct User {
    active: bool,
    username: String,
    email: String,
    sign_in_count: u64,
}

fn main() {
    let user1 = User {
        email: String::from("some.email@email.com"),
        username: String::from("someusername"),
        active: true,
        sign_in_count: 1,
    };

    println!("{}", user1.active);
    println!("{}", user1.email);
    println!("{}", user1.email);
}
