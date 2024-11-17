package com.github.t1.jsonbap.test;

import com.github.t1.jsonbap.runtime.FluentParser;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;
import java.util.stream.Stream;

import static jakarta.json.stream.JsonParser.Event;

@SuppressWarnings("unused")
public class Person$$JsonbDeserializer implements JsonbDeserializer<Person> {
    @Override public Person deserialize(JsonParser jsonParser, DeserializationContext ctx, Type rtType) {
        var parser = new FluentParser(jsonParser);
        if (parser.is(Event.VALUE_NULL)) return null;
        var builder = Person.builder();
        parser.assume(Event.START_OBJECT);
        while (parser.next().is(Event.KEY_NAME)) {
            switch (parser.StringAndNext()) {
                case "firstName" -> parser.readString().ifPresent(builder::firstName);
                case "lastName" -> parser.readString().ifPresent(builder::lastName);
                case "age" -> parser.readInteger().ifPresent(builder::age);
                case "averageScore" -> parser.readDouble().ifPresent(builder::averageScore);
                case "address" -> builder.address(parser.deserialize(ctx, Address.class));
                case "formerAddress" -> builder.formerAddress(parser.deserialize(ctx, Address.class));
                case "member" -> parser.readBoolean().ifPresent(builder::member);
                case "roles" -> builder.roles(Stream.of(parser.deserialize(ctx, String[].class)).toList());
                case "registrationTimestamp" -> parser.readLong().ifPresent(builder::registrationTimestamp);
                case "pets" -> Stream.of(parser.deserialize(ctx, Pet[].class)).forEach(builder::pet);
                case "income" -> parser.readBigDecimal().ifPresent(builder::income);
            }
        }
        parser.assume(Event.END_OBJECT);
        return builder.build();
    }
}
