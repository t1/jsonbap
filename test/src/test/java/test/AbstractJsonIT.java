package test;

import com.github.t1.jsonbap.test.Address;
import com.github.t1.jsonbap.test.Person;
import jakarta.json.Json;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static jakarta.json.stream.JsonGenerator.PRETTY_PRINTING;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.BDDAssertions.then;

abstract class AbstractJsonIT {
    private static final int N = 5;
    protected static final List<Person> DATA = IntStream.range(0, N).mapToObj(AbstractJsonIT::person)
            .collect(toList());

    protected static String repeatedJson(boolean nullValues) {
        return IntStream.range(0, N).mapToObj(i -> json(i, nullValues))
                .collect(joining(",", "[", "]"));
    }

    static String json(int i, boolean nullValues) {
        return
                "{\"address\":{" +
                        /**/"\"city\":\"Somewhere-" + i + "\"," +
                        /**/(nullValues ? "\"country\":null," : "") +
                        /**/(nullValues ? "\"state\":null," : "") +
                        /**/"\"street\":\"" + (12000 + i) + " Main Street\"," +
                        /**/"\"zip\":" + (50000 + i) +
                "}," +
                "\"age\":" + (12 + i) + "," +
                "\"averageScore\":" + (0.123d * i) + "," +
                "\"firstName\":\"Jane-" + i + "\"," +
                (nullValues ? "\"formerAddress\":null," : "") +
                "\"lastName\":\"Doe-" + i + "\"," +
                "\"member\":" + (i % 2 == 0) + "," +
                "\"registrationTimestamp\":" + (i + 10000000000L) + "," +
                "\"roles\":[\"role-1\",\"role-...\",\"role-" + i + "\"]}";
    }

    static Person person(int i) {
        return Person.builder()
                .firstName("Jane-" + i)
                .lastName("Doe-" + i)
                .age(12 + i)
                .averageScore(0.123d * i)
                .address(Address.builder()
                        .street((12000 + i) + " Main Street")
                        .zip(50000 + i)
                        .city("Somewhere-" + i)
                        .build())
                .formerAddress(null)
                .member(i % 2 == 0)
                .registrationTimestamp(i + 10000000000L)
                .roles(new ArrayList<>(List.of("role-1", "role-...", "role-" + i)))
                .build();
    }

    @Test
    void shouldSerialize() {
        var json = toJson(DATA);

        then(json).isEqualTo(repeatedJson(false));
    }

    abstract String toJson(@SuppressWarnings("SameParameterValue") Object object);

    static String prettyPrint(String json) {
        var jsonValue = Json.createReader(new StringReader(json)).readValue();
        var out = new StringWriter();
        var factory = Json.createGeneratorFactory(Map.of(PRETTY_PRINTING, true));
        try (var gen = factory.createGenerator(out)) {
            gen.write(jsonValue);
        }
        return out.toString();
    }
}
