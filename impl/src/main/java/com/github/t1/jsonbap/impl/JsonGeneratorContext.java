package com.github.t1.jsonbap.impl;

import com.github.t1.jsonbap.runtime.NullWriter;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

import static jakarta.json.bind.JsonbConfig.NULL_VALUES;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

record JsonGeneratorContext(boolean writeNullValues) implements SerializationContext {
    public JsonGeneratorContext(JsonbConfig config) {
        this(booleanConfig(config, NULL_VALUES));
    }

    static boolean booleanConfig(JsonbConfig config, String name) {
        return config.getProperty(name).orElse(FALSE) == TRUE;
    }

    @Override public <T> void serialize(String key, T object, JsonGenerator generator) {
        var serializer = ApJsonbProvider.serializerFor(object);
        if (NullWriter.isNull(serializer, object)) {
            if (writeNullValues) {
                generator.writeNull(key);
            }
        } else {
            generator.writeKey(key);
            serializer.serialize(object, generator, this);
        }
    }

    @Override public <T> void serialize(T object, JsonGenerator generator) {
        var serializer = ApJsonbProvider.serializerFor(object);
        if (NullWriter.isNull(serializer, object)) {
            if (writeNullValues) {
                generator.writeNull();
            }
        } else {
            serializer.serialize(object, generator, this);
        }
    }
}
