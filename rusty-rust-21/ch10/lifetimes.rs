struct ImportantExcerpt<'a> {
    part: &'a str,
}

impl<'a> ImportantExcerpt<'a> {

    fn level(&self) -> i32 {
        3
    }
}

fn main() {
    let novel = String::from("A long time ago, in a land far, far away..");
    let first_sentence = novel.split(',').next().expect("Couldn't find a ',");
    let i = ImportantExcerpt {
        part: first_sentence,
    };

    println!("excerpt: {}", i.part);
}
