use std::thread;
use std::sync::{Mutex, Arc};

struct Philosopher {
    name: String,
    // usize is the type you index vectors with
    // left and right are the two forks that the philosopher has
    left: usize,
    right: usize,
}

// impl: this block lets us define things on structs. We are defining an
// 'associated function' named 'new'
impl Philosopher {
    // 'new' takes an argument of type 'reference to string'. Returns an
    // instance of the Philosopher struct
    fn new(name: &str, left: usize, right: usize) -> Philosopher {
        // Creates a new Philosopher, takes the name reference and uses
        // 'to_string' to create a copy of the string '&str' points to.
        // Since Rust is an 'expression based' language, everything (almost)
        // is an expression that returns a value. This function will return
        // a new Philosopher since it is the last expression of this function.
        Philosopher {
            name: name.to_string(),
            left: left,
            right: right,
        }
    }
    // In Rust, methods take an explicit 'self' parameter, so eat() is a method
    // but 'new' is an associated function since it has no 'self'.
    fn eat(&self, table: &Table) {
        // The call to lock might fail, the error that could happen is that the
        // mutex is poisoned (when the thread panics while the lock is held)
        // _variable : is called this way because we are not planning on using
        // this variable. In this case we just want to acquire the lock.
        // If we didn't use the variable and declare it with without the
        // underscore, Rust will throw a warning.
        // The lock will be released when _left and _right go out of scope.
        let _left = table.forks[self.left].lock().unwrap();
        let _right = table.forks[self.right].lock().unwrap();
        
        println!("{} has started to eat!", self.name);

        thread::sleep_ms(1000);

        println!("{} is done eating!", self.name);
    }
}

// The Table struct has a vector of Mutex-es. Remember that with a mutex just
// one thread can access the contents at once.
struct Table {
    // We put an empty tuple (¿?)
    forks: Vec<Mutex<()>>,
}

fn main() {
    // Arc: 'atomic reference count'. We need this to share the Table across
    // multiple threads. When we share it, the reference count will go up,
    // then the thread ends it will go down.
    let table = Arc::new(Table { forks: vec![
        Mutex::new(()),
        Mutex::new(()),
        Mutex::new(()),
        Mutex::new(()),
        Mutex::new(()),
    ]});
    // philosophers is a Vec<T>, a growable array type
    let philosophers = vec![
        // we are in a circle: left - right, we make Gilles to be left handed
        Philosopher::new("Friedrich Nietzshe", 0, 1),
        Philosopher::new("Baruch Spinoza", 1, 2),
        Philosopher::new("Karl Marx", 2, 3),
        Philosopher::new("Michel Foucault", 3, 4),
        Philosopher::new("Gilles Deleuze", 0, 4),
    ];
    // We could also have used:
    // let p0 = Philosopher { name: "Some name".to_string() };

    // let handles: Vec<_>: is a vector of something, that something can be
    // figured by Rust. We are going to store here the handles to some threads.
    // philosophers.into_iter().map(|p| { : take the list of philosophers
    // and generate an iterator on it. Take the iterator and call 'map' on it.
    // Map is like Clojure, takes an element and a function and calls the
    // function for each element. In Rust the function needs to be a closure.
    // TODO: why the |p|? Investigate map.
    let handles: Vec<_> = philosophers.into_iter().map(|p| {
        // the table.clone() is what on Arc<T> turns the reference count up/down
        let table = table.clone();
        // thread::spawn: takes a closure as an argument and executes that
        // closure in a new thread. This closure needs the 'move' annotation
        // to indicate that the closure will take ownership of the values
        // it is capturing. Here the 'p' variable of the 'map' function
        // TODO: more examples with 'move', see when is and isn't needed.
        thread::spawn(move || {
            p.eat(&table);
        })
            // take the results of the 'map' calls and make a collection
            // ('collect()') of some kind with them.
            // This is why in 'let handles: Vec<_>' we needed to annotate the
            // return type. The elements of the vector are the return values
            // of the thread::spawn calls, this is, handles to threads.
    }).collect();

    for h in handles {
        // 'join' blocks execution until the thread has finished.
        // TODO: what happens with 'unwrap'?
        h.join().unwrap();
    }
    
    // We get a reference to each philosopher
    //    for p in &philosophers {
    //        p.eat();
   //     }
}
