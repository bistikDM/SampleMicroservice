use std::collections::LinkedList;

use kafka::{Error as KafkaError, Error};
use kafka::client::FetchOffset;
use kafka::consumer::{Consumer, MessageSets};
use serde_json::Value;

pub struct KafkaConsumer {
    brokers: Vec<String>,
    consumer: Consumer,
    group_id: String,
    topic: String,
}

impl KafkaConsumer {
    pub fn new(brokers: Vec<String>, group_id: &str, topic: &str) -> Result<Self, Error> {
        let group: String = String::from(group_id);
        let consumer: Result<Consumer, KafkaError> = Consumer::from_hosts(brokers.clone())
            .with_group(group)
            .with_fallback_offset(FetchOffset::Latest)
            .with_topic(String::from(topic))
            .create();

        return match consumer {
            Ok(consumer) => {
                Ok(Self {
                    brokers,
                    consumer,
                    group_id: String::from(group_id),
                    topic: String::from(topic),
                })
            }
            Err(error) => { Err(error) }
        };
    }

    pub fn consume_message<T>(&mut self) -> LinkedList<T> {
        let poll_result: Result<MessageSets, KafkaError> = self.consumer.poll();
        return match poll_result {
            Ok(content) => {
                let mut result: LinkedList<T> = LinkedList::new();
                for message_set in content.iter() {
                    for msg in message_set.messages() {
                        result.push_back(serde_json::from_value::<T>(Value::from(msg.value)));
                    }
                    let _ = self.consumer.consume_messageset(message_set);
                }
                let _ = self.consumer.commit_consumed();

                result
            }
            Err(_error) => { LinkedList::new() }
        };
    }
}