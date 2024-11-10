package com.github.t1.jsonbap.test;

import com.github.t1.jsonbap.runtime.ParserHelper;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;

import static jakarta.json.stream.JsonParser.Event.KEY_NAME;
import static jakarta.json.stream.JsonParser.Event.START_OBJECT;
import static jakarta.json.stream.JsonParser.Event.VALUE_NULL;

public class Address$$JsonbDeserializer implements JsonbDeserializer<Address> {
    @Override public Address deserialize(JsonParser jsonParser, DeserializationContext ctx, Type rtType) {
        var parser = new ParserHelper(jsonParser);
        if (parser.next().is(VALUE_NULL)) return null;
        var address = Address.builder();
        parser.assume(START_OBJECT);
        while (parser.next().is(KEY_NAME)) {
            switch (parser.getString()) {
                case "city" -> parser.next().String().ifPresent(address::city);
                case "country" -> parser.next().String().ifPresent(address::country);
                case "state" -> parser.next().String().ifPresent(address::state);
                case "street" -> parser.next().String().ifPresent(address::street);
                case "zip" -> parser.next().Integer().ifPresent(address::zip);
            }
        }
        return address.build();
    }
}
