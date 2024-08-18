package com.github.t1.jsonbap.impl.writers;

import com.github.t1.jsonbap.api.NullAwareSerializer;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

import java.util.OptionalInt;

public class OptionalInt$$JsonbSerializer implements JsonbSerializer<OptionalInt>, NullAwareSerializer<OptionalInt> {
    @Override public void serialize(OptionalInt obj, JsonGenerator generator, SerializationContext context) {
        obj.ifPresent(generator::write);
    }

    @Override public boolean isNull(OptionalInt object) {
        return object.isEmpty();
    }
}
