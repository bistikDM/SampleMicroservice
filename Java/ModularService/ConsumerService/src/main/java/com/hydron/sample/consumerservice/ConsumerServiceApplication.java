package com.hydron.sample.consumerservice;

import com.hydron.sample.modularlibrary.pojo.ExampleData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@SpringBootApplication
public class ConsumerServiceApplication implements CommandLineRunner {
    private final Map<Class<? extends ExampleData>, Consumer<ExampleData>> consumerMap;
    private final String serviceName;
    private static ApplicationContext context;

    public ConsumerServiceApplication(
            Map<Class<? extends ExampleData>, Consumer<ExampleData>> consumerMap,
            @Value("${spring.application.name}") String serviceName
    ) {
        this.consumerMap = consumerMap;
        this.serviceName = serviceName;
    }

    public static void main(String[] args) {
        context = SpringApplication.run(ConsumerServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.printf("%s application is starting...%n", this.serviceName);
    }

    @KafkaListener(topics = "${spring.kafka.topics.example}")
    public void listen(ExampleData msg) {
        Consumer<ExampleData> consumer = this.consumerMap.get(msg.getClass());
        if (Objects.isNull(consumer)) {
            System.out.printf("%s [%s] -- No handler for message type: %s%n.", this.serviceName, Instant.now(), msg.getClass());
            return;
        }
        consumer.accept(msg);
    }
}
