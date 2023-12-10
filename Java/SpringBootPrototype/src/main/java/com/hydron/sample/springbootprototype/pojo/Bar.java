package com.hydron.sample.springbootprototype.pojo;

import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Bar implements ExampleData {
    private String name;
    private String identifier;
    private Instant timestamp;
    private static final String text = "%s [%s] - Bar says \"Goodbye, %s!\"";

    @Override
    public String getMessage() {
        return String.format(text, this.identifier, this.timestamp, this.name);
    }
}
