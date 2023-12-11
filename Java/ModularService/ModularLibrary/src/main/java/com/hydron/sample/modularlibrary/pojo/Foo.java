package com.hydron.sample.modularlibrary.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;
import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Foo implements ExampleData {
    @JsonProperty
    private String[] names;

    @JsonProperty
    private String identifier;

    @JsonProperty
    private Instant timestamp;

    @JsonProperty
    private static final String text = "%s [%s] - Foo says \"Hello %s!\"";

    @Override
    public String getMessage() {
        return String.format(text, this.identifier, this.timestamp, Arrays.toString(this.names));
    }
}
