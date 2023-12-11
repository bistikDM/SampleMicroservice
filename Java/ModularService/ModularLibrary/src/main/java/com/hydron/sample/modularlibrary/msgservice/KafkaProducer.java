package com.hydron.sample.modularlibrary.msgservice;

import org.springframework.kafka.core.KafkaTemplate;

public class KafkaProducer implements MsgServiceInterface {
    private final KafkaTemplate<String, Object> template;

    public KafkaProducer(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    @Override
    public void sendMessage(String topic, Object msg) {
        this.template.send(topic, msg);
    }
}
