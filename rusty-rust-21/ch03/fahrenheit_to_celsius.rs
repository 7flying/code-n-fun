use std::io;

fn main() {
    loop {
        println!(" Enter the temperature in ºF:");
        let mut fahr = String::new();
        io::stdin()
            .read_line(&mut fahr)
            .expect("Failed to read line");
        let fahr: f64 = match fahr.trim().parse() {
            Ok(num) => num,
            Err(_) => continue,
        };
        let celcius = fahrenheit_to_celcius(fahr);
        println!(" {} ºF -> {} ºC", fahr, celcius);
    }
}

fn fahrenheit_to_celcius(farh: f64) -> f64 {
    let mut result: f64 = farh - 32.0;
    result *= 5 as f64;
    result /= 9 as f64;
    result
}
