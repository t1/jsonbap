package com.github.t1.jsonbap.impl;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.JsonbException;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.spi.JsonProvider;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonGeneratorFactory;
import jakarta.json.stream.JsonParser;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.HashMap;

import static com.github.t1.jsonbap.impl.JsonGeneratorContext.bool;
import static jakarta.json.bind.JsonbConfig.FORMATTING;
import static jakarta.json.stream.JsonGenerator.PRETTY_PRINTING;

@RequiredArgsConstructor
public class JsonbapJsonb implements Jsonb {
    private final JsonbConfig config;
    private final JsonProvider jsonpProvider;

    @Override public <T> T fromJson(String str, Class<T> type) throws JsonbException {
        return fromJson(str, (Type) type);
    }

    @Override public <T> T fromJson(String str, Type type) throws JsonbException {
        return fromJson(new StringReader(str), type);
    }

    @Override public <T> T fromJson(Reader reader, Class<T> type) throws JsonbException {
        return fromJson(reader, (Type) type);
    }

    @Override public <T> T fromJson(Reader reader, Type type) throws JsonbException {
        JsonParser parser = jsonpProvider.createParser(reader);
        DeserializationContext context = new JsonParserContext(config);
        JsonbDeserializer<T> deserializer = ApJsonbProvider.deserializerFor(type);
        return deserializer.deserialize(parser, context, type);
    }

    @Override public <T> T fromJson(InputStream stream, Class<T> type) throws JsonbException {
        return fromJson(stream, (Type) type);
    }

    @Override public <T> T fromJson(InputStream stream, Type type) throws JsonbException {
        return fromJson(new InputStreamReader(stream), type);
    }

    static RuntimeException notYetImplemented(@SuppressWarnings("SameParameterValue") String message) {
        try {
            return (RuntimeException) Class.forName("org.opentest4j.TestAbortedException")
                    .getConstructor(String.class)
                    .newInstance(message);
        } catch (ReflectiveOperationException e) {
            return new UnsupportedOperationException("not yet implemented");
        }
    }

    @Override public String toJson(Object object) throws JsonbException {
        return toJson(object, ApJsonbProvider.typeOf(object));
    }

    @Override public String toJson(Object object, Type runtimeType) throws JsonbException {
        var writer = new StringWriter();
        toJson(object, runtimeType, writer);
        return writer.toString();
    }

    @Override public void toJson(Object object, Writer writer) throws JsonbException {
        toJson(object, ApJsonbProvider.typeOf(object), writer);
    }

    @Override public void toJson(Object object, Type runtimeType, Writer writer) throws JsonbException {
        var factory = createJsonGeneratorFactory();
        try (var generator = factory.createGenerator(writer)) {
            write(object, runtimeType, generator);
        }
    }

    @Override public void toJson(Object object, OutputStream stream) throws JsonbException {
        toJson(object, ApJsonbProvider.typeOf(object), stream);
    }

    @Override public void toJson(Object object, Type runtimeType, OutputStream stream) throws JsonbException {
        var factory = createJsonGeneratorFactory();
        try (var generator = factory.createGenerator(stream)) {
            write(object, runtimeType, generator);
        }
    }

    private JsonGeneratorFactory createJsonGeneratorFactory() {
        var jsonpConfig = new HashMap<String, Object>();
        if (bool(config, FORMATTING)) {
            jsonpConfig.put(PRETTY_PRINTING, true); // value is insignificant, i.e. FALSE is also pretty
        }
        return jsonpProvider.createGeneratorFactory(jsonpConfig);
    }

    private <T> void write(T object, Type type, JsonGenerator out) {
        var context = new JsonGeneratorContext(config);
        ApJsonbProvider.serializerFor(type).serialize(object, out, context);
    }

    @Override public void close() {}
}
