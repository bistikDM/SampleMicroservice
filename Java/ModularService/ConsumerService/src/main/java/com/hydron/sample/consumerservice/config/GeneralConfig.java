package com.hydron.sample.consumerservice.config;


import com.hydron.sample.consumerservice.implementations.BarConsumerImpl;
import com.hydron.sample.consumerservice.implementations.FooConsumerImpl;
import com.hydron.sample.consumerservice.services.ConsumerInterface;
import com.hydron.sample.modularlibrary.config.KafkaConfig;
import com.hydron.sample.modularlibrary.pojo.Bar;
import com.hydron.sample.modularlibrary.pojo.ExampleData;
import com.hydron.sample.modularlibrary.pojo.Foo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Configuration
@Import(KafkaConfig.class)
public class GeneralConfig {
    @Value("${spring.application.name}")
    private String serviceName;

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

    @Bean("FooConsumer")
    public ConsumerInterface getFooConsumer() {
        return new FooConsumerImpl(this.serviceName);
    }

    @Bean("BarConsumer")
    public ConsumerInterface getBarConsumer() {
        return new BarConsumerImpl(this.serviceName);
    }
}
