package com.hydron.sample.implementations.producer;

import com.hydron.sample.pojo.ExampleData;
import com.hydron.sample.pojo.Foo;
import com.hydron.sample.services.producer.ProducerInterface;
import com.hydron.sample.utils.WordList;

import java.time.Instant;

public class FooProducerImpl implements ProducerInterface {
    private final WordList wordList;
    private final String serviceName;

    public FooProducerImpl(WordList wordList, String serviceName) {
        this.wordList = wordList;
        this.serviceName = serviceName;
    }

    @Override
    public ExampleData produceData() {
        return Foo.builder()
                .name(this.wordList.getWord())
                .identifier(this.serviceName)
                .timestamp(Instant.now())
                .build();
    }
}
