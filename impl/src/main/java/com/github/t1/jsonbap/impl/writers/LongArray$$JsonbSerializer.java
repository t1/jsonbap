package com.github.t1.jsonbap.impl.writers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class LongArray$$JsonbSerializer implements JsonbSerializer<long[]> {
    public static final LongArray$$JsonbSerializer INSTANCE = new LongArray$$JsonbSerializer();

    @Override public void serialize(long[] obj, JsonGenerator generator, SerializationContext ctx) {
        generator.writeStartArray();
        for (Object element : obj) {
            ctx.serialize(element, generator);
        }
        generator.writeEnd();
    }
}
