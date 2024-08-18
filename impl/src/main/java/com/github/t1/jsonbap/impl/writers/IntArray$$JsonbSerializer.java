package com.github.t1.jsonbap.impl.writers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class IntArray$$JsonbSerializer implements JsonbSerializer<int[]> {
    public static final IntArray$$JsonbSerializer INSTANCE = new IntArray$$JsonbSerializer();

    @Override public void serialize(int[] obj, JsonGenerator generator, SerializationContext ctx) {
        generator.writeStartArray();
        for (Object element : obj) {
            ctx.serialize(element, generator);
        }
        generator.writeEnd();
    }
}
