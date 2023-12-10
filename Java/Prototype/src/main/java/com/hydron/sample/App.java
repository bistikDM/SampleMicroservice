package com.hydron.sample;

import com.hydron.sample.implementations.consumer.BarConsumerImpl;
import com.hydron.sample.implementations.consumer.FooConsumerImpl;
import com.hydron.sample.implementations.producer.BarProducerImpl;
import com.hydron.sample.implementations.producer.FooProducerImpl;
import com.hydron.sample.pojo.Bar;
import com.hydron.sample.pojo.ExampleData;
import com.hydron.sample.pojo.Foo;
import com.hydron.sample.services.consumer.ConsumerInterface;
import com.hydron.sample.services.producer.ProducerInterface;
import com.hydron.sample.utils.WordList;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws IOException {
        // Instantiate producers and consumers.
        WordList wordList = new WordList(args[0]);
        ProducerInterface fooProducer = new FooProducerImpl(wordList, args[0]);
        ProducerInterface barProducer = new BarProducerImpl(wordList, args[0]);

        ConsumerInterface fooConsumer = new FooConsumerImpl(args[0]);
        ConsumerInterface barConsumer = new BarConsumerImpl(args[0]);
        Map<Class<? extends ExampleData>, Consumer<ExampleData>> consumerHashMap = new HashMap<>();
        consumerHashMap.put(Foo.class, fooConsumer::consumeData);
        consumerHashMap.put(Bar.class, barConsumer::consumeData);

        Random random = new Random();

        try {
            while (true) {
                // We're "producing" data.
                Thread.sleep(random.nextLong(500L, 3000L));
                ExampleData foo = fooProducer.produceData();
                ExampleData bar = barProducer.produceData();
                System.out.printf("[%s] [%s] -- Data produced!%n", args[0], Instant.now());

                // We're "consuming" data.
                Thread.sleep(random.nextLong(500L, 3000L));
                consumerHashMap.get(foo.getClass())
                        .accept(foo);
                consumerHashMap.get(bar.getClass())
                        .accept(bar);
            }
        } catch (InterruptedException e) {
            Thread.currentThread()
                    .interrupt();
            System.exit(0);
        }
    }
}
