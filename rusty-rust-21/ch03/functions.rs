fn main() {
    let y = {
        let x = 3;
        x + 1
    };
    println!("The value of y is: {}", y); // 4

    let result = five();
    println!("The result is: {}", result);
}

fn five() -> i32 {
    5
}
