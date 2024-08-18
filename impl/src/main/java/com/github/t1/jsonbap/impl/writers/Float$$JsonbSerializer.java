package com.github.t1.jsonbap.impl.writers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class Float$$JsonbSerializer implements JsonbSerializer<Float> {
    @Override public void serialize(Float obj, JsonGenerator generator, SerializationContext ctx) {
        generator.write(obj);
    }
}
