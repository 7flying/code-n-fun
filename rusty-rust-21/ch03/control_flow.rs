fn main() {
    let mut counter = 0;
    let result = loop {
        counter += 1;
        if counter == 10 {
            break counter * 2;
        }
    };
    println!("The result is: {}", result);

    let a = [10, 20, 30, 40, 50];
    let mut index = 0;
    while index < 5 {
        println!(" at {} the value is {}", index, a[index]);
        index += 1;
    }

    for element in a {
        println!(" value is {}", element);
    }

    for number in 1..4 {
        println!("{}", number);
    }
    for number in (1..4).rev() {
        println!("{}", number);
    }
}

