package com.github.t1.jsonbap.impl.writers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class FloatArray$$JsonbSerializer implements JsonbSerializer<float[]> {
    public static final FloatArray$$JsonbSerializer INSTANCE = new FloatArray$$JsonbSerializer();

    @Override public void serialize(float[] obj, JsonGenerator generator, SerializationContext ctx) {
        generator.writeStartArray();
        for (Object element : obj) {
            ctx.serialize(element, generator);
        }
        generator.writeEnd();
    }
}
