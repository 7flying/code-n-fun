use std::collections::HashMap;

fn main() {
    let teams = vec![String::from("Blue"), String::from("Red")];
    let ini_scores = vec![10, 30];

    let mut scores: HashMap<_, _> = teams.into_iter().zip(ini_scores.into_iter()).collect();

    for (key, value) in &scores {
        println!("{}: {}", key, value);
    }
}
