package test;

import com.github.t1.jsonbap.runtime.NullWriter;
import com.github.t1.jsonbap.runtime.ParserHelper;
import com.github.t1.jsonbap.test.Address;
import com.github.t1.jsonbap.test.Address$$JsonbDeserializer;
import com.github.t1.jsonbap.test.Cat;
import com.github.t1.jsonbap.test.Dog;
import com.github.t1.jsonbap.test.Person;
import com.github.t1.jsonbap.test.Pet;
import jakarta.json.Json;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static jakarta.json.stream.JsonParser.Event.KEY_NAME;
import static jakarta.json.stream.JsonParser.Event.START_OBJECT;
import static org.assertj.core.api.BDDAssertions.then;

@Slf4j
public class JsonPIT extends AbstractJsonIT {
    @Override String toJson(Object object) {
        @SuppressWarnings("unchecked")
        List<Person> person = (List<Person>) object;
        var out = new StringWriter();
        var generator = Json.createGenerator(out);
        toJson(person, generator);
        generator.flush();
        return out.toString();
    }

    private static void toJson(List<Person> object, JsonGenerator out) {
        out.writeStartArray();
        for (Person item : object) {
            if (item != null) {
                toJson(item, out);
            }
        }
        out.writeEnd();
    }

    private static void toJson(Person object, JsonGenerator out) {
        out.writeStartObject();
        if (object.getAddress() != null) {
            out.writeStartObject("address");
            toJson(object.getAddress(), out);
            out.writeEnd();
        }
        if (object.getFormerAddress() != null) {
            out.writeStartObject("formerAddress");
            toJson(object.getFormerAddress(), out);
            out.writeEnd();
        }
        out.write("age", object.getAge());
        out.write("averageScore", object.getAverageScore());
        if (object.getFirstName() != null) {
            out.write("firstName", object.getFirstName());
        }
        out.write("income", Optional.ofNullable(object.getIncome())
                .map(NumberFormat.getInstance(Locale.of("fr"))::format)
                .orElse(null));
        if (object.getLastName() != null) {
            out.write("lastName", object.getLastName());
        }
        out.write("member", object.isMember());
        if (object.getPets() != null) {
            out.writeStartArray("pets");
            for (Pet pet : object.getPets()) {
                if (pet != null) {
                    toJson(pet, out);
                }
            }
            out.writeEnd();
        }
        out.write("registrationTimestamp", object.getRegistrationTimestamp());
        if (object.getRoles() != null) {
            out.writeStartArray("roles");
            for (String role : object.getRoles()) {
                if (role != null) {
                    out.write(role);
                }
            }
            out.writeEnd();
        }
        out.writeEnd();
    }

    private static void toJson(Address object, JsonGenerator out) {
        if (object.getCity() != null) {
            out.write("city", object.getCity());
        }
        if (object.getCountry() != null) {
            out.write("country", object.getCountry());
        }
        if (object.getState() != null) {
            out.write("state", object.getState());
        }
        if (object.getStreet() != null) {
            out.write("street", object.getStreet());
        }
        if (object.getZip() != null) {
            out.write("zip", object.getZip());
        }
    }

    private static void toJson(Pet object, JsonGenerator out) {
        if (object instanceof Cat cat) toJson(cat, out);
        else if (object instanceof Dog dog) toJson(dog, out);
        else throw new IllegalArgumentException("unknown type: " + object);
    }

    private static void toJson(Cat object, JsonGenerator out) {
        out.writeStartObject();
        out.write("@type", "cat");
        out.write("isCat", object.getIsCat());
        if (object.getName() != null) {
            out.write("name", object.getName());
        }
        out.writeEnd();
    }

    private static void toJson(Dog object, JsonGenerator out) {
        out.writeStartObject();
        out.write("@type", "dog");
        out.write("isDog", object.getIsDog());
        if (object.getName() != null) {
            out.write("name", object.getName());
        }
        out.writeEnd();
    }

    @Test
    void shouldSerializeNil() {
        var person = Person.builder()
                .firstName("Jane")
                .lastName(null)
                .build();

        var json = new StringWriter();
        try (var out = Json.createGenerator(json)) {
            out.writeStartObject();
            NullWriter.writeNillable("firstName", person.getFirstName(), out, null);
            NullWriter.writeNillable("lastName", person.getLastName(), out, null);
            out.writeEnd();
        }

        then(json.toString()).isEqualTo(
                "{" +
                "\"firstName\":\"Jane\"," +
                "\"lastName\":null" +
                "}");
    }

    @Override
    @SuppressWarnings("unchecked")
    <T> T fromJson(String json, Class<T> type) {
        var parser = Json.createParser(new StringReader(json));
        if (type == Address.class) return (T) parseAddress(parser);
        if (type == Person.class) return (T) parsePerson(parser);
        throw new UnsupportedOperationException("unknown type: " + type);
    }

    private Address parseAddress(JsonParser jsonParser) {
        var parser = new ParserHelper(jsonParser);
        var builder = Address.builder();
        parser.next().assume(START_OBJECT);
        while (parser.next().is(KEY_NAME)) {
            switch (parser.getString()) {
                case "city" -> parser.next().String().ifPresent(builder::city);
                case "country" -> parser.next().String().ifPresent(builder::country);
                case "state" -> parser.next().String().ifPresent(builder::state);
                case "street" -> parser.next().String().ifPresent(builder::street);
                case "zip" -> parser.next().Integer().ifPresent(builder::zip);
                case String key -> log.debug("unknown key: {}", key);
            }
        }
        return builder.build();
    }

    private Person parsePerson(JsonParser jsonParser) {
        var parser = new ParserHelper(jsonParser);
        var builder = Person.builder();
        parser.next().assume(START_OBJECT);
        while (parser.next().is(KEY_NAME)) {
            switch (parser.getString()) {
                case "firstName" -> parser.next().String().ifPresent(builder::firstName);
                case "lastName" -> parser.next().String().ifPresent(builder::lastName);
                case "age" -> parser.next().Integer().ifPresent(builder::age);
                case "averageScore" -> parser.next().Double().ifPresent(builder::averageScore);
                case "address" ->
                        builder.address(new Address$$JsonbDeserializer().deserialize(jsonParser, null, Address.class));
                case "formerAddress" ->
                        builder.formerAddress(new Address$$JsonbDeserializer().deserialize(jsonParser, null, Address.class));
                case "member" -> parser.next().Boolean().ifPresent(builder::member);
                //case "roles" -> builder.roles(Stream.of(ctx.deserialize(String[].class, jsonParser)).toList());
                case "registrationTimestamp" -> parser.next().Long().ifPresent(builder::registrationTimestamp);
                //case "pets" -> Stream.of(ctx.deserialize(Pet[].class, jsonParser)).forEach(builder::pet);
                case "income" -> parser.next().BigDecimal().ifPresent(builder::income);
            }
        }
        return builder.build();
    }
}
