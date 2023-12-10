use std::{fs, io};
use std::path::Path;

use chrono::Utc;
use rand::Rng;

pub struct WordList {
    word_array: Vec<String>,
    random_generator: rand::rngs::ThreadRng,
}

impl WordList {
    pub fn new(service_name: &str) -> Result<Self, io::Error> {
        println!(
            "[{}] [{:?}] -- Generating word list...",
            service_name,
            Utc::now()
        );
        let word_array = Self::_load_word_list()?;
        println!(
            "[{}] [{:?}] -- Word list generated!",
            service_name,
            Utc::now()
        );

        return Ok(Self {
            word_array,
            random_generator: rand::thread_rng(),
        });
    }

    pub fn get_word(&mut self) -> &str {
        return &self.word_array[self.random_generator.gen_range(0..self.word_array.len())];
    }

    fn _load_word_list() -> Result<Vec<String>, io::Error> {
        let resource_path = Path::new(env!("CARGO_MANIFEST_DIR"))
            .join("src")
            .join("resources")
            .join("wordlist");
        let content = fs::read_to_string(resource_path)?;
        let word_array: Vec<String> = content.lines().map(String::from).collect();

        return Ok(word_array);
    }
}
