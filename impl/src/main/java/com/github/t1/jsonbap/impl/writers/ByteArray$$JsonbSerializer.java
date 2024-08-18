package com.github.t1.jsonbap.impl.writers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class ByteArray$$JsonbSerializer implements JsonbSerializer<byte[]> {
    public static final ByteArray$$JsonbSerializer INSTANCE = new ByteArray$$JsonbSerializer();

    @Override public void serialize(byte[] obj, JsonGenerator generator, SerializationContext ctx) {
        generator.writeStartArray();
        for (Object element : obj) {
            ctx.serialize(element, generator);
        }
        generator.writeEnd();
    }
}
