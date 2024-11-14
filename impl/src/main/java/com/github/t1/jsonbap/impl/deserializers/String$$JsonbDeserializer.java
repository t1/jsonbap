package com.github.t1.jsonbap.impl.deserializers;

import com.github.t1.jsonbap.runtime.FluentParser;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;

public class String$$JsonbDeserializer implements JsonbDeserializer<String> {
    @Override public String deserialize(JsonParser jsonParser, DeserializationContext ctx, Type rtType) {
        var parser = new FluentParser(jsonParser);
        return parser.StringAndNext();
    }
}
