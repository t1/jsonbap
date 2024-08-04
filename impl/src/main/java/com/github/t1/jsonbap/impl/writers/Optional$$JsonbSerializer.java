package com.github.t1.jsonbap.impl.writers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

import java.util.Optional;

public class Optional$$JsonbSerializer implements JsonbSerializer<Optional<?>> {
    @Override public void serialize(Optional<?> obj, JsonGenerator generator, SerializationContext context) {
        obj.ifPresent(value -> context.serialize(value, generator));
    }
}
