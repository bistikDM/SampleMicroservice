pub trait ExampleData {
    fn new(name: &str, identifier: &str, timestamp: String) -> Self;
    fn get_message(&self) -> String;
}