package test;

import com.github.t1.jsonbap.api.Bindable;
import com.github.t1.jsonbap.test.Address;
import com.github.t1.jsonbap.test.Cat;
import com.github.t1.jsonbap.test.Dog;
import jakarta.json.Json;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.spi.JsonbProvider;
import jakarta.json.spi.JsonProvider;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static test.JsonbIT.Product.PRODUCT;
import static test.JsonbIT.TestConfig.FORMATTED;
import static test.JsonbIT.TestConfig.FORMATTED_AND_NULL_VALUES;
import static test.JsonbIT.TestConfig.PLAIN;

abstract class JsonbIT extends AbstractJsonIT {
    protected static JsonbBuilder builder(Class<? extends JsonbProvider> provider, TestConfig testConfig) {
        return JsonbBuilder.newBuilder(provider.getName())
                .withConfig(testConfig.toJsonbConfig())
                .withProvider(testConfig.toJsonpConfig());
    }

    protected record TestConfig(boolean formatted, boolean nullValues, boolean jsonpProvider) {
        static final TestConfig PLAIN = new TestConfig(false, false, false);
        static final TestConfig FORMATTED = new TestConfig(true, false, false);
        static final TestConfig NULL_VALUES = new TestConfig(false, true, false);
        static final TestConfig FORMATTED_AND_NULL_VALUES = new TestConfig(true, true, false);

        public static Stream<TestConfig> stream() {
            return Stream.of(PLAIN, FORMATTED, NULL_VALUES, FORMATTED_AND_NULL_VALUES);
        }

        public JsonProvider toJsonpConfig() {return jsonpProvider ? JsonProvider.provider() : null;}

        public JsonbConfig toJsonbConfig() {
            return new JsonbConfig().withFormatting(formatted).withNullValues(nullValues);
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

    protected Jsonb jsonb() {return jsonb(FORMATTED_AND_NULL_VALUES);}

    @Test void shouldSerializeFormatted() {
        var json = toJson(DATA, FORMATTED);

        then(json).isEqualTo(prettyPrint(repeatedJson(false)));
    }

    @Test void shouldSerializeNullValues() {
        var json = toJson(DATA, FORMATTED_AND_NULL_VALUES);

        then(json).isEqualTo(prettyPrint(repeatedJson(true)));
    }

    @Test void shouldSerializeFormattedNullValues() {
        var json = toJson(DATA, FORMATTED_AND_NULL_VALUES);

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

    @Test void shouldSerializeNull() throws Exception {
        try (var jsonb = jsonb()) {

            var json = jsonb.toJson(null);

            then(json).isEqualTo("null");
        }
    }

    @Test void shouldSerializeJsonValues() throws Exception {
        try (var jsonb = jsonb()) {

            var json = jsonb.toJson(Json.createObjectBuilder()
                    .add("string", Json.createValue("foo"))
                    .add("int", Json.createValue(123))
                    .add("long", Json.createValue(123456789012345L))
                    .add("double", Json.createValue(12345.6789d))
                    .add("bigDecimal", Json.createValue(BigDecimal.valueOf(123.456d)))
                    .add("bigInteger", Json.createValue(BigInteger.TEN))
                    .add("array", Json.createArrayBuilder(List.of(1, 2, 3)))
                    .build());

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
    }

    @Test void shouldSerializeBigInteger() throws Exception {
        try (var jsonb = jsonb()) {

            var json = jsonb.toJson(new BigInteger("12345678901234567890"));

            then(json).isEqualTo("12345678901234567890");
        }
    }

    @Test void shouldSerializeBigDecimal() throws Exception {
        try (var jsonb = jsonb()) {

            var json = jsonb.toJson(new BigDecimal("123456789012345678901234567890123456789.123"));

            then(json).isEqualTo("123456789012345678901234567890123456789.123");
        }
    }

    @Test void shouldSerializeLong() throws Exception {
        try (var jsonb = jsonb()) {

            var json = jsonb.toJson(123456L);

            then(json).isEqualTo("123456");
        }
    }

    @Test void shouldSerializeDouble() throws Exception {
        try (var jsonb = jsonb()) {

            var json = jsonb.toJson(123.456d);

            then(json).isEqualTo("123.456");
        }
    }

    @Test void shouldSerializeBoolean() throws Exception {
        try (var jsonb = jsonb()) {

            var json = jsonb.toJson(true);

            then(json).isEqualTo("true");
        }
    }

    @Test void shouldSerializeListWithNullWithNullValues() throws Exception {
        try (var jsonb = jsonb()) {

            var people = new java.util.ArrayList<Address>();
            people.add(address(0));
            people.add(null);
            people.add(address(2));
            var json = jsonb.toJson(people);

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
    }

    @Test void shouldSerializeListWithNullNoNullValues() throws Exception {
        try (var jsonb = jsonb(FORMATTED)) {

            var people = new java.util.ArrayList<Address>();
            people.add(address(0));
            people.add(null);
            people.add(address(2));
            var json = jsonb.toJson(people);

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
    }

    @Test void shouldSerializeSubtypeCat() throws Exception {
        try (var jsonb = jsonb(PLAIN)) {

            var json = jsonb.toJson(new Cat());

            then(json).isEqualTo("{\"@type\":\"cat\",\"isCat\":true}");
        }
    }

    @Test void shouldSerializeSubtypeDog() throws Exception {
        try (var jsonb = jsonb(PLAIN)) {

            var json = jsonb.toJson(new Dog());

            then(json).isEqualTo("{\"@type\":\"dog\",\"isDog\":true}");
        }
    }

    @Test void shouldSerializeAnimalList() throws Exception {
        try (var jsonb = jsonb()) {

            var list = List.of(new Cat("tabula"), new Dog("rasa"));
            var json = jsonb.toJson(list);

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

    @Test void shouldSerializeField() throws Exception {
        try (var jsonb = jsonb(PLAIN)) {

            var json = jsonb.toJson(PRODUCT);

            then(json).isEqualTo("{\"description\":\"foo\",\"id\":123}");
        }
    }
}
