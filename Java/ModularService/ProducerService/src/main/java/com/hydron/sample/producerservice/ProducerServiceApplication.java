package com.hydron.sample.producerservice;

import com.hydron.sample.modularlibrary.msgservice.MsgServiceInterface;
import com.hydron.sample.modularlibrary.pojo.ExampleData;
import com.hydron.sample.producerservice.services.ProducerInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.Instant;
import java.util.Random;

@SpringBootApplication
public class ProducerServiceApplication implements CommandLineRunner {
    private final ProducerInterface fooProducer;
    private final ProducerInterface barProducer;
    private final MsgServiceInterface msgServiceInterface;
    private final String serviceName;
    private final String topic;
    private static ApplicationContext context;

    public ProducerServiceApplication(
            @Qualifier("FooProducer") ProducerInterface fooProducer,
            @Qualifier("BarProducer") ProducerInterface barProducer,
            MsgServiceInterface msgServiceInterface,
            @Value("${spring.application.name}") String serviceName,
            @Value("${spring.kafka.topics.example}") String topic) {
        this.fooProducer = fooProducer;
        this.barProducer = barProducer;
        this.msgServiceInterface = msgServiceInterface;
        this.serviceName = serviceName;
        this.topic = topic;
    }

    public static void main(String[] args) {
        context = SpringApplication.run(ProducerServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();
        try {
            while (true) {
                // We're "producing" data.
                Thread.sleep(random.nextLong(500L, 3000L));
                ExampleData foo = fooProducer.produceData();
                ExampleData bar = barProducer.produceData();
                System.out.printf("[%s] [%s] -- Data produced!%n", this.serviceName, Instant.now());

                // Sending the data.
                this.msgServiceInterface.sendMessage(this.topic, foo);
                this.msgServiceInterface.sendMessage(this.topic, bar);
            }
        } catch (InterruptedException e) {
            Thread.currentThread()
                    .interrupt();
            SpringApplication.exit(context, () -> 0);
        }
    }
}
