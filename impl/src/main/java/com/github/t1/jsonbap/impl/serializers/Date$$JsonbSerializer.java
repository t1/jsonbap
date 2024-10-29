package com.github.t1.jsonbap.impl.serializers;

import com.github.t1.jsonbap.impl.JsonGeneratorContext;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

import java.util.Date;

public class Date$$JsonbSerializer implements jakarta.json.bind.serializer.JsonbSerializer<Date> {
    @Override public void serialize(Date obj, JsonGenerator generator, SerializationContext ctx) {
        generator.write(((JsonGeneratorContext) ctx).dateFormat()
                .map(formatter -> formatter.format(obj))
                .orElseGet(obj::toString));
    }
}
