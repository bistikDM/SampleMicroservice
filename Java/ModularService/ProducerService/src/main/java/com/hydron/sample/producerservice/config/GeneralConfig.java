package com.hydron.sample.producerservice.config;

import com.hydron.sample.modularlibrary.config.KafkaConfig;
import com.hydron.sample.producerservice.implementations.BarProducerImpl;
import com.hydron.sample.producerservice.implementations.FooProducerImpl;
import com.hydron.sample.producerservice.services.ProducerInterface;
import com.hydron.sample.producerservice.utils.WordList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.IOException;

@Configuration
@Import(KafkaConfig.class)
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

    @Bean("FooProducer")
    public ProducerInterface getFooProducer(WordList wordList) {
        return new FooProducerImpl(wordList, this.serviceName);
    }

    @Bean("BarProducer")
    public ProducerInterface getBarProducer(WordList wordList) {
        return new BarProducerImpl(wordList, this.serviceName);
    }
}
