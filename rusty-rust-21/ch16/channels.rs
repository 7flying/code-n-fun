use std::sync::mpsc;
use std::thread;

fn main() {
    let (tx, rx) = mpsc::channel();
    thread::spawn(move || {
        let val = String::from("hi");
        tx.send(val).unwrap();
    });

     // recv blocks the main thread an waits until a value is sent down the channel
    let received = rx.recv().unwrap();
    println!("Got: {}", received);
}
