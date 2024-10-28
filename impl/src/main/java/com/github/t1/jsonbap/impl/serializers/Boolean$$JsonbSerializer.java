package com.github.t1.jsonbap.impl.serializers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class Boolean$$JsonbSerializer implements JsonbSerializer<Boolean> {
    @Override public void serialize(Boolean obj, JsonGenerator generator, SerializationContext ctx) {
        generator.write(obj);
    }
}
