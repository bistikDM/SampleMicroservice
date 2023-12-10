use std::env;
use std::error::Error;

use chrono::Utc;

use utils::word_list;

use crate::structs::bar::Bar;
use crate::structs::example_data::ExampleData;
use crate::structs::foo::Foo;

mod utils;
mod traits;
mod structs;

fn main() -> Result<(), Box<dyn Error>> {
    let args: Vec<String> = env::args().collect();
    let service_name = if args.len() > 1 {
        &args[1]
    } else {
        "DefaultServiceName"
    };

    let mut word_list = word_list::WordList::new(service_name)?;

    println!("{}", word_list.get_word());

    let foo = Foo::new("fooName", "fooIdentifier", Utc::now());
    let bar = Bar::new("barName", "barIdentifier", Utc::now());
    println!("{}", foo.get_message());
    println!("{}", bar.get_message());

    return Ok(());
}
