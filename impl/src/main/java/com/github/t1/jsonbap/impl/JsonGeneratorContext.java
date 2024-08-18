package com.github.t1.jsonbap.impl;

import com.github.t1.jsonbap.api.NullAwareSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

record JsonGeneratorContext(boolean writeNullValues) implements SerializationContext {
    @Override public <T> void serialize(String key, T object, JsonGenerator generator) {
        if (object == null) {
            if (writeNullValues) {
                generator.writeNull(key);
            }
        } else {
            var serializer = ApJsonbProvider.serializerFor(object);
            //noinspection rawtypes,unchecked // it seems to be impossible to make this type-safe
            if (serializer instanceof NullAwareSerializer nullAware && nullAware.isNull(object)) {
                if (writeNullValues) {
                    generator.writeKey(key);
                    generator.writeNull(key);
                }
            } else {
                generator.writeKey(key);
                serializer.serialize(object, generator, this);
            }
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
