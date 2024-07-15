package com.github.t1.jsonbap.impl.writers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class String$$JsonbSerializer implements JsonbSerializer<String> {
    @Override public void serialize(String obj, JsonGenerator generator, SerializationContext ctx) {
        generator.write(obj);
    }
}
