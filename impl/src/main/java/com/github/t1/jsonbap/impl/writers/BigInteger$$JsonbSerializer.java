package com.github.t1.jsonbap.impl.writers;

import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

import java.math.BigInteger;

public class BigInteger$$JsonbSerializer implements JsonbSerializer<BigInteger> {
    @Override public void serialize(BigInteger obj, JsonGenerator generator, SerializationContext ctx) {
        generator.write(obj);
    }
}
