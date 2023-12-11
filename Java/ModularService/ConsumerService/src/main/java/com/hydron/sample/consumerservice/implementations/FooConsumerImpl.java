package com.hydron.sample.consumerservice.implementations;

import com.hydron.sample.consumerservice.services.ConsumerInterface;
import com.hydron.sample.modularlibrary.pojo.ExampleData;
import com.hydron.sample.modularlibrary.pojo.Foo;

import java.time.Instant;

public class FooConsumerImpl implements ConsumerInterface {
    private final String serviceName;
    private static final String ERROR_STRING = "FooConsumer received wrong data type!";

    public FooConsumerImpl(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public void consumeData(ExampleData data) {
        if (data instanceof Foo) {
            System.out.printf("[%s] [%s] -- %s%n", this.serviceName, Instant.now(), data.getMessage());
        } else {
            System.err.printf("[%s] [%s] -- %s%n", this.serviceName, Instant.now(), ERROR_STRING);
        }
    }
}
