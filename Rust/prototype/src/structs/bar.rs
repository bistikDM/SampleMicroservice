use chrono::{DateTime, Utc};

use crate::structs::example_data::ExampleData;

pub struct Bar {
    pub name: String,
    pub identifier: String,
    pub timestamp: DateTime<Utc>,
}

impl ExampleData for Bar {
    fn get_message(&self) -> String {
        return format!("{} [{}] - Bar says \"Goodbye, {}!\"", self.identifier, self.timestamp, self.name);
    }
}

impl Bar {
    pub fn new(name: &str, identifier: &str, timestamp: DateTime<Utc>) -> Self {
        return Self {
            name: String::from(name),
            identifier: String::from(identifier),
            timestamp,
        };
    }
}
