use std::collections::HashMap;

fn main() {
    let teams = vec![String::from("Blue"), String::from("Red")];
    let ini_scores = vec![10, 30];

    let mut scores: HashMap<_, _> = teams.into_iter().zip(ini_scores.into_iter()).collect();

    for (key, value) in &scores {
        println!("{}: {}", key, value);
    }
    let team_name = String::from("Blue");
    let score = scores.get(&team_name);

    match score {
        Some(&value) => println!("Team {} has {}", &team_name, value),
        None => println!("Team {} not found", &team_name),
    }

    // ownership stuff
    let field_name = String::from("fav colour");
    let field_value = String::from("blue");

    let mut map = HashMap::new();
    map.insert(field_name, field_value);
    // println!("{} - {}", field_name, field_value); // compiler error, hash maps move values

    for (key, value) in &map {
        println!("{}: {}", key, value);
    }
}
