package test;

import com.github.t1.jsonbap.runtime.FluentParser;
import com.github.t1.jsonbap.runtime.NullWriter;
import com.github.t1.jsonbap.test.Address;
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
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static jakarta.json.stream.JsonParser.Event.END_ARRAY;
import static jakarta.json.stream.JsonParser.Event.END_OBJECT;
import static jakarta.json.stream.JsonParser.Event.KEY_NAME;
import static jakarta.json.stream.JsonParser.Event.START_ARRAY;
import static jakarta.json.stream.JsonParser.Event.START_OBJECT;
import static jakarta.json.stream.JsonParser.Event.VALUE_NULL;
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
    <T> T fromJson(String json, Type type) {
        var parser = Json.createParser(new StringReader(json));
        if (type == Address.class) return (T) parseAddress(parser);
        if (type == Person.class) return (T) parsePerson(parser);
        if ("java.util.List<com.github.t1.jsonbap.test.Person>".equals(type.toString()))
            return (T) parseListOfPerson(parser);
        throw new UnsupportedOperationException("unknown type: " + type);
    }

    private Address parseAddress(JsonParser jsonParser) {
        var parser = new FluentParser(jsonParser);
        if (parser.is(VALUE_NULL)) return null;
        var builder = Address.builder();
        parser.assume(START_OBJECT);
        while (parser.next().is(KEY_NAME)) {
            switch (parser.StringAndNext()) {
                case "city" -> parser.readString().ifPresent(builder::city);
                case "country" -> parser.readString().ifPresent(builder::country);
                case "state" -> parser.readString().ifPresent(builder::state);
                case "street" -> parser.readString().ifPresent(builder::street);
                case "zip" -> parser.readInteger().ifPresent(builder::zip);
                case String key -> log.debug("unknown key: {}", key);
            }
        }
        parser.assume(END_OBJECT);
        return builder.build();
    }

    private Person parsePerson(JsonParser jsonParser) {
        var parser = new FluentParser(jsonParser);
        if (parser.is(VALUE_NULL)) return null;
        var builder = Person.builder();
        parser.assume(START_OBJECT);
        while (parser.next().is(KEY_NAME)) {
            switch (parser.StringAndNext()) {
                case "firstName" -> parser.readString().ifPresent(builder::firstName);
                case "lastName" -> parser.readString().ifPresent(builder::lastName);
                case "age" -> parser.readInteger().ifPresent(builder::age);
                case "averageScore" -> parser.readDouble().ifPresent(builder::averageScore);
                case "address" -> builder.address(parseAddress(jsonParser));
                case "formerAddress" -> builder.formerAddress(parseAddress(jsonParser));
                case "member" -> parser.readBoolean().ifPresent(builder::member);
                case "roles" -> parser.assume(START_ARRAY).skipArray(); // TODO
                case "registrationTimestamp" -> parser.readLong().ifPresent(builder::registrationTimestamp);
                case "pets" -> parser.assume(START_ARRAY).skipArray(); // TODO
                case "income" -> parser.readBigDecimal().ifPresent(builder::income);
            }
        }
        parser.assume(END_OBJECT);
        return builder.build();
    }

    private List<Person> parseListOfPerson(JsonParser jsonParser) {
        var parser = new FluentParser(jsonParser);
        if (parser.is(VALUE_NULL)) return null;
        var list = new ArrayList<Person>();
        parser.assume(START_ARRAY);
        while (!parser.next().is(END_ARRAY)) {
            list.add(parsePerson(jsonParser));
        }
        return list;
    }

    @Override Person cheat(Person person) {
        return super.cheat(person).withPets(List.of()).withRoles(null);
    }
}
