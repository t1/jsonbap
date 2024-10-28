package com.github.t1.jsonbap.impl.serializers;

import jakarta.json.JsonValue;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class JsonValue$$JsonbSerializer implements JsonbSerializer<JsonValue> {
    @Override public void serialize(JsonValue obj, JsonGenerator generator, SerializationContext ctx) {
        generator.write(obj);
    }
}
