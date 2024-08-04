package com.github.t1.jsonbap.impl;

import com.github.t1.jsonbap.impl.writers.Array$$JsonbSerializer;
import com.github.t1.jsonbap.impl.writers.BigDecimal$$JsonbSerializer;
import com.github.t1.jsonbap.impl.writers.BigInteger$$JsonbSerializer;
import com.github.t1.jsonbap.impl.writers.Boolean$$JsonbSerializer;
import com.github.t1.jsonbap.impl.writers.Double$$JsonbSerializer;
import com.github.t1.jsonbap.impl.writers.Integer$$JsonbSerializer;
import com.github.t1.jsonbap.impl.writers.Iterable$$JsonbSerializer;
import com.github.t1.jsonbap.impl.writers.JsonValue$$JsonbSerializer;
import com.github.t1.jsonbap.impl.writers.Long$$JsonbSerializer;
import com.github.t1.jsonbap.impl.writers.Null$$JsonbSerializer;
import com.github.t1.jsonbap.impl.writers.Optional$$JsonbSerializer;
import com.github.t1.jsonbap.impl.writers.OptionalInt$$JsonbSerializer;
import com.github.t1.jsonbap.impl.writers.OptionalLong$$JsonbSerializer;
import com.github.t1.jsonbap.impl.writers.String$$JsonbSerializer;
import com.github.t1.jsonbap.impl.writers.ToStringSerializer;
import jakarta.json.JsonValue;
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.SimpleTimeZone;
import java.util.concurrent.ConcurrentHashMap;

