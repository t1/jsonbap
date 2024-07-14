package com.github.t1.jsonbap.impl;

import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public record JsonGeneratorContext(boolean writeNullValues) implements SerializationContext {
    @Override public <T> void serialize(String key, T object, JsonGenerator generator) {
        generator.writeKey(key);
        ApJsonbProvider.jsonbWriterFor(object.getClass()).toJson(object, generator, this);
    }

    @Override public <T> void serialize(T object, JsonGenerator generator) {
        ApJsonbProvider.jsonbWriterFor(object.getClass()).toJson(object, generator, this);
    }
}
