package com.hydron.sample.springbootprototype;

import com.hydron.sample.springbootprototype.pojo.ExampleData;
import com.hydron.sample.springbootprototype.services.consumer.ConsumerInterface;
import com.hydron.sample.springbootprototype.services.producer.ProducerInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.Instant;
import java.util.Random;

@SpringBootApplication
public class SpringBootPrototypeApplication implements CommandLineRunner {
    private final ProducerInterface fooProducer;
    private final ProducerInterface barProducer;
    private final ConsumerInterface fooConsumer;
    private final ConsumerInterface barConsumer;
    private final String serviceName;
    private static ApplicationContext context;

    public SpringBootPrototypeApplication(
            @Qualifier("FooProducer") ProducerInterface fooProducer,
            @Qualifier("BarProducer") ProducerInterface barProducer,
            @Qualifier("FooConsumer") ConsumerInterface fooConsumer,
            @Qualifier("BarConsumer") ConsumerInterface barConsumer,
            @Value("${spring.application.name}") String serviceName
    ) {
        this.fooProducer = fooProducer;
        this.barProducer = barProducer;
        this.fooConsumer = fooConsumer;
        this.barConsumer = barConsumer;
        this.serviceName = serviceName;
    }

    public static void main(String[] args) {
        context = SpringApplication.run(SpringBootPrototypeApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Random random = new Random();

        try {
            while (true) {
                // We're "producing" data.
                Thread.sleep(random.nextLong(500L, 3000L));
                ExampleData foo = fooProducer.produceData();
                ExampleData bar = barProducer.produceData();
                System.out.printf("[%s] [%s] -- Data produced!%n", this.serviceName, Instant.now());

                // We're "consuming" data.
                Thread.sleep(random.nextLong(500L, 3000L));
                fooConsumer.consumeData(foo);
                barConsumer.consumeData(bar);
            }
        } catch (InterruptedException e) {
            Thread.currentThread()
                    .interrupt();
            SpringApplication.exit(context, () -> 0);
        }
    }
}
