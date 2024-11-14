package test;

import com.github.t1.jsonbap.api.Bindable;
import com.github.t1.jsonbap.runtime.TypeLiteral;
import com.github.t1.jsonbap.test.Address;
import com.github.t1.jsonbap.test.Cat;
import com.github.t1.jsonbap.test.Dog;
import jakarta.json.Json;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.annotation.JsonbVisibility;
import jakarta.json.bind.config.PropertyVisibilityStrategy;
import jakarta.json.bind.spi.JsonbProvider;
import jakarta.json.spi.JsonProvider;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.github.t1.jsonbap.runtime.TypeLiteral.genericType;
import static org.assertj.core.api.BDDAssertions.then;
import static test.JsonbIT.Invisibilities.InvisibilitiesStrategy;
import static test.JsonbIT.Product.PRODUCT;
import static test.JsonbIT.TestConfig.FORMATTING;
import static test.JsonbIT.TestConfig.FORMATTING_AND_NULL_VALUES;
import static test.JsonbIT.TestConfig.PLAIN;

abstract class JsonbIT extends AbstractJsonIT {
    protected static JsonbBuilder builder(Class<? extends JsonbProvider> provider, TestConfig testConfig) {
        return JsonbBuilder.newBuilder(provider.getName())
                .withConfig(testConfig.toJsonbConfig())
                .withProvider(testConfig.toJsonpConfig());
    }

    protected record TestConfig(boolean formatting, boolean nullValues, boolean jsonpProvider) {
        static final TestConfig PLAIN = new TestConfig(false, false, false);
        static final TestConfig FORMATTING = new TestConfig(true, false, false);
        static final TestConfig NULL_VALUES = new TestConfig(false, true, false);
        static final TestConfig FORMATTING_AND_NULL_VALUES = new TestConfig(true, true, false);

        public static Stream<TestConfig> stream() {
            return Stream.of(PLAIN, FORMATTING, NULL_VALUES, FORMATTING_AND_NULL_VALUES);
        }

        public JsonProvider toJsonpConfig() {return jsonpProvider ? JsonProvider.provider() : null;}

        public JsonbConfig toJsonbConfig() {
            return new JsonbConfig().withFormatting(formatting).withNullValues(nullValues);
        }
    }

    @Override String toJson(Object object) {
        return toJson(object, PLAIN);
    }

