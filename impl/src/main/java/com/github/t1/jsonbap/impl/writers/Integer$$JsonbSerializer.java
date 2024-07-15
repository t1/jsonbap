package com.github.t1.jsonbap.impl.writers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class Integer$$JsonbSerializer implements JsonbSerializer<Integer> {
    @Override public void serialize(Integer obj, JsonGenerator generator, SerializationContext ctx) {
        generator.write(obj);
    }
}
