package com.github.t1.jsonbap.impl;

import com.github.t1.jsonbap.api.JsonbWriter;
import com.github.t1.jsonbap.impl.writers.Iterable$$JsonbWriter;
import com.github.t1.jsonbap.impl.writers.String$$JsonbWriter;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.JsonbException;
import jakarta.json.bind.spi.JsonbProvider;
import jakarta.json.spi.JsonProvider;
import jakarta.json.stream.JsonGenerator;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static jakarta.json.bind.JsonbConfig.FORMATTING;
import static jakarta.json.stream.JsonGenerator.PRETTY_PRINTING;
import static java.lang.Boolean.TRUE;

public class ApJsonbProvider extends JsonbProvider {
    private static final Map<String, JsonbWriter<?>> WRITERS = new ConcurrentHashMap<>();

    static {
        WRITERS.put(ArrayList.class.getName(), new Iterable$$JsonbWriter());
        WRITERS.put(String.class.getName(), new String$$JsonbWriter());
    }

    public static <T> JsonbWriter<T> jsonbWriterFor(Object object) {
        @SuppressWarnings("unchecked") JsonbWriter<T> jsonbWriter = (JsonbWriter<T>)
                WRITERS.computeIfAbsent(object.getClass().getName(), ApJsonbProvider::loadWriterFor);
        return jsonbWriter;
    }

    private static JsonbWriter<?> loadWriterFor(String className) {
        try {
            Class<?> jsonbWriterClass = Class.forName(className + "$$JsonbWriter");
            return (JsonbWriter<?>) jsonbWriterClass.getConstructor().newInstance();
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
            var writer = new StringWriter();
            var jsonpConfig = new HashMap<String, Object>();
            if (config.getProperty(FORMATTING).isPresent() && config.getProperty(FORMATTING).get() == TRUE) {
                jsonpConfig.put(PRETTY_PRINTING, true);
            }
            var factory = jsonpProvider.createGeneratorFactory(jsonpConfig);
            try (var generator = factory.createGenerator(writer)) {
                write(object, generator);
            }
            return writer.toString();
        }

        @Override public String toJson(Object object, Type runtimeType) throws JsonbException {
            throw new UnsupportedOperationException("not yet implemented"); // TODO implement
        }

        @Override public void toJson(Object object, Writer writer) throws JsonbException {
            throw new UnsupportedOperationException("not yet implemented"); // TODO implement
        }

        @Override public void toJson(Object object, Type runtimeType, Writer writer) throws JsonbException {
            throw new UnsupportedOperationException("not yet implemented"); // TODO implement
        }

        @Override public void toJson(Object object, OutputStream stream) throws JsonbException {
            throw new UnsupportedOperationException("not yet implemented"); // TODO implement
        }

        @Override public void toJson(Object object, Type runtimeType, OutputStream stream) throws JsonbException {
            throw new UnsupportedOperationException("not yet implemented"); // TODO implement
        }

        private static <T> void write(T object, JsonGenerator out) {
            jsonbWriterFor(object).toJson(object, out);
        }

        @Override public void close() {
        }
    }
}
