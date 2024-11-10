package com.github.t1.jsonbap.runtime;

import jakarta.json.stream.JsonParser;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.Optional;

@RequiredArgsConstructor
public final class ParserHelper {
    private final JsonParser parser;

    @Override public String toString() {
        return "Parser@" + parser.currentEvent() +
               switch (parser.currentEvent()) {
                   case START_ARRAY, START_OBJECT, VALUE_TRUE, VALUE_FALSE, VALUE_NULL, END_OBJECT, END_ARRAY -> "";
                   case KEY_NAME, VALUE_STRING -> ":" + parser.getString();
                   case VALUE_NUMBER -> ":" + parser.getBigDecimal();
               };
    }

    public void assume(JsonParser.Event... events) {assume(EnumSet.of(events[0], events));}

    public void assume(EnumSet<JsonParser.Event> events) {
        if (!is(events)) throw new IllegalStateException("expected " + events + " but was " + parser.currentEvent());
    }

    public boolean is(JsonParser.Event... events) {return is(EnumSet.of(events[0], events));}

    public boolean is(EnumSet<JsonParser.Event> events) {return events.contains(parser.currentEvent());}

    public ParserHelper next() {
        parser.next();
        return this;
    }

    public String getString() {return parser.getString();}

    public Optional<String> String() {
        return switch (parser.currentEvent()) {
            case VALUE_NULL -> Optional.empty();
            case VALUE_STRING -> Optional.of(parser.getString());
            default -> throw new IllegalStateException("unexpected " + parser.currentEvent());
        };
    }

    public Optional<Boolean> Boolean() {
        return switch (parser.currentEvent()) {
            case VALUE_NULL -> Optional.empty();
            case VALUE_TRUE -> Optional.of(true);
            case VALUE_NUMBER -> Optional.of(false);
            default -> throw new IllegalStateException("unexpected " + parser.currentEvent());
        };
    }

    public Optional<Integer> Integer() {
        return switch (parser.currentEvent()) {
            case VALUE_NULL -> Optional.empty();
            case VALUE_NUMBER -> Optional.of(parser.getInt());
            default -> throw new IllegalStateException("unexpected " + parser.currentEvent());
        };
    }

    public Optional<Long> Long() {
        return switch (parser.currentEvent()) {
            case VALUE_NULL -> Optional.empty();
            case VALUE_NUMBER -> Optional.of(parser.getLong());
            default -> throw new IllegalStateException("unexpected " + parser.currentEvent());
        };
    }

    public Optional<Double> Double() {
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
