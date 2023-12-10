package com.hydron.sample.springbootprototype;

import com.hydron.sample.springbootprototype.pojo.ExampleData;
import com.hydron.sample.springbootprototype.services.producer.ProducerInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

@SpringBootApplication
public class SpringBootPrototypeApplication implements CommandLineRunner {
    private final ProducerInterface fooProducer;
    private final ProducerInterface barProducer;
    private final Map<Class<? extends ExampleData>, Consumer<ExampleData>> consumerMap;
    private final String serviceName;
    private static ApplicationContext context;

    public SpringBootPrototypeApplication(
            @Qualifier("FooProducer") ProducerInterface fooProducer,
            @Qualifier("BarProducer") ProducerInterface barProducer,
            Map<Class<? extends ExampleData>, Consumer<ExampleData>> consumerMap,
            @Value("${spring.application.name}") String serviceName
    ) {
        this.fooProducer = fooProducer;
        this.barProducer = barProducer;
        this.consumerMap = consumerMap;
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
                this.consumerMap.get(foo.getClass())
                        .accept(foo);
                this.consumerMap.get(bar.getClass())
                        .accept(bar);
            }
        } catch (InterruptedException e) {
            Thread.currentThread()
                    .interrupt();
            SpringApplication.exit(context, () -> 0);
        }
    }
}
