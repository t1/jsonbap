package com.github.t1.jsonbap.runtime;

import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.Optional;

import static java.util.Locale.ROOT;

public final class FluentParser {
    public static String titleCase(String name) {
        return name.substring(0, 1).toUpperCase(ROOT) + name.substring(1);
    }


    public FluentParser(JsonParser parser) {
        this.parser = parser;
        if (parser.currentEvent() == null) parser.next();
    }

    private final JsonParser parser;

    public static Optional<String> readMethod(String simpleTypeName) {
        return switch (simpleTypeName) {
            case "String", "Boolean", "Integer", "Long", "Double", "BigDecimal" -> Optional.of("read" + simpleTypeName);
            case "int" -> Optional.of("readInteger");
            case "boolean", "long", "double" -> Optional.of("read" + titleCase(simpleTypeName));
            default -> Optional.empty();
        };
    }

    @Override public String toString() {
        return "Parser@" + parser.currentEvent() +
               switch (parser.currentEvent()) {
                   case null -> "";
                   case START_ARRAY, START_OBJECT, VALUE_TRUE, VALUE_FALSE, VALUE_NULL, END_OBJECT, END_ARRAY -> "";
                   case KEY_NAME, VALUE_STRING -> ":" + parser.getString();
                   case VALUE_NUMBER -> ":" + parser.getBigDecimal();
               };
    }

    public FluentParser assume(JsonParser.Event... events) {return assume(EnumSet.of(events[0], events));}

    public FluentParser assume(EnumSet<JsonParser.Event> events) {
        if (!is(events)) throw new IllegalStateException("expected " + events + " but was " + parser.currentEvent());
        return this;
    }

    public boolean is(JsonParser.Event... events) {return is(EnumSet.of(events[0], events));}

    public boolean is(EnumSet<JsonParser.Event> events) {return events.contains(parser.currentEvent());}

    public FluentParser next() {
        parser.next();
        return this;
    }

    public void skipArray() {parser.skipArray();}

    public <T> T deserialize(DeserializationContext ctx, Type type) {return ctx.deserialize(type, parser);}


    public String StringAndNext() {
        var string = parser.getString();
        next();
        return string;
    }

    public Optional<String> readString() {
        return switch (parser.currentEvent()) {
            case VALUE_NULL -> Optional.empty();
            case VALUE_STRING -> Optional.of(parser.getString());
            default -> throw new IllegalStateException("unexpected " + parser.currentEvent());
        };
    }

    public Optional<Boolean> readBoolean() {
        return switch (parser.currentEvent()) {
            case VALUE_NULL -> Optional.empty();
            case VALUE_TRUE -> Optional.of(true);
            case VALUE_FALSE -> Optional.of(false);
            default -> throw new IllegalStateException("unexpected " + parser.currentEvent());
        };
    }

    public Optional<Integer> readInteger() {
        return switch (parser.currentEvent()) {
            case VALUE_NULL -> Optional.empty();
            case VALUE_NUMBER -> Optional.of(parser.getInt());
            default -> throw new IllegalStateException("unexpected " + parser.currentEvent());
        };
    }

    public Optional<Long> readLong() {
        return switch (parser.currentEvent()) {
            case VALUE_NULL -> Optional.empty();
            case VALUE_NUMBER -> Optional.of(parser.getLong());
            default -> throw new IllegalStateException("unexpected " + parser.currentEvent());
        };
    }

    public Optional<Double> readDouble() {
        return switch (parser.currentEvent()) {
            case VALUE_NULL -> Optional.empty();
            case VALUE_NUMBER -> Optional.of(parser.getBigDecimal().doubleValue());
            default -> throw new IllegalStateException("unexpected " + parser.currentEvent());
        };
    }

    public Optional<BigDecimal> BigDecimal() {
        return switch (parser.currentEvent()) {
            case VALUE_NULL -> Optional.empty();
            case VALUE_NUMBER -> Optional.of(parser.getBigDecimal());
            case VALUE_STRING -> Optional.of(parser.getString()).map(BigDecimal::new);
            default -> throw new IllegalStateException("unexpected " + parser.currentEvent());
        };
    }
}
