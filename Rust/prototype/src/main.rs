use std::{env, thread};
use std::error::Error;
use std::time::Duration;

use chrono::Utc;
use rand::Rng;

use utils::word_list;

use crate::consumer::consumer::Consumer;
use crate::consumer::data_consumer::DataConsumer;
use crate::producer::data_producer::DataProducer;
use crate::producer::producer::Producer;

mod utils;
mod producer;
mod structs;
mod consumer;

fn main() -> Result<(), Box<dyn Error>> {
    let args: Vec<String> = env::args().collect();
    let service_name = if args.len() > 1 {
        &args[1]
    } else {
        "DefaultServiceName"
    };

    let mut random_generator = rand::thread_rng();
    let mut word_list = word_list::WordList::new(service_name)?;
    let data_producer = DataProducer::new(service_name);
    let data_consumer = DataConsumer::new(service_name);

    loop {
        let mut duration = random_generator.gen_range(0..3000);
        thread::sleep(Duration::from_millis(duration));

        // We're "producing" data.
        let foo = data_producer.produce_foo_data(&mut word_list);
        let bar = data_producer.produce_bar_data(&mut word_list);
        println!("[{}] [{}] -- Data produced!", service_name, Utc::now());

        // We're "consuming" data.
        duration = random_generator.gen_range(0..3000);
        thread::sleep(Duration::from_millis(duration));
        data_consumer.consume_foo_data(foo);
        data_consumer.consume_bar_data(bar);
    }

    return Ok(());
}
