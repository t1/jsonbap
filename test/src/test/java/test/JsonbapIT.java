package test;

import com.github.t1.jsonbap.api.Bindable;
import com.github.t1.jsonbap.impl.ApJsonbProvider;
import com.github.t1.jsonbap.test.Person;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.annotation.JsonbNillable;
import jakarta.json.bind.spi.JsonbProvider;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.CASE_INSENSITIVE;
import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.LOWER_CASE_WITH_DASHES;
import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.LOWER_CASE_WITH_UNDERSCORES;
import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.UPPER_CAMEL_CASE;
import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.UPPER_CAMEL_CASE_WITH_SPACES;
import static com.github.t1.jsonbap.api.Bindable.PropertyNamingStrategyEnum.UPPER_CASE_WITH_UNDERSCORES;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.BDDAssertions.then;
import static test.JsonbIT.TestConfig.FORMATTING;

public class JsonbapIT extends JsonbIT {
    // this extra effort is for the performance when doing performance tests: we want every variant to be initiated only once
    private static final Class<? extends JsonbProvider> JSONB_PROVIDER = ApJsonbProvider.class;
    private static final Map<TestConfig, Jsonb> JSONB_MAP = TestConfig.stream()
            .collect(toMap(identity(), testConfig -> builder(JSONB_PROVIDER, testConfig).build()));

    @Override protected Jsonb jsonb(TestConfig testConfig) {return JSONB_MAP.get(testConfig);}


    @Bindable(propertyNamingStrategy = LOWER_CASE_WITH_DASHES)
    @SuppressWarnings("unused")
    public static class KebabCaseType {
        public String getFirstName() {return "first";}

        public String lastName = "last";
    }

    @Test void shouldSerializeWithKebabCase() throws Exception {
        try (var jsonb = jsonb()) {

            var json = jsonb.toJson(new KebabCaseType());

            then(json).isEqualTo("""
                    {
                        "first-name": "first",
                        "last-name": "last"
                    }""");
        }
    }


    @Bindable(propertyNamingStrategy = LOWER_CASE_WITH_UNDERSCORES)
    @SuppressWarnings("unused")
    public static class LowerSnakeCaseType {
        public String getFirstName() {return "first";}

        public String lastName = "last";
    }

    @Test void shouldSerializeWithLowerSnakeCase() throws Exception {
        try (var jsonb = jsonb()) {

            var json = jsonb.toJson(new LowerSnakeCaseType());

            then(json).isEqualTo("""
                    {
                        "first_name": "first",
                        "last_name": "last"
                    }""");
        }
    }


    @Bindable(propertyNamingStrategy = UPPER_CASE_WITH_UNDERSCORES)
    @SuppressWarnings("unused")
    public static class UpperSnakeCaseType {
        public String getFirstName() {return "first";}

        public String lastName = "last";
    }

    @Test void shouldSerializeWithUpperSnakeCase() throws Exception {
        try (var jsonb = jsonb()) {

            var json = jsonb.toJson(new UpperSnakeCaseType());

            then(json).isEqualTo("""
                    {
                        "FIRST_NAME": "first",
                        "LAST_NAME": "last"
                    }""");
        }
    }


    @Bindable(propertyNamingStrategy = UPPER_CAMEL_CASE)
    @SuppressWarnings("unused")
    public static class UpperCamelCaseType {
        public String getFirstName() {return "first";}

        public String lastName = "last";
    }

    @Test void shouldSerializeWithUpperCamelCase() throws Exception {
        try (var jsonb = jsonb()) {

            var json = jsonb.toJson(new UpperCamelCaseType());

            then(json).isEqualTo("""
                    {
                        "FirstName": "first",
                        "LastName": "last"
                    }""");
        }
    }


    @Bindable(propertyNamingStrategy = UPPER_CAMEL_CASE_WITH_SPACES)
    @SuppressWarnings("unused")
    public static class TitleCaseType {
        public String getFirstName() {return "first";}

        public String lastName = "last";
    }

    @Test void shouldSerializeWithTitleCase() throws Exception {
        try (var jsonb = jsonb()) {

            var json = jsonb.toJson(new TitleCaseType());

            then(json).isEqualTo("""
                    {
                        "First Name": "first",
                        "Last Name": "last"
                    }""");
        }
    }


    @Bindable(propertyNamingStrategy = CASE_INSENSITIVE)
    @SuppressWarnings("unused")
    public static class CaseInsensitiveType {
        public String getFirstName() {return "first";}

        public String lastName = "last";
    }

    @Test void shouldSerializeWithCaseInsensitive() throws Exception {
        try (var jsonb = jsonb()) {

            var json = jsonb.toJson(new CaseInsensitiveType());

            then(json).isEqualTo("""
                    {
                        "firstName": "first",
                        "lastName": "last"
                    }""");
        }
    }


    @Bindable
    @SuppressWarnings("unused")
    @JsonbNillable
    public static class NillableType {
        public String getFirstName() {return null;}

        public String lastName = null;
    }

    @Test void shouldSerializeNillable() throws Exception {
        try (var jsonb = jsonb(FORMATTING)) {

            var json = jsonb.toJson(new NillableType());

            then(json).isEqualTo("""
                    {
                        "firstName": null,
                        "lastName": null
                    }""");
        }
    }

    @Disabled("not yet implemented")
    @Test void shouldSerializeInvisibleElements() {
    }

    @Disabled("not yet implemented")
    @Test void shouldDeserializeListOfPerson() {
    }

    @Override String cheat(String json) {
        return super.cheat(json)
                .replaceAll(",\"roles\":\\[.*?]", "");
    }

    Person cheat(Person person) {
        return super.cheat(person)
                .withPets(List.of())
                .withRoles(null);
    }
}
