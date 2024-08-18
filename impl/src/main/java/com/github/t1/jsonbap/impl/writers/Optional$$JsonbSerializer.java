package com.github.t1.jsonbap.impl.writers;

import com.github.t1.jsonbap.api.NullAwareSerializer;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

import java.util.Optional;

public class Optional$$JsonbSerializer implements JsonbSerializer<Optional<?>>, NullAwareSerializer<Optional<?>> {
    @Override public void serialize(Optional<?> obj, JsonGenerator generator, SerializationContext context) {
        obj.ifPresent(value -> context.serialize(value, generator));
    }

    @Override public boolean isNull(Optional<?> object) {
        return object.isEmpty();
    }
}
