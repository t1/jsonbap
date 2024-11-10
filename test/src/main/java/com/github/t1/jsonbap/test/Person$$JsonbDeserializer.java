package com.github.t1.jsonbap.test;

import com.github.t1.jsonbap.runtime.ParserHelper;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;
import java.util.stream.Stream;

import static jakarta.json.stream.JsonParser.Event.KEY_NAME;
import static jakarta.json.stream.JsonParser.Event.START_OBJECT;
import static jakarta.json.stream.JsonParser.Event.VALUE_NULL;

public class Person$$JsonbDeserializer implements JsonbDeserializer<Person> {
    @Override public Person deserialize(JsonParser jsonParser, DeserializationContext ctx, Type rtType) {
        var parser = new ParserHelper(jsonParser);
        if (parser.next().is(VALUE_NULL)) return null;
        var builder = Person.builder();
        parser.assume(START_OBJECT);
        while (parser.next().is(KEY_NAME)) {
            switch (parser.getString()) {
                case "firstName" -> parser.next().String().ifPresent(builder::firstName);
                case "lastName" -> parser.next().String().ifPresent(builder::lastName);
                case "age" -> parser.next().Integer().ifPresent(builder::age);
                case "averageScore" -> parser.next().Double().ifPresent(builder::averageScore);
                case "address" ->
                        builder.address(new Address$$JsonbDeserializer().deserialize(jsonParser, ctx, Address.class));
                case "formerAddress" ->
                        builder.formerAddress(new Address$$JsonbDeserializer().deserialize(jsonParser, ctx, Address.class));
                case "member" -> parser.next().Boolean().ifPresent(builder::member);
                case "roles" -> builder.roles(Stream.of(ctx.deserialize(String[].class, jsonParser)).toList());
                case "registrationTimestamp" -> parser.next().Long().ifPresent(builder::registrationTimestamp);
                case "pets" -> Stream.of(ctx.deserialize(Pet[].class, jsonParser)).forEach(builder::pet);
                case "income" -> parser.next().BigDecimal().ifPresent(builder::income);
            }
        }
        return builder.build();
    }
}
