package com.github.t1.jsonbap.impl.writers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

import java.util.OptionalLong;

public class OptionalLong$$JsonbSerializer implements JsonbSerializer<OptionalLong> {
    @Override public void serialize(OptionalLong obj, JsonGenerator generator, SerializationContext context) {
        obj.ifPresent(generator::write);
    }
}
