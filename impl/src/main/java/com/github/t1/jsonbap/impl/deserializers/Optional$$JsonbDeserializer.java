package com.github.t1.jsonbap.impl.deserializers;

import com.github.t1.jsonbap.runtime.FluentParser;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Type;
import java.util.Optional;

import static jakarta.json.stream.JsonParser.Event;

@RequiredArgsConstructor
public class Optional$$JsonbDeserializer<T> implements JsonbDeserializer<Optional<T>> {
    private final Type subtype;

    @Override public Optional<T> deserialize(JsonParser jsonParser, DeserializationContext ctx, Type rtType) {
        var parser = new FluentParser(jsonParser);
        if (parser.is(Event.VALUE_NULL)) {
            parser.next();
            return Optional.empty();
        }
        return Optional.of(parser.deserialize(ctx, subtype));
    }
}
