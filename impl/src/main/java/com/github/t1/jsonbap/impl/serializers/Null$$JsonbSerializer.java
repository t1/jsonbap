package com.github.t1.jsonbap.impl.serializers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class Null$$JsonbSerializer<T> implements JsonbSerializer<T> {
    public static final JsonbSerializer<?> INSTANCE = new Null$$JsonbSerializer<>();

    @Override public void serialize(Object obj, JsonGenerator generator, SerializationContext ctx) {
        generator.writeNull();
    }
}