    private String toJson(Object object, @NonNull TestConfig testConfig) {
        try (var jsonb = jsonb(testConfig)) {
            return jsonb.toJson(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Jsonb jsonb(TestConfig testConfig);

    protected Jsonb jsonb() {return jsonb(FORMATTING_AND_NULL_VALUES);}

    @Test void shouldSerializeFormatting() {
        var json = toJson(DATA, FORMATTING);

        then(json).isEqualTo(prettyPrint(repeatedJson(false)));
    }

    @Test void shouldSerializeNullValues() {
        var json = toJson(DATA, FORMATTING_AND_NULL_VALUES);

        then(json).isEqualTo(prettyPrint(repeatedJson(true)));
    }

    @Test void shouldSerializeFormattingNullValues() {
        var json = toJson(DATA, FORMATTING_AND_NULL_VALUES);

        then(json).isEqualTo(prettyPrint(repeatedJson(true)));
    }

    @Test void shouldSerializeWithType() throws Exception {
        String json;
        try (var jsonb = jsonb(PLAIN)) {

            json = jsonb.toJson(DATA, List.class);
        }

        then(json).isEqualTo(repeatedJson(false));
    }

    @Test void shouldSerializeToWriter() throws Exception {
        var writer = new StringWriter();
        try (var jsonb = jsonb(PLAIN)) {

            jsonb.toJson(DATA, writer);
        }

        then(writer.toString()).isEqualTo(repeatedJson(false));
    }

    @Test void shouldSerializeToWriterWithType() throws Exception {
        var writer = new StringWriter();
        try (var jsonb = jsonb(PLAIN)) {

            jsonb.toJson(DATA, List.class, writer);
        }

        then(writer.toString()).isEqualTo(repeatedJson(false));
    }

    @Test void shouldSerializeToOutputStream() throws Exception {
        var outputStream = new ByteArrayOutputStream();
        try (var jsonb = jsonb(PLAIN)) {

            jsonb.toJson(DATA, outputStream);
        }

        then(outputStream.toString()).isEqualTo(repeatedJson(false));
    }

    @Test void shouldSerializeToOutputStreamWithType() throws Exception {
        var outputStream = new ByteArrayOutputStream();
        try (var jsonb = jsonb(PLAIN)) {

            jsonb.toJson(DATA, List.class, outputStream);
        }

        then(outputStream.toString()).isEqualTo(repeatedJson(false));
    }

    @Test void shouldSerializeNull() {
        var json = toJson(null);

        then(json).isEqualTo("null");
    }

    @Test void shouldSerializeJsonValues() {
        var json = toJson(Json.createObjectBuilder()
                        .add("string", Json.createValue("foo"))
                        .add("int", Json.createValue(123))
                        .add("long", Json.createValue(123456789012345L))
                        .add("double", Json.createValue(12345.6789d))
                        .add("bigDecimal", Json.createValue(BigDecimal.valueOf(123.456d)))
                        .add("bigInteger", Json.createValue(BigInteger.TEN))
                        .add("array", Json.createArrayBuilder(List.of(1, 2, 3)))
                        .build(),
                FORMATTING);

        then(json).isEqualTo("""
                {
                    "string": "foo",
                    "int": 123,
                    "long": 123456789012345,
                    "double": 12345.6789,
                    "bigDecimal": 123.456,
                    "bigInteger": 10,
                    "array": [
                        1,
                        2,
                        3
                    ]
                }""");
    }

    @Test void shouldSerializeBigInteger() {
        var json = toJson(new BigInteger("12345678901234567890"));

        then(json).isEqualTo("12345678901234567890");
    }

    @Test void shouldSerializeBigDecimal() {
        var json = toJson(new BigDecimal("123456789012345678901234567890123456789.123"));

        then(json).isEqualTo("123456789012345678901234567890123456789.123");
    }

    @Test void shouldSerializeLong() {
        var json = toJson(123456L);

        then(json).isEqualTo("123456");
    }

    @Test void shouldSerializeDouble() {
        var json = toJson(123.456d);

        then(json).isEqualTo("123.456");
    }

    @Test void shouldSerializeBoolean() {
        var json = toJson(true);

        then(json).isEqualTo("true");
    }

    @Test void shouldSerializeListWithNullWithNullValues() {
        var people = new java.util.ArrayList<Address>();
        people.add(address(0));
        people.add(null);
        people.add(address(2));
        var json = toJson(people, FORMATTING_AND_NULL_VALUES);

        then(json).isEqualTo("""
                [
                    {
                        "city": "Somewhere-0",
                        "country": null,
                        "state": null,
                        "street": "12000 Main Street",
                        "zip": 50000
                    },
                    null,
                    {
                        "city": "Somewhere-2",
                        "country": null,
                        "state": null,
                        "street": "12002 Main Street",
                        "zip": 50002
                    }
                ]""");
    }

    @Test void shouldSerializeListWithNullNoNullValues() {
        var people = new java.util.ArrayList<Address>();
        people.add(address(0));
        people.add(null);
        people.add(address(2));
        var json = toJson(people, FORMATTING);

        then(json).isEqualTo("""
                [
                    {
                        "city": "Somewhere-0",
                        "street": "12000 Main Street",
                        "zip": 50000
                    },
                    null,
                    {
                        "city": "Somewhere-2",
                        "street": "12002 Main Street",
                        "zip": 50002
                    }
                ]""");
    }

    @Test void shouldSerializeSubtypeCat() {
        var json = toJson(new Cat());

        then(json).isEqualTo("{\"@type\":\"cat\",\"isCat\":true}");
    }

    @Test void shouldSerializeSubtypeDog() {
        var json = toJson(new Dog());

        then(json).isEqualTo("{\"@type\":\"dog\",\"isDog\":true}");
    }

    @Test void shouldSerializeAnimalList() {
        var list = List.of(new Cat("tabula"), new Dog("rasa"));
        var json = toJson(list, FORMATTING);

        then(json).isEqualTo("""
                [
                    {
                        "@type": "cat",
                        "isCat": true,
                        "name": "tabula"
                    },
                    {
                        "@type": "dog",
                        "isDog": true,
                        "name": "rasa"
                    }
                ]""");
    }

    @Bindable
    @SuperBuilder
    @NoArgsConstructor
    @SuppressWarnings("unused")
    public static class Product {
        public static final Product PRODUCT = Product.builder()
                .id(123)
                .description("foo")
                .hiddenPackagePrivate(true)
                .hiddenProtected(true)
                .hiddenPrivate(true)
                .build();

        public long id;
        public String description;
        boolean hiddenPackagePrivate;
        protected boolean hiddenProtected;
        private boolean hiddenPrivate;
    }

    @Test void shouldSerializeFields() {
        var json = toJson(PRODUCT);

        then(json).isEqualTo("{\"description\":\"foo\",\"id\":123}");
    }

    @Bindable
    @SuppressWarnings("unused")
    public static class PropertyVariations {
        public String foo = "field";

        public String getFoo() {return "getter";}
    }

    @Test void shouldSerializePropertyVariations() {
        var json = toJson(new PropertyVariations());

        then(json).isEqualTo("{\"foo\":\"getter\"}");
    }

    @Test void shouldSerializeNullFormattedNumber() {
        var json = toJson(person(1).withAddress(null).withPets(null).withIncome(null));

        then(json).isEqualTo("{" +
                             "\"age\":13," +
                             "\"averageScore\":0.123," +
                             "\"firstName\":\"Jane-1\"," +
                             "\"lastName\":\"Doe-1\"," +
                             "\"member\":false," +
                             "\"registrationTimestamp\":10000000001," +
                             "\"roles\":[\"role-1\",\"role-...\",\"role-1\"]}");
    }


    @Bindable
    @SuppressWarnings("unused")
    @JsonbVisibility(InvisibilitiesStrategy.class)
    public static class Invisibilities {
        public String invisibleField = "¬F";

        public String getInvisibleGetter() {return "¬G";}

        public String visibleField = "F";

        public String getVisibleGetter() {return "G";}

        public static class InvisibilitiesStrategy implements PropertyVisibilityStrategy {
            @Override public boolean isVisible(Field field) {return field.getName().startsWith("visible");}

            @Override public boolean isVisible(Method method) {return method.getName().startsWith("getVisible");}
        }
    }

    @Test void shouldSerializeInvisibleElements() {
        var json = toJson(new Invisibilities());

        then(json).isEqualTo("{\"visibleField\":\"F\",\"visibleGetter\":\"G\"}");
    }

    @Test void shouldSerializeOptionalListWithoutNullValues() {
        var json = toJson(List.of(
                        Optional.of("one"),
                        Optional.empty(),
                        Optional.of("three")),
                FORMATTING);

        then(json).isEqualTo("""
                [
                    "one",
                    null,
                    "three"
                ]""");
    }

    @Test void shouldSerializeOptionalListWithNullValues() {
        var json = toJson(List.of(
                        Optional.of("one"),
                        Optional.empty(),
                        Optional.of("three")),
                FORMATTING_AND_NULL_VALUES);

        then(json).isEqualTo("""
                [
                    "one",
                    null,
                    "three"
                ]""");
    }


    @Override <T> T fromJson(String json, Type type) {
        try (var jsonb = jsonb(PLAIN)) {
            return jsonb.fromJson(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test void shouldDeserializeOptionalListWithNullValues() {
        var list = fromJson("""
                [
                    "one",
                    null,
                    "three"
                ]""", genericType(new TypeLiteral<List<Optional<String>>>() {}));

        then(list).isEqualTo(List.of(
                Optional.of("one"),
                Optional.empty(),
                Optional.of("three")));
    }
}
