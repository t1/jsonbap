package com.github.t1.jsonbap.impl.serializers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class BooleanArray$$JsonbSerializer implements JsonbSerializer<boolean[]> {
    public static final BooleanArray$$JsonbSerializer INSTANCE = new BooleanArray$$JsonbSerializer();

    @Override public void serialize(boolean[] obj, JsonGenerator generator, SerializationContext ctx) {
        generator.writeStartArray();
        for (Object element : obj) {
            ctx.serialize(element, generator);
        }
        generator.writeEnd();
    }
}
