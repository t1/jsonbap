package com.github.t1.jsonbap.test;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

// jsonb
@JsonbTypeInfo({
        @JsonbSubtype(alias = "cat", type = Cat.class),
        @JsonbSubtype(alias = "dog", type = Dog.class)
})
// jackson
@JsonTypeInfo(use = NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "dog", value = Dog.class),
        @JsonSubTypes.Type(name = "cat", value = Cat.class)}
)
public interface Pet {
    String getName();
}
