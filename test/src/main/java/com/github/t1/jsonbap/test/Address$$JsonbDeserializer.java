package com.github.t1.jsonbap.test;

import com.github.t1.jsonbap.runtime.ParserHelper;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;

import static jakarta.json.stream.JsonParser.Event;

@SuppressWarnings("unused")
public class Address$$JsonbDeserializer implements JsonbDeserializer<Address> {
    @Override public Address deserialize(JsonParser jsonParser, DeserializationContext ctx, Type rtType) {
        var parser = new ParserHelper(jsonParser);
        if (parser.is(Event.VALUE_NULL)) return null;
        var address = Address.builder();
        parser.assume(Event.START_OBJECT);
        while (parser.next().is(Event.KEY_NAME)) {
            switch (parser.StringAndNext()) {
                case "city" -> parser.readString().ifPresent(address::city);
                case "country" -> parser.readString().ifPresent(address::country);
                case "state" -> parser.readString().ifPresent(address::state);
                case "street" -> parser.readString().ifPresent(address::street);
                case "zip" -> parser.readInteger().ifPresent(address::zip);
            }
        }
        parser.assume(Event.END_OBJECT);
        return address.build();
    }
}