import static jakarta.json.bind.JsonbConfig.FORMATTING;
import static jakarta.json.bind.JsonbConfig.NULL_VALUES;
import static jakarta.json.stream.JsonGenerator.PRETTY_PRINTING;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ApJsonbProvider extends JsonbProvider {
    private static final Map<Type, JsonbSerializer<?>> SERIALIZERS = new ConcurrentHashMap<>();

    static {
        try {
            SERIALIZERS.put(Optional.class, new Optional$$JsonbSerializer());
            SERIALIZERS.put(OptionalInt.class, new OptionalInt$$JsonbSerializer());
            SERIALIZERS.put(OptionalLong.class, new OptionalLong$$JsonbSerializer());
            SERIALIZERS.put(OptionalDouble.class, new Optional$$JsonbSerializer());
            SERIALIZERS.put(List.class, new Iterable$$JsonbSerializer());
            SERIALIZERS.put(ArrayList.class, new Iterable$$JsonbSerializer());
            SERIALIZERS.put(LinkedList.class, new Iterable$$JsonbSerializer());
            // TODO automatically add all instances of e.g. Iterable in #loadWriterFor (no new instance necessary)

            SERIALIZERS.put(Class.forName("java.util.ImmutableCollections$List12"), new Iterable$$JsonbSerializer());
            SERIALIZERS.put(Class.forName("java.util.Collections$UnmodifiableRandomAccessList"), new Iterable$$JsonbSerializer());
            SERIALIZERS.put(Class.forName("java.util.Arrays$ArrayList"), new Iterable$$JsonbSerializer());

            SERIALIZERS.put(Class.class, new ToStringSerializer<>());
            SERIALIZERS.put(URL.class, new ToStringSerializer<>());
            SERIALIZERS.put(URI.class, new ToStringSerializer<>());

            SERIALIZERS.put(Duration.class, new ToStringSerializer<>());
            SERIALIZERS.put(Instant.class, new ToStringSerializer<>());
            SERIALIZERS.put(LocalDate.class, new ToStringSerializer<>());
            SERIALIZERS.put(LocalDateTime.class, new ToStringSerializer<>());
            SERIALIZERS.put(LocalTime.class, new ToStringSerializer<>());
            SERIALIZERS.put(OffsetDateTime.class, new ToStringSerializer<>());
            SERIALIZERS.put(OffsetTime.class, new ToStringSerializer<>());
            SERIALIZERS.put(Period.class, new ToStringSerializer<>());
            SERIALIZERS.put(ZonedDateTime.class, new ToStringSerializer<>());
            SERIALIZERS.put(ZoneOffset.class, new ToStringSerializer<>());
            SERIALIZERS.put(Class.forName("sun.util.calendar.ZoneInfo"), new ToStringSerializer<>());
            SERIALIZERS.put(Class.forName("java.time.ZoneRegion"), new ToStringSerializer<>());
            SERIALIZERS.put(Class.forName("java.util.Date"), new ToStringSerializer<>());
            SERIALIZERS.put(SimpleTimeZone.class, new ToStringSerializer<>());
            SERIALIZERS.put(GregorianCalendar.class, new ToStringSerializer<>());

            // ---------- Types directly supported by JsonGenerator:
            SERIALIZERS.put(JsonValue.class, new JsonValue$$JsonbSerializer());
            SERIALIZERS.put(String.class, new String$$JsonbSerializer());
            SERIALIZERS.put(BigInteger.class, new BigInteger$$JsonbSerializer());
            SERIALIZERS.put(BigDecimal.class, new BigDecimal$$JsonbSerializer());
            SERIALIZERS.put(Byte.class, new Integer$$JsonbSerializer());
            SERIALIZERS.put(Character.class, new Integer$$JsonbSerializer());
            SERIALIZERS.put(Short.class, new Integer$$JsonbSerializer());
            SERIALIZERS.put(Integer.class, new Integer$$JsonbSerializer());
            SERIALIZERS.put(Long.class, new Long$$JsonbSerializer());
            SERIALIZERS.put(Float.class, new Double$$JsonbSerializer());
            SERIALIZERS.put(Double.class, new Double$$JsonbSerializer());
            SERIALIZERS.put(Boolean.class, new Boolean$$JsonbSerializer());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("can't initialize jsonb serializers", e);
        }
    }

    public static <T> JsonbSerializer<T> serializerFor(Object object) {
        return serializerFor(typeOf(object));
    }

    @SuppressWarnings("unchecked")
    public static <T> JsonbSerializer<T> serializerFor(Type type) {
        return (JsonbSerializer<T>) ((type == null) ? Null$$JsonbSerializer.INSTANCE :
                SERIALIZERS.computeIfAbsent(type, ApJsonbProvider::loadWriterFor));
    }

    private static Type typeOf(Object object) {
        return (object == null) ? null : object.getClass();
    }

    private static JsonbSerializer<?> loadWriterFor(Type type) {
        if (type instanceof Class<?> c && JsonValue.class.isAssignableFrom(c)) {
            return new JsonValue$$JsonbSerializer();
        }
        if (type instanceof Class<?> c && c.isArray()) {
            return Array$$JsonbSerializer.INSTANCE;
        }
        try {
            Class<?> serializerClass = Class.forName(nonGenericTypeName(type) + "$$JsonbSerializer");
            return (JsonbSerializer<?>) serializerClass.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("can't create instance of jsonb writer for " + type, e);
        }
    }

    private static String nonGenericTypeName(Type type) {
        var typeName = type.getTypeName();
        var genericTypeIndex = typeName.indexOf('<');
        return genericTypeIndex < 0 ? typeName : typeName.substring(0, typeName.indexOf('<'));
    }


    @Override public JsonbBuilder create() {return new ApJsonbBuilder();}

    public static class ApJsonbBuilder implements JsonbBuilder {
        private JsonbConfig config = new JsonbConfig();
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
            return toJson(object, typeOf(object));
        }

        @Override public String toJson(Object object, Type runtimeType) throws JsonbException {
            var writer = new StringWriter();
            toJson(object, runtimeType, writer);
            return writer.toString();
        }

        @Override public void toJson(Object object, Writer writer) throws JsonbException {
            toJson(object, typeOf(object), writer);
        }

        @Override public void toJson(Object object, Type runtimeType, Writer writer) throws JsonbException {
            var factory = createJsonGeneratorFactory();
            try (var generator = factory.createGenerator(writer)) {
                write(object, runtimeType, generator);
            }
        }

        @Override public void toJson(Object object, OutputStream stream) throws JsonbException {
            toJson(object, typeOf(object), stream);
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
