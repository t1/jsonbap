package com.github.t1.jsonbap.impl.serializers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class Long$$JsonbSerializer implements JsonbSerializer<Long> {
    @Override public void serialize(Long obj, JsonGenerator generator, SerializationContext ctx) {
        generator.write(obj);
    }
}
