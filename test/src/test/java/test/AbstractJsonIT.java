package test;

import com.github.t1.jsonbap.test.Address;
import com.github.t1.jsonbap.test.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.BDDAssertions.then;

abstract class AbstractJsonIT {
    private static final int N = 2;
    private static final Object DATA = IntStream.range(0, N - 1).mapToObj(AbstractJsonIT::person)
        .collect(toList());
    private static final String JSON = IntStream.range(0, N - 1).mapToObj(AbstractJsonIT::json)
        .collect(joining(",", "[", "]"));

    @Test
    void shouldSerialize() {
        var json = toJson(DATA);

        then(json).isEqualTo(JSON);
    }

    abstract String toJson(@SuppressWarnings("SameParameterValue") Object object);

    static String json(int i) {
        return
            "{\"address\":{" +
                /**/"\"city\":\"Somewhere-" + i + "\"," +
                /**/"\"street\":\"" + (12000 + i) + " Main Street\"," +
                /**/"\"zip\":" + (50000 + i) +
            "}," +
            "\"age\":" + (12 + i) + "," +
            "\"firstName\":\"Jane-" + i + "\"," +
            "\"lastName\":\"Doe-" + i + "\"," +
            "\"roles\":[\"role-1\",\"role-...\",\"role-" + i + "\"]}";
    }

    static Person person(int i) {
        return Person.builder()
            .firstName("Jane-" + i)
            .lastName("Doe-" + i)
            .age(12 + i)
            .address(Address.builder()
                .street((12000 + i) + " Main Street")
                .zip(50000 + i)
                .city("Somewhere-" + i)
                .build())
            .roles(new ArrayList<>(List.of("role-1", "role-...", "role-" + i)))
            .build();
    }
}
