package com.github.t1.jsonbap.impl;

import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.stream.JsonParser;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Type;

@RequiredArgsConstructor
public class JsonParserContext implements DeserializationContext {
    private final JsonbConfig config;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(Class<T> clazz, JsonParser parser) {
        return (T) ApJsonbProvider.deserializerFor(clazz).deserialize(parser, this, clazz);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(Type type, JsonParser parser) {
        return (T) ApJsonbProvider.deserializerFor(type).deserialize(parser, this, type);
    }
}
