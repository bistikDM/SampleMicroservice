use kafka::Error;
use kafka::error::Error as KafkaError;
use kafka::producer::{Producer, Record};

pub struct KafkaProducer {
    brokers: Vec<String>,
    producer: Producer,
}

impl KafkaProducer {
    pub fn new(brokers: Vec<String>) -> Result<Self, Error> {
        let producer: Result<Producer, KafkaError> = Producer::from_hosts(brokers.clone()).create();

        return match producer {
            Ok(producer) => {
                Ok(Self {
                    brokers,
                    producer,
                })
            }
            Err(error) => { Err(error) }
        };
    }

    pub fn produce_message<T>(&mut self, topic: &str, data: &String) -> () {
        let result = self.producer.send(&Record::from_value(topic, data.as_bytes()));

        match result {
            Ok(_) => { println!("Message sent to {}!", topic) }
            Err(_) => { eprintln!("Failed to send message to {}...", topic) }
        }
    }
}
