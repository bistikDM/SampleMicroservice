use kafka::error::Error as KafkaError;
use kafka::producer::{Producer, Record};
use serde_json;

use crate::example_data::ExampleData;

pub struct KafkaProducer {
    brokers: Vec<String>,
    producer: Producer,
}

impl KafkaProducer {
    pub fn new(brokers: Vec<String>) -> Self {
        let producer: Producer = Producer::from_hosts(brokers.clone()).create().unwrap_or_default();
        return Self {
            brokers,
            producer,
        };
    }

    pub fn produce_message<'a, 'b>(&mut self, topic: &'a str, data: &'b dyn ExampleData) -> Result<(), KafkaError> {
        let msg = serde_json::to_string(data);

        return match msg {
            Ok(json) => { self.producer.send(&Record::from_value(topic, json.as_bytes())) }
            Err(error) => { Err(KafkaError::from(error)) }
        };
    }
}
