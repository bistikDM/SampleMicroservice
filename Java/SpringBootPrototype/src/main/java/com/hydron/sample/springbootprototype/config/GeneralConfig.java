package com.hydron.sample.springbootprototype.config;

import com.hydron.sample.springbootprototype.implementations.consumer.BarConsumerImpl;
import com.hydron.sample.springbootprototype.implementations.consumer.FooConsumerImpl;
import com.hydron.sample.springbootprototype.implementations.producer.BarProducerImpl;
import com.hydron.sample.springbootprototype.implementations.producer.FooProducerImpl;
import com.hydron.sample.springbootprototype.pojo.Bar;
import com.hydron.sample.springbootprototype.pojo.ExampleData;
import com.hydron.sample.springbootprototype.pojo.Foo;
import com.hydron.sample.springbootprototype.services.consumer.ConsumerInterface;
import com.hydron.sample.springbootprototype.services.producer.ProducerInterface;
import com.hydron.sample.springbootprototype.utils.WordList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Configuration
public class GeneralConfig {
    @Value("${spring.application.name}")
    private String serviceName;

    @Bean
    public WordList getWordList(ApplicationContext context) {
        try {
            return new WordList(this.serviceName);
        } catch (IOException e) {
            SpringApplication.exit(context, () -> 1);
            return null;
        }
    }

    @Bean
    public Map<Class<? extends ExampleData>, Consumer<ExampleData>> getConsumerMap(
            @Qualifier("FooConsumer") ConsumerInterface ci1,
            @Qualifier("BarConsumer") ConsumerInterface ci2
    ) {
        Map<Class<? extends ExampleData>, Consumer<ExampleData>> consumerHashMap = new HashMap<>();
        consumerHashMap.put(Foo.class, ci1::consumeData);
        consumerHashMap.put(Bar.class, ci2::consumeData);

        return consumerHashMap;
    }

    @Bean("FooProducer")
    public ProducerInterface getFooProducer(WordList wordList) {
        return new FooProducerImpl(wordList, this.serviceName);
    }

    @Bean("BarProducer")
    public ProducerInterface getBarProducer(WordList wordList) {
        return new BarProducerImpl(wordList, this.serviceName);
    }

    @Bean("FooConsumer")
    public ConsumerInterface getFooConsumer() {
        return new FooConsumerImpl(this.serviceName);
    }

    @Bean("BarConsumer")
    public ConsumerInterface getBarConsumer() {
        return new BarConsumerImpl(this.serviceName);
    }
}
