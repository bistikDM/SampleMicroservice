use chrono::Utc;

use crate::producer::producer::Producer;
use crate::structs::bar::Bar;
use crate::structs::foo::Foo;
use crate::utils::word_list::WordList;

pub struct DataProducer {
    service_name: String,
}

impl Producer for DataProducer {
    fn produce_foo_data(&self, word_list: &mut WordList) -> Foo {
        return Foo::new(word_list.get_word(), self.service_name.as_str(), Utc::now());
    }

    fn produce_bar_data(&self, word_list: &mut WordList) -> Bar {
        return Bar::new(word_list.get_word(), self.service_name.as_str(), Utc::now());
    }
}

impl DataProducer {
    pub fn new(service_name: &str) -> Self {
        return Self {
            service_name: String::from(service_name)
        };
    }
}
