package com.github.t1.jsonbap.impl.writers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class Character$$JsonbSerializer implements JsonbSerializer<Character> {
    @Override public void serialize(Character obj, JsonGenerator generator, SerializationContext ctx) {
        generator.write(obj);
    }
}
