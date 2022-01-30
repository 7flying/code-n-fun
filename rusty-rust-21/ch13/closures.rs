use std::thread;
use std::time::Duration;


fn simulated_expensive_calculation(intensity: u32) -> u32 {
    println!("calculating..");
    thread::sleep(Duration::from_secs(2));
    intensity
}    

fn generate_workout(intensity: u32, random_numer: u32) {
    let expensive_result = simulated_expensive_calculation(intensity);
    if intensity < 25 {
        println!("{} pushups", expensive_result);
    } else {
        if random_numer == 3 {
            println!("take a break!");
        } else {
            println!("run for {} minutes", expensive_result);
        }        
    }
}

fn generate_workout_2(intensity: u32, random_numer: u32) {
    let expensive_closure = |num| {
        println!("calculating..");
        thread::sleep(Duration::from_secs(2));
        num
    };
    let expensive_closure_2 = |num: u32| -> u32 {
        println!("calculating..");
        thread::sleep(Duration::from_secs(2));
        num
    };
    if intensity < 25 {
        println!("{} pushups", expensive_closure(intensity));
    } else {
        if random_numer == 3 {
            println!("take a break");
        } else {
            println!("run for {} minutes", expensive_closure_2(intensity));
        }
    }
}

struct Cacher<T>
where
    T: Fn(u32) -> u32,
{
    calculation: T,
    value: Option<u32>,
}

impl<T> Cacher<T>
where
    T: Fn(u32) -> u32,
{
    fn new(calculation: T) -> Cacher<T> {
        Cacher {
            calculation,
            value: None,
        }
    }

    fn value(&mut self, arg: u32) -> u32 {
        match self.value {
            Some(v) => v,
            None => {
                let v = (self.calculation)(arg);
                self.value = Some(v);
                v
            }
        }
    }
}


fn main() {
    let simulated_value = 10;
    let simualted_number = 8;
    generate_workout(simulated_value, simualted_number);
    generate_workout_2(simulated_value, simualted_number);

    let mut c = Cacher::new(|num| {
        num + 1 
    });
    let v = c.value(3);
    println!("{}", v);


    let x = 4;
    let equal_to_x = |z| z == x;
    let y = 4;
    assert!(equal_to_x(y));
}
