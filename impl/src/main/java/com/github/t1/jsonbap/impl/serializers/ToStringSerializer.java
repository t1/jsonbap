package com.github.t1.jsonbap.impl.serializers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class ToStringSerializer<T> implements JsonbSerializer<T> {
    @Override public void serialize(Object obj, JsonGenerator generator, SerializationContext context) {
        generator.write(obj.toString());
    }
}
