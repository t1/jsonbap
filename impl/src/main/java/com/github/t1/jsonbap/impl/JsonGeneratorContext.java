package com.github.t1.jsonbap.impl;

import com.github.t1.jsonbap.runtime.DateTimeWriter;
import com.github.t1.jsonbap.runtime.NullWriter;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.serializer.SerializationContext;
import jakarta.json.stream.JsonGenerator;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import static jakarta.json.bind.JsonbConfig.DATE_FORMAT;
import static jakarta.json.bind.JsonbConfig.LOCALE;
import static jakarta.json.bind.JsonbConfig.NULL_VALUES;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public record JsonGeneratorContext(
        boolean writeNullValues,
        Locale locale,
        Optional<DateTimeFormatter> dateTimeFormatter,
        Optional<DateFormat> dateFormat)

        implements SerializationContext {

    public JsonGeneratorContext(JsonbConfig config) {
        this(
                bool(config, NULL_VALUES),
                locale(config).orElseGet(Locale::getDefault),
                string(config, DATE_FORMAT).map(pattern -> DateTimeWriter.dateTimeFormatter(pattern, locale(config))),
                string(config, DATE_FORMAT).map(pattern -> DateTimeWriter.dateFormat(pattern, locale(config))));
    }

    private static Optional<Locale> locale(JsonbConfig config) {return string(config, LOCALE).map(Locale::forLanguageTag);}
    private static Optional<String> string(JsonbConfig config, String name) {return config.getProperty(name).map(Object::toString);}
    static boolean bool(JsonbConfig config, String name) {return config.getProperty(name).orElse(FALSE) == TRUE;}


    @Override public <T> void serialize(String key, T object, JsonGenerator generator) {
        var serializer = ApJsonbProvider.serializerFor(object);
        if (NullWriter.isNull(serializer, object)) {
            if (writeNullValues) {
                generator.writeNull(key);
            }
        } else {
            generator.writeKey(key);
            serializer.serialize(object, generator, this);
        }
    }

    @Override public <T> void serialize(T object, JsonGenerator generator) {
        var serializer = ApJsonbProvider.serializerFor(object);
        if (NullWriter.isNull(serializer, object)) {
            if (writeNullValues) {
                generator.writeNull();
            }
        } else {
            serializer.serialize(object, generator, this);
        }
    }
}
