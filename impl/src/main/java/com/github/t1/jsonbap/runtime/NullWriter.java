package com.github.t1.jsonbap.runtime;

import com.github.t1.jsonbap.api.NullAwareSerializer;
import com.github.t1.jsonbap.impl.ApJsonbProvider;
import jakarta.json.JsonValue;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NullWriter {
    /// Write key and value, or `null` if the value is `null`
    public static void writeNillable(String name, JsonValue value, JsonGenerator out, @SuppressWarnings("unused") SerializationContext context) {
        if (value == null) out.writeNull(name);
        else out.write(name, value);
    }

    /// Write key and value, or `null` if the value is `null`
    public static void writeNillable(String name, String value, JsonGenerator out, @SuppressWarnings("unused") SerializationContext context) {
        if (value == null) out.writeNull(name);
        else out.write(name, value);
    }

    /// Write key and value, or `null` if the value is `null`
    public static void writeNillable(String name, BigInteger value, JsonGenerator out, @SuppressWarnings("unused") SerializationContext context) {
        if (value == null) out.writeNull(name);
        else out.write(name, value);
    }

    /// Write key and value, or `null` if the value is `null`
    public static void writeNillable(String name, BigDecimal value, JsonGenerator out, @SuppressWarnings("unused") SerializationContext context) {
        if (value == null) out.writeNull(name);
        else out.write(name, value);
    }

    /// Write key and value, or `null` if the value is `null`
    public static void writeNillable(String name, Object value, JsonGenerator out, SerializationContext context) {
        var serializer = ApJsonbProvider.serializerFor(value);
        if (NullWriter.isNull(serializer, value)) {
            out.writeNull(name);
        } else {
            out.writeKey(name);
            serializer.serialize(value, out, context);
        }
    }


    /// Write key and value, or neither if the value is `null`
    public static void writeNullable(String name, JsonValue value, JsonGenerator out, @SuppressWarnings("unused") SerializationContext context) {
        if (value != null) out.write(name, value);
    }

    /// Write key and value, or neither if the value is `null`
    public static void writeNullable(String name, String value, JsonGenerator out, @SuppressWarnings("unused") SerializationContext context) {
        if (value != null) out.write(name, value);
    }

    /// Write key and value, or neither if the value is `null`
    public static void writeNullable(String name, BigInteger value, JsonGenerator out, @SuppressWarnings("unused") SerializationContext context) {
        if (value != null) out.write(name, value);
    }

    /// Write key and value, or neither if the value is `null`
    public static void writeNullable(String name, BigDecimal value, JsonGenerator out, @SuppressWarnings("unused") SerializationContext context) {
        if (value != null) out.write(name, value);
    }

    /// Write key and value, or neither if the value is `null`
    public static void writeNullable(String name, Object value, JsonGenerator out, SerializationContext context) {
        var serializer = ApJsonbProvider.serializerFor(value);
        if (!NullWriter.isNull(serializer, value)) {
            out.writeKey(name);
            serializer.serialize(value, out, context);
        }
    }


    public static <T> boolean isNull(JsonbSerializer<Object> serializer, T object) {
        //noinspection rawtypes,unchecked // it seems to be impossible to make this type-safe
        return object == null || (serializer instanceof NullAwareSerializer nullAware && nullAware.isNull(object));
    }
}
