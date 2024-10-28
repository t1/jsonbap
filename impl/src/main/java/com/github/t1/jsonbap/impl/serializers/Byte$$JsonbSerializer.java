package com.github.t1.jsonbap.impl.serializers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class Byte$$JsonbSerializer implements JsonbSerializer<Byte> {
    @Override public void serialize(Byte obj, JsonGenerator generator, SerializationContext ctx) {
        generator.write(obj);
    }
}
