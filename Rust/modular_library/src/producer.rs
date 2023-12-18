use kafka::error::Error as KafkaError;
use kafka::producer::{Producer, Record};
use serde_json;

pub struct KafkaProducer {
    brokers: Vec<String>,
    producer: Producer,
}

impl KafkaProducer {
    pub fn new(brokers: Vec<String>) -> Self {
        let producer: Result<Producer, KafkaError> = Producer::from_hosts(brokers.clone()).create();

        return Self {
            brokers,
            producer: producer.unwrap_or_default(),
        };
    }

    pub fn produce_message<T>(&mut self, topic: &str, data: &T) -> Result<(), KafkaError> {
        let msg = serde_json::to_string(data);

        return match msg {
            Ok(json) => { self.producer.send(&Record::from_value(topic, json)) }
            Err(error) => { Err(KafkaError::from(error)) }
        };
    }
}
