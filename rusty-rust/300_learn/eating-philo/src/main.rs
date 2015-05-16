use std::thread;

struct Philosopher {
    name: String,
}

// impl: this block lets us define things on structs. We are defining an
// 'associated function' named 'new'
impl Philosopher {
    // 'new' takes an argument of type 'reference to string'. Returns an
    // instance of the Philosopher struct
    fn new(name: &str) -> Philosopher {
        // Creates a new Philosopher, takes the name reference and uses
        // 'to_string' to create a copy of the string '&str' points to.
        // Since Rust is an 'expression based' language, everything (almost)
        // is an expression that returns a value. This function will return
        // a new Philosopher since it is the last expresion of this function.
        Philosopher {
            name: name.to_string(),
        }
    }
    // In Rust, methods take an explicit 'self' parameter, so eat() is a method
    // but 'new' is an asociated function since it has no 'self'.
    fn eat(&self) {
        println!("{} has started to eat!", self.name);

        thread::sleep_ms(1000);

        println!("{} is done eating!", self.name);
    }
}

fn main() {
    // philosophers is a Vec<T>, a growable array type
    let philosophers = vec![Philosopher::new("Friedrich Nietzshe"),
                            Philosopher::new("Baruch Spinoza"),
                            Philosopher::new("Karl Marx"),
                            Philosopher::new("Michel Foucault"),
                            Philosopher::new("Gilles Deleuze"),
                            ];
    // We could also have used:
    // let p0 = Philosopher { name: "Some name".to_string() };

    // let handles: Vec<_>: is a vector of something, that something can be
    // figured by Rust. We are going to store here the handles to some threads.
    // philosophers.into_iter().map(|p| { : take the list of philosophers
    // and generate an iterator on it. Take the iterator and call 'map' on it.
    // Map is like Clojure, takes an element and a function and calls the
    // function for each element. In Rust the functionneeds to be a closure.
    // TODO: why the |p|? Investigate map.
    let handles: Vec<_> = philosophers.into_iter().map(|p| {
        // thread::spawn: takes a closure as an argument and executes that
        // closure in a new thread. This closure needs the 'move' anotation
        // to indicate that the closure will take ownership of the values
        // it is capturing. Here the 'p' variable of the 'map' function
        // TODO: more examples with 'move', see when is and isn't needed.
        thread::spawn(move || {
            p.eat();
        })
            // take the results of the 'map' calls and make a collection
            // ('collect()') of some kind with them.
            // This is why in 'let handles: Vec<_>' we needed to anotate the
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
