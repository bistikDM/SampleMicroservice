use chrono::{DateTime, Utc};

use crate::structs::example_data::ExampleData;

pub struct Foo {
    pub name: String,
    pub identifier: String,
    pub timestamp: DateTime<Utc>,
}

impl ExampleData for Foo {
    fn get_message(&self) -> String {
        return format!("{} [{}] - Foo says \"Hello, {}!\"", self.identifier, self.timestamp, self.name);
    }
}

impl Foo {
    pub fn new(name: &str, identifier: &str, timestamp: DateTime<Utc>) -> Self {
        return Self {
            name: String::from(name),
            identifier: String::from(identifier),
            timestamp,
        };
    }
}
