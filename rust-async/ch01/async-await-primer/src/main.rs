use futures::executor::block_on;

// async fn hello_world() {
//     println!("Hello world!");
// }

// fn main() {
//     let future = hello_world();
//     block_on(future);
// }


struct Song {
    title: String,
}

async fn learn_song(title: String) -> Song {
    println!("Learning song {}", title);
    Song {
        title: title,
    }
}

async fn sing_song(song: &Song) {
    println!("Singing {}", song.title);
}

async fn dance() {
    println!("Dancing!");
}

async fn learn_and_sing() {
    // wait until the song has been learning before signing it
    println!(" [learn_and_sing] Calling learn_song");
    let song = learn_song(String::from("An Interesting Song Title")).await;
    println!(" [learn_and_sing] Calling sign_song");
    sing_song(&song).await;
}

async fn async_main() {
    println!(" [async_main] Calling learn and sing");
    let f1 = learn_and_sing();
    println!(" [async_main] Calling dance");
    let f2 = dance();
    // join! is like .await but can wait for multiple futures concurrently
    println!(" [async_main] Calling join");
    futures::join!(f1, f2);
}

fn main() {
    println!(" [main] Calling block_on");
    block_on(async_main());
}
