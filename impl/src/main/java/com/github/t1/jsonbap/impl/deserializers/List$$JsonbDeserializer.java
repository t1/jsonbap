package com.github.t1.jsonbap.impl.deserializers;

import com.github.t1.jsonbap.runtime.FluentParser;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static jakarta.json.stream.JsonParser.Event;

@RequiredArgsConstructor
public class List$$JsonbDeserializer<T> implements JsonbDeserializer<List<T>> {
    private final Type subtype;

    @Override public List<T> deserialize(JsonParser jsonParser, DeserializationContext ctx, Type rtType) {
        var list = new ArrayList<T>();
        var parser = new FluentParser(jsonParser);
        parser.assume(Event.START_ARRAY);
        while (parser.hasNext() && !parser.next().is(Event.END_ARRAY)) {
            list.add(parser.deserialize(ctx, subtype));
        }
        return list;
    }
}
