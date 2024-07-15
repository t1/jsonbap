package com.github.t1.jsonbap.impl;

import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public record JsonGeneratorContext(boolean writeNullValues) implements SerializationContext {
    @Override public <T> void serialize(String key, T object, JsonGenerator generator) {
        if (object == null) {
            if (writeNullValues) {
                generator.writeNull(key);
            }
        } else {
            generator.writeKey(key);
            var serializer = ApJsonbProvider.serializerFor(object);
            serializer.serialize(object, generator, this);
        }
    }

    @Override public <T> void serialize(T object, JsonGenerator generator) {
        if (object == null) {
            if (writeNullValues) {
                generator.writeNull();
            }
        } else {
            var serializer = ApJsonbProvider.serializerFor(object);
            serializer.serialize(object, generator, this);
        }
    }
}
