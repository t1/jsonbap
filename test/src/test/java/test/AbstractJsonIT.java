package test;

import com.github.t1.jsonbap.test.Address;
import com.github.t1.jsonbap.test.Cat;
import com.github.t1.jsonbap.test.Dog;
import com.github.t1.jsonbap.test.Person;
import jakarta.json.Json;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static jakarta.json.stream.JsonGenerator.PRETTY_PRINTING;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.BDDAssertions.then;

abstract class AbstractJsonIT {
    private static final int N = 10;
    public static final List<Person> DATA = IntStream.range(0, N)
            .mapToObj(AbstractJsonIT::person)
            .collect(toList());
    // narrow no-break space; used in French numbers as thousands-separator in JDK 13+
    protected static final char NNBSP = '\u202f';

    protected static String repeatedJson(boolean nullValues) {
        return IntStream.range(0, N).mapToObj(i -> json(i, nullValues))
                .collect(joining(",", "[", "]"));
    }

    static String json(int i, boolean nullValues) {
        return "{\"address\":" + addressJson(i, nullValues) + "," +
               "\"age\":" + (12 + i) + "," +
               "\"averageScore\":" + (0.123d * i) + "," +
               "\"firstName\":\"Jane-" + i + "\"," +
               (nullValues ? "\"formerAddress\":null," : "") +
               "\"income\":\"123" + NNBSP + "456" + NNBSP + "789,01\"," +
               "\"lastName\":\"Doe-" + i + "\"," +
               "\"member\":" + (i % 2 == 0) + "," +
               "\"pets\":[" +
                /**/"{\"@type\":\"cat\",\"isCat\":true,\"name\":\"foo-" + i + "\"}," +
                /**/"{\"@type\":\"cat\",\"isCat\":true,\"name\":\"bar-" + i + "\"}," +
                /**/"{\"@type\":\"dog\",\"isDog\":true,\"name\":\"baz-" + i + "\"}" +
               "]," +
               "\"registrationTimestamp\":" + (i + 10000000000L) + "," +
               "\"roles\":[\"role-1\",\"role-...\",\"role-" + i + "\"]}";
    }

    static String addressJson(int i, boolean nullValues) {
        return "{" +
                /**/"\"city\":\"Somewhere-" + i + "\"," +
                /**/(nullValues ? "\"country\":null," : "") +
                /**/(nullValues ? "\"state\":null," : "") +
                /**/"\"street\":\"" + (12000 + i) + " Main Street\"," +
                /**/"\"zip\":" + (50000 + i) +
               "}";
    }

    static Person person(int i) {
        return Person.builder()
                .firstName("Jane-" + i)
                .lastName("Doe-" + i)
                .age(12 + i)
                .averageScore(0.123d * i)
                .address(address(i))
                .formerAddress(null)
                .member(i % 2 == 0)
                .registrationTimestamp(i + 10000000000L)
                .roles(List.of("role-1", "role-...", "role-" + i))
                .pet(new Cat("foo-" + i))
                .pet(new Cat("bar-" + i))
                .pet(new Dog("baz-" + i))
                .income(BigDecimal.valueOf(123_456_789.01))
                .build();
    }

    static Address address(int i) {
        return Address.builder()
                .street((12000 + i) + " Main Street")
                .zip(50000 + i)
                .city("Somewhere-" + i)
                .build();
    }

    abstract String toJson(@SuppressWarnings("SameParameterValue") Object object);

    @Test
    void shouldSerializeListOfPerson() {
        var json = toJson(DATA);

        then(json).isEqualTo(repeatedJson(false));
    }

    static String prettyPrint(String json) {
        var jsonValue = Json.createReader(new StringReader(json)).readValue();
        var out = new StringWriter();
        var factory = Json.createGeneratorFactory(Map.of(PRETTY_PRINTING, true));
        try (var gen = factory.createGenerator(out)) {
            gen.write(jsonValue);
        }
        return out.toString();
    }


    abstract <T> T fromJson(String json, Type type);

    @Test void shouldDeserializeAddress() {
        var person = fromJson(addressJson(0, false), Address.class);

        then(person).usingRecursiveComparison().isEqualTo(address(0));
    }

    @Test void shouldDeserializePerson() {
        var person = fromJson(cheat(json(0, false)), Person.class);

        then(person).usingRecursiveComparison().isEqualTo(cheat(person(0)));
    }

    @Test void shouldDeserializeListOfPerson() throws Exception {
        var list = fromJson(cheat(repeatedJson(false)), AbstractJsonIT.class.getField("DATA").getGenericType());

        then(list).usingRecursiveComparison().isEqualTo(DATA.stream().map(this::cheat).toList());
    }

    @Test void shouldDeserializePersonWithNullValues() {
        var person = fromJson("{" +
                              "\"firstName\":null," +
                              "\"lastName\":null," +
                              "\"address\":null," +
                              "\"formerAddress\":null," +
                              //"\"pets\":null," +
                              //"\"roles\":null," +
                              "\"income\":null" +
                              "}", Person.class);

        then(person).usingRecursiveComparison().isEqualTo(cheat(new Person()));
    }

    String cheat(String json) {
        return json
                .replaceAll("\"pets\":\\[.*?],", "")
                .replaceAll(" ", "").replaceAll("789,01", "789.01");
    }

    Person cheat(Person person) {return person;}
}
