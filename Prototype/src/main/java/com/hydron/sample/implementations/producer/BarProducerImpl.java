package com.hydron.sample.implementations.producer;

import com.hydron.sample.pojo.Bar;
import com.hydron.sample.pojo.ExampleData;
import com.hydron.sample.services.producer.ProducerInterface;
import com.hydron.sample.utils.WordList;

import java.time.Instant;

public class BarProducerImpl implements ProducerInterface {
    private final WordList wordList;

    public BarProducerImpl(WordList wordList) {
        this.wordList = wordList;
    }

    @Override
    public ExampleData produceData() {
        return Bar.builder()
                .name(this.wordList.getWord())
                .identifier("Prototype")
                .timestamp(Instant.now())
                .build();
    }
}
