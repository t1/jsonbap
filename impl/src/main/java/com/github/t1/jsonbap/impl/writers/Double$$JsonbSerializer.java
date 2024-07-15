package com.github.t1.jsonbap.impl.writers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class Double$$JsonbSerializer implements JsonbSerializer<Double> {
    @Override public void serialize(Double obj, JsonGenerator generator, SerializationContext ctx) {
        generator.write(obj);
    }
}
