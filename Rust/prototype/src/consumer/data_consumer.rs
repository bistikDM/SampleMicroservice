use chrono::Utc;

use crate::consumer::consumer::Consumer;
use crate::structs::bar::Bar;
use crate::structs::example_data::ExampleData;
use crate::structs::foo::Foo;

pub struct DataConsumer {
    service_name: String,
}

impl Consumer for DataConsumer {
    fn consume_foo_data(&self, foo_data: Foo) -> () {
        println!("[{}] [{}] -- {}", self.service_name, Utc::now(), foo_data.get_message());
    }

    fn consume_bar_data(&self, bar_data: Bar) -> () {
        println!("[{}] [{}] -- {}", self.service_name, Utc::now(), bar_data.get_message());
    }
}

impl DataConsumer {
    pub fn new(service_name: &str) -> Self {
        return Self {
            service_name: String::from(service_name)
        };
    }
}
