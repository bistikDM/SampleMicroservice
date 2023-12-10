package com.hydron.sample.implementations.consumer;

import com.hydron.sample.pojo.Bar;
import com.hydron.sample.pojo.ExampleData;
import com.hydron.sample.services.consumer.ConsumerInterface;

import java.time.Instant;

public class BarConsumerImpl implements ConsumerInterface {
    private final String serviceName;
    private static final String ERROR_STRING = "BarConsumer received wrong data type!";

    public BarConsumerImpl(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public void consumeData(ExampleData data) {
        if (data instanceof Bar) {
            System.out.printf("[%s] [%s] -- %s%n", this.serviceName, Instant.now(), data.getMessage());
        } else {
            System.err.printf("[%s] [%s] -- %s%n", this.serviceName, Instant.now(), ERROR_STRING);
        }
    }
}
