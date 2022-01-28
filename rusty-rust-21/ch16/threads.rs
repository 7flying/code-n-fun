use std::thread;
use std::time::Duration;

fn main() {
    // this doesn't guarantee that the for will be completed
    // thread::spawn(|| {
    //     for i in 1..10 {
    //         println!("[spawned-thread] hi number {}", i);
    //         thread::sleep(Duration::from_millis(1));
    //     }
    // });
    let handle = thread::spawn(|| {
        for i in 1..10 {
            println!("[spawned-thread] hi number {}", i);
            thread::sleep(Duration::from_millis(1));
        }
    });
    for i in 1..5 {
        println!("[main-thread] hi number {}", i);
        thread::sleep(Duration::from_millis(1));
    }
    handle.join().unwrap();

    let v = vec![1, 2, 3];
    let handle_2 = thread::spawn(move || {
        println!("vector: {:?}", v);
    });
    handle_2.join().unwrap();
}
