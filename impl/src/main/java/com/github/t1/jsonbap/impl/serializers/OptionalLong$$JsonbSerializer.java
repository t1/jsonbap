package com.github.t1.jsonbap.impl.serializers;

import com.github.t1.jsonbap.api.NullAwareSerializer;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

import java.util.OptionalLong;

public class OptionalLong$$JsonbSerializer implements JsonbSerializer<OptionalLong>, NullAwareSerializer<OptionalLong> {
    @Override public boolean isNull(OptionalLong object) {
        return object.isEmpty();
    }

    @Override public void serialize(OptionalLong obj, JsonGenerator generator, SerializationContext context) {
        obj.ifPresent(generator::write);
    }
}
