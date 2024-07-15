package com.github.t1.jsonbap.impl.writers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class Iterable$$JsonbSerializer implements JsonbSerializer<Iterable<?>> {
    @Override public void serialize(Iterable<?> obj, JsonGenerator generator, SerializationContext context) {
        generator.writeStartArray();
        for (Object item : obj) {
            context.serialize(item, generator);
        }
        generator.writeEnd();
    }
}
