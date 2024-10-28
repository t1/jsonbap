package com.github.t1.jsonbap.impl.serializers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

public class DoubleArray$$JsonbSerializer implements JsonbSerializer<double[]> {
    public static final DoubleArray$$JsonbSerializer INSTANCE = new DoubleArray$$JsonbSerializer();

    @Override public void serialize(double[] obj, JsonGenerator generator, SerializationContext ctx) {
        generator.writeStartArray();
        for (Object element : obj) {
            ctx.serialize(element, generator);
        }
        generator.writeEnd();
    }
}
