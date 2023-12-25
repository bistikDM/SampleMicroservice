package com.hydron.sample.services.consumer;

import com.hydron.sample.pojo.ExampleData;

/**
 * An interface meant for any consumer type classes to implement so that the logic can be abstracted.
 */
public interface ConsumerInterface {
    /**
     * Consumes the data and perform a task.
     *
     * @param data The data to consume.
     */
    void consumeData(ExampleData data);
}
