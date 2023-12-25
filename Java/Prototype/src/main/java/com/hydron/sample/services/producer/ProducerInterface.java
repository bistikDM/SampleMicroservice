package com.hydron.sample.services.producer;

import com.hydron.sample.pojo.ExampleData;

/**
 * An interface meant for any producer type classes to implement so that the logic can be abstracted.
 */
public interface ProducerInterface {
    /**
     * Produce data.
     *
     * @return The data produced.
     */
    ExampleData produceData();
}
