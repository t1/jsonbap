package com.github.t1.jsonbap.impl;

import com.github.t1.jsonbap.impl.writers.Integer$$JsonbSerializer;
import com.github.t1.jsonbap.impl.writers.Iterable$$JsonbSerializer;
import com.github.t1.jsonbap.impl.writers.String$$JsonbSerializer;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.JsonbException;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.spi.JsonbProvider;
import jakarta.json.spi.JsonProvider;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonGeneratorFactory;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static jakarta.json.bind.JsonbConfig.FORMATTING;
import static jakarta.json.bind.JsonbConfig.NULL_VALUES;
import static jakarta.json.stream.JsonGenerator.PRETTY_PRINTING;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ApJsonbProvider extends JsonbProvider {
    private static final Map<String, JsonbSerializer<?>> SERIALIZERS = new ConcurrentHashMap<>();

    static {
        SERIALIZERS.put(List.class.getName(), new Iterable$$JsonbSerializer());
        SERIALIZERS.put(ArrayList.class.getName(), new Iterable$$JsonbSerializer());
        // ---------- Types directly supported by JsonGenerator:
        // TODO JsonValue
        SERIALIZERS.put(String.class.getName(), new String$$JsonbSerializer());
        // TODO BigInteger
        // TODO BigDecimal
        SERIALIZERS.put(Integer.class.getName(), new Integer$$JsonbSerializer());
        // TODO long, Long
        // TODO double, Double
        // TODO boolean, Boolean
    }

    public static <T> JsonbSerializer<T> serializerFor(Object object) {
        return serializerFor(object.getClass());
    }

    @SuppressWarnings("unchecked")
    public static <T> JsonbSerializer<T> serializerFor(Type type) {
        return (JsonbSerializer<T>)
                SERIALIZERS.computeIfAbsent(type.getTypeName(), ApJsonbProvider::loadWriterFor);
    }

    private static JsonbSerializer<?> loadWriterFor(String className) {
        try {
            Class<?> serializerClass = Class.forName(className + "$$JsonbSerializer");
            return (JsonbSerializer<?>) serializerClass.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("can't create instance of jsonb writer for " + className, e);
        }
    }


    @Override public JsonbBuilder create() {return new ApJsonbBuilder();}

    public static class ApJsonbBuilder implements JsonbBuilder {
        private JsonbConfig config;
        private JsonProvider jsonpProvider;

        @Override public JsonbBuilder withConfig(JsonbConfig config) {
            this.config = config;
            return this;
        }

        @Override public JsonbBuilder withProvider(JsonProvider jsonpProvider) {
            this.jsonpProvider = jsonpProvider;
            return this;
        }

        @Override public Jsonb build() {
            if (config == null) config = new JsonbConfig();
            if (jsonpProvider == null) jsonpProvider = JsonProvider.provider();
            return new ApJsonb(config, jsonpProvider);
        }
    }


    @RequiredArgsConstructor
    public static class ApJsonb implements Jsonb {
        private final JsonbConfig config;
        private final JsonProvider jsonpProvider;

        private boolean booleanConfig(String name) {
            return config.getProperty(name).orElse(FALSE) == TRUE;
        }

        @Override public <T> T fromJson(String str, Class<T> type) throws JsonbException {
            throw new UnsupportedOperationException("not yet implemented"); // TODO implement
        }

        @Override public <T> T fromJson(String str, Type runtimeType) throws JsonbException {
            throw new UnsupportedOperationException("not yet implemented"); // TODO implement
        }

        @Override public <T> T fromJson(Reader reader, Class<T> type) throws JsonbException {
            throw new UnsupportedOperationException("not yet implemented"); // TODO implement
        }

        @Override public <T> T fromJson(Reader reader, Type runtimeType) throws JsonbException {
            throw new UnsupportedOperationException("not yet implemented"); // TODO implement
        }

        @Override public <T> T fromJson(InputStream stream, Class<T> type) throws JsonbException {
            throw new UnsupportedOperationException("not yet implemented"); // TODO implement
        }

        @Override public <T> T fromJson(InputStream stream, Type runtimeType) throws JsonbException {
            throw new UnsupportedOperationException("not yet implemented"); // TODO implement
        }

        @Override public String toJson(Object object) throws JsonbException {
            return toJson(object, object.getClass());
        }

        @Override public String toJson(Object object, Type runtimeType) throws JsonbException {
            var writer = new StringWriter();
            toJson(object, runtimeType, writer);
            return writer.toString();
        }

        @Override public void toJson(Object object, Writer writer) throws JsonbException {
            toJson(object, object.getClass(), writer);
        }

        @Override public void toJson(Object object, Type runtimeType, Writer writer) throws JsonbException {
            var factory = createJsonGeneratorFactory();
            try (var generator = factory.createGenerator(writer)) {
                write(object, runtimeType, generator);
            }
        }

        @Override public void toJson(Object object, OutputStream stream) throws JsonbException {
            toJson(object, object.getClass(), stream);
        }

        @Override public void toJson(Object object, Type runtimeType, OutputStream stream) throws JsonbException {
            var factory = createJsonGeneratorFactory();
            try (var generator = factory.createGenerator(stream)) {
                write(object, runtimeType, generator);
            }
        }

        private JsonGeneratorFactory createJsonGeneratorFactory() {
            var jsonpConfig = new HashMap<String, Object>();
            if (booleanConfig(FORMATTING)) {
                jsonpConfig.put(PRETTY_PRINTING, true); // value is insignificant, i.e. FALSE is also pretty
            }
            return jsonpProvider.createGeneratorFactory(jsonpConfig);
        }

        private <T> void write(T object, Type type, JsonGenerator out) {
            var context = new JsonGeneratorContext(booleanConfig(NULL_VALUES));
            serializerFor(type).serialize(object, out, context);
        }

        @Override public void close() {
        }
    }
}
