fn main() {
    let width1 = 30;
    let height1 = 40;

    println!("The area is {}", area(width1, height1));

    let rect = (20, 30);
    println!("The area is {}", area2(rect));

    let rectangle = Rectangle {
        height: 30,
        width: 20,
    };
    println!("The area is {}", area3(&rectangle));
    println!("{:?}", rectangle);
    println!("{:#?}", rectangle);
    dbg!(&rectangle);
}

fn area(width: u32, height: u32) -> u32 {
    width * height
}

fn area2(dimensions: (u32, u32)) -> u32 {
    dimensions.0 * dimensions.1
}

#[derive(Debug)]
struct Rectangle {
    width: u32,
    height: u32
}

fn area3(rectangle: &Rectangle) -> u32 {
    rectangle.width * rectangle.height
}
