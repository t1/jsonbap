package com.github.t1.jsonbap.impl.writers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class Short$$JsonbSerializer implements JsonbSerializer<Short> {
    @Override public void serialize(Short obj, JsonGenerator generator, SerializationContext ctx) {
        generator.write(obj);
    }
}
