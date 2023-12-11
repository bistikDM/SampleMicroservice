package com.hydron.sample.modularlibrary.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Bar implements ExampleData {
    @JsonProperty
    private String name;

    @JsonProperty
    private String identifier;

    @JsonProperty
    private Instant timestamp;

    @JsonProperty
    private static final String text = "%s [%s] - Bar says \"Goodbye, %s!\"";

    @Override
    public String getMessage() {
        return String.format(text, this.identifier, this.timestamp, this.name);
    }
}
