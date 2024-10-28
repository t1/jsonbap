package com.github.t1.jsonbap.impl.serializers;

import com.github.t1.jsonbap.api.NullAwareSerializer;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

import java.util.OptionalDouble;

public class OptionalDouble$$JsonbSerializer implements JsonbSerializer<OptionalDouble>, NullAwareSerializer<OptionalDouble> {
    @Override public boolean isNull(OptionalDouble object) {
        return object.isEmpty();
    }

    @Override public void serialize(OptionalDouble obj, JsonGenerator generator, SerializationContext context) {
        obj.ifPresent(generator::write);
    }
}
