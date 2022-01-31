struct CustomSmartPointer {
    data: String,
}

impl Drop for CustomSmartPointer {
    fn drop(&mut self) {
        println!("Dropping CustomSmartpointer with data '{}'", self.data);
    }
}

fn main() {
    let c = CustomSmartPointer {
        data: String::from("stuff"),
    };
    println!(" c created");
    let d = CustomSmartPointer {
        data: String::from("more stuff"),
    };
    // drop(c);
    println!(" d created");
}
