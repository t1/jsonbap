package com.github.t1.jsonbap.impl;

import com.github.t1.jsonbap.impl.serializers.Array$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.BigDecimal$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.BigInteger$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.Boolean$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.BooleanArray$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.Byte$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.ByteArray$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.CharArray$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.Character$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.Double$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.DoubleArray$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.Float$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.FloatArray$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.IntArray$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.Integer$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.Iterable$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.JsonValue$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.Long$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.LongArray$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.Null$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.Optional$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.OptionalDouble$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.OptionalInt$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.OptionalLong$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.Short$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.ShortArray$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.String$$JsonbSerializer;
import com.github.t1.jsonbap.impl.serializers.ToStringSerializer;
import jakarta.json.JsonValue;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.serializer.JsonbSerializer;
import jakarta.json.bind.spi.JsonbProvider;
import jakarta.json.spi.JsonProvider;

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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.SimpleTimeZone;
import java.util.concurrent.ConcurrentHashMap;

public class ApJsonbProvider extends JsonbProvider {
    private static final Map<Type, JsonbSerializer<?>> SERIALIZERS = new ConcurrentHashMap<>();

    static {
        try {
            SERIALIZERS.put(Optional.class, new Optional$$JsonbSerializer());
            SERIALIZERS.put(OptionalInt.class, new OptionalInt$$JsonbSerializer());
            SERIALIZERS.put(OptionalLong.class, new OptionalLong$$JsonbSerializer());
            SERIALIZERS.put(OptionalDouble.class, new OptionalDouble$$JsonbSerializer());
            SERIALIZERS.put(List.class, new Iterable$$JsonbSerializer());
            SERIALIZERS.put(ArrayList.class, new Iterable$$JsonbSerializer());
            SERIALIZERS.put(LinkedList.class, new Iterable$$JsonbSerializer());
            // TODO automatically add all instances of e.g. Iterable in #loadSerializerFor (no new instance necessary)

            // TODO add all other collections from ImmutableCollections and Collections
            SERIALIZERS.put(Class.forName("java.util.ImmutableCollections$List12"), new Iterable$$JsonbSerializer());
            SERIALIZERS.put(Class.forName("java.util.ImmutableCollections$ListN"), new Iterable$$JsonbSerializer());
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
            SERIALIZERS.put(SimpleTimeZone.class, new ToStringSerializer<>());
            SERIALIZERS.put(ZonedDateTime.class, new ToStringSerializer<>());
            SERIALIZERS.put(ZoneOffset.class, new ToStringSerializer<>());
            SERIALIZERS.put(Class.forName("java.time.ZoneRegion"), new ToStringSerializer<>());

            // old date/time types
            SERIALIZERS.put(java.util.Date.class, new ToStringSerializer<>());
            SERIALIZERS.put(GregorianCalendar.class, new ToStringSerializer<>());
            SERIALIZERS.put(Class.forName("sun.util.calendar.ZoneInfo"), new ToStringSerializer<>());

            // ---------- Types directly supported by JsonGenerator:
            SERIALIZERS.put(JsonValue.class, new JsonValue$$JsonbSerializer());
            SERIALIZERS.put(String.class, new String$$JsonbSerializer());
            SERIALIZERS.put(BigInteger.class, new BigInteger$$JsonbSerializer());
            SERIALIZERS.put(BigDecimal.class, new BigDecimal$$JsonbSerializer());
            SERIALIZERS.put(Byte.class, new Byte$$JsonbSerializer());
            SERIALIZERS.put(Character.class, new Character$$JsonbSerializer());
            SERIALIZERS.put(Short.class, new Short$$JsonbSerializer());
            SERIALIZERS.put(Integer.class, new Integer$$JsonbSerializer());
            SERIALIZERS.put(Long.class, new Long$$JsonbSerializer());
            SERIALIZERS.put(Float.class, new Float$$JsonbSerializer());
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
                SERIALIZERS.computeIfAbsent(type, ApJsonbProvider::loadSerializerFor));
    }

    static Type typeOf(Object object) {
        return (object == null) ? null : object.getClass();
    }

    private static JsonbSerializer<?> loadSerializerFor(Type type) {
        if (type instanceof Class<?> c && JsonValue.class.isAssignableFrom(c)) {
            return new JsonValue$$JsonbSerializer();
        }
        if (type instanceof Class<?> c && c.isArray()) {
            if (c.componentType() == boolean.class) return BooleanArray$$JsonbSerializer.INSTANCE;
            if (c.componentType() == byte.class) return ByteArray$$JsonbSerializer.INSTANCE;
            if (c.componentType() == short.class) return ShortArray$$JsonbSerializer.INSTANCE;
            if (c.componentType() == char.class) return CharArray$$JsonbSerializer.INSTANCE;
            if (c.componentType() == int.class) return IntArray$$JsonbSerializer.INSTANCE;
            if (c.componentType() == long.class) return LongArray$$JsonbSerializer.INSTANCE;
            if (c.componentType() == float.class) return FloatArray$$JsonbSerializer.INSTANCE;
            if (c.componentType() == double.class) return DoubleArray$$JsonbSerializer.INSTANCE;
            return Array$$JsonbSerializer.INSTANCE;
        }
        try {
            Class<?> serializerClass = Class.forName(nonGenericTypeName(type) + "$$JsonbSerializer");
            return (JsonbSerializer<?>) serializerClass.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("can't create instance of jsonb serializer for " + type, e);
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
            return new JsonbapJsonb(config, jsonpProvider);
        }
    }
}
