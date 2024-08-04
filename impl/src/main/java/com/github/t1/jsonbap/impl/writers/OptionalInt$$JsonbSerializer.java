package com.github.t1.jsonbap.impl.writers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

import java.util.OptionalInt;

public class OptionalInt$$JsonbSerializer implements JsonbSerializer<OptionalInt> {
    @Override public void serialize(OptionalInt obj, JsonGenerator generator, SerializationContext context) {
        obj.ifPresent(generator::write);
    }
}
