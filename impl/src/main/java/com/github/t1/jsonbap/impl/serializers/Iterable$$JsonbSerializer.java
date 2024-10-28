package com.github.t1.jsonbap.impl.serializers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class Iterable$$JsonbSerializer implements JsonbSerializer<Iterable<?>> {
    @Override public void serialize(Iterable<?> obj, JsonGenerator generator, SerializationContext context) {
        generator.writeStartArray();
        for (Object item : obj) {
            if (item == null) {
                generator.writeNull(); // in arrays, nulls need to be written, no matter the config
            } else {
                context.serialize(item, generator);
            }
        }
        generator.writeEnd();
    }
}
