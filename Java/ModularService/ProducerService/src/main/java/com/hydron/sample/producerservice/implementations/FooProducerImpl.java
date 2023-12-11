package com.hydron.sample.producerservice.implementations;

import com.hydron.sample.modularlibrary.pojo.ExampleData;
import com.hydron.sample.modularlibrary.pojo.Foo;
import com.hydron.sample.producerservice.services.ProducerInterface;
import com.hydron.sample.producerservice.utils.WordList;

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
