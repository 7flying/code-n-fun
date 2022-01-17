use std::fs::File;
use std::io::{self, Read, ErrorKind};

fn main() {
    // first example
    // let f = File::open("hello.txt");
    // let f = match f {
    //     Ok(file) => file,
    //     Err(error) => panic!("problem opening file: {:?}", error),
    // };

    let f = File::open("hello.txt");
    let f = match f {
        Ok(file) => file,
        Err(error) => match error.kind() {
            ErrorKind::NotFound => match File::create("hello.txt") {
                Ok(fc) => fc,
                Err(e) => panic!(" error creating the file {:?}", e),
            },
            other_error => {
                panic!(" problem opening the file: {:?}", other_error)
            },
        },
    };
}

fn read_username_from_file() -> Result<String, io::Error> {
    let mut f = File::open("hello.txt")?;
    let mut s = String::new();
    f.read_to_string(&mut s)?;
    Ok(s)
}
