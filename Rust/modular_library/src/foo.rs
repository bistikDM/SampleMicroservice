use serde::{Deserialize, Serialize};

use crate::example_data::ExampleData;

#[derive(Serialize, Deserialize)]
pub struct Foo {
    name: String,
    identifier: String,
    timestamp: String,
}

impl ExampleData for Foo {
    fn new(name: &str, identifier: &str, timestamp: String) -> Self {
        return Self {
            name: String::from(name),
            identifier: String::from(identifier),
            timestamp,
        };
    }

    fn get_message(&self) -> String {
        return format!("{} [{}] - Foo says \"Hello, {}!\"", self.identifier, self.timestamp, self.name);
    }
}
