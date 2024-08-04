package com.github.t1.jsonbap.impl.writers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class Array$$JsonbSerializer implements JsonbSerializer<Object> {
    public static final Array$$JsonbSerializer INSTANCE = new Array$$JsonbSerializer();

    @Override public void serialize(Object obj, JsonGenerator generator, SerializationContext ctx) {
        generator.writeStartArray();
        for (Object element : (Object[]) obj) {
            ctx.serialize(element, generator);
        }
        generator.writeEnd();
    }
}
