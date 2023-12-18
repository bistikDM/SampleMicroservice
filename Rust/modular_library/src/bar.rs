use crate::example_data::ExampleData;

pub struct Bar {
    pub name: String,
    pub identifier: String,
    pub timestamp: String,
}

impl ExampleData for Bar {
    fn new(name: &str, identifier: &str, timestamp: String) -> Self {
        return Self {
            name: String::from(name),
            identifier: String::from(identifier),
            timestamp,
        };
    }

    fn get_message(&self) -> String {
        return format!("{} [{}] - Bar says \"Goodbye, {}!\"", self.identifier, self.timestamp, self.name);
    }
}
