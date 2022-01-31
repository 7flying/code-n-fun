use std::ops::Deref;

fn test_1() {
    let x = 5;
    let y = &x;
    assert_eq!(5, x);
    assert_eq!(5, *y);
    assert_eq!(&x, y);
}

fn test_2() {
    let x = 5;
    let y = Box::new(x);
    assert_eq!(5, x);
    assert_eq!(5, *y);
}

fn test_3() {
    let x = 5;
    let y = MyBox::new(x);
    assert_eq!(5, x);
    assert_eq!(5, *y);
}

struct MyBox<T>(T);

impl<T> MyBox<T> {
    fn new(x: T) -> MyBox<T> {
        MyBox(x)
    }
}

impl<T> Deref for MyBox<T> {
    type Target = T; // this defines an associated type for the Deref trait

    fn deref(&self) -> &Self::Target {
        &self.0
    }
}

fn hello(name: &str) {
    println!("Hello {}", name);
}

fn main() {
    test_1();
    test_2();
    test_3();
    hello(&String::from("Rust"));
    let m = MyBox::new(String::from("Rust"));
    hello(&m);
}
