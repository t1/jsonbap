package test;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.spi.JsonbProvider;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

abstract class JsonbIT extends AbstractJsonIT {
    protected static Jsonb builder(Class<? extends JsonbProvider> provider, TestConfig testConfig) {
        return JsonbBuilder.newBuilder(provider.getName())
                .withConfig(testConfig.toJsonConfig())
                .build();
    }

    @Override String toJson(Object object) {
        return toJson(object, new TestConfig(false, false));
    }

    @Test void shouldSerializeFormatted() {
        var json = toJson(DATA, new TestConfig(true, false));

        then(json).isEqualTo(prettyPrint(repeatedJson(false)));
    }

    @Test void shouldSerializeNullValues() {
        var json = toJson(DATA, new TestConfig(true, true));

        then(json).isEqualTo(prettyPrint(repeatedJson(true)));
    }

    @Test void shouldSerializeFormattedNullValues() {
        var json = toJson(DATA, new TestConfig(true, true));

        then(json).isEqualTo(prettyPrint(repeatedJson(true)));
    }

    @Test void shouldSerializeWithType() throws Exception {
        String json;
        try(var jsonb = jsonb(new TestConfig(false, false))) {

            json = jsonb.toJson(DATA, List.class);
        }

        then(json).isEqualTo(repeatedJson(false));
    }

    @Test void shouldSerializeToWriter() throws Exception {
        var writer = new StringWriter();
        try(var jsonb = jsonb(new TestConfig(false, false))) {

            jsonb.toJson(DATA, writer);
        }

        then(writer.toString()).isEqualTo(repeatedJson(false));
    }

    @Test void shouldSerializeToWriterWithType() throws Exception {
        var writer = new StringWriter();
        try(var jsonb = jsonb(new TestConfig(false, false))) {

            jsonb.toJson(DATA, List.class, writer);
        }

        then(writer.toString()).isEqualTo(repeatedJson(false));
    }

    @Test void shouldSerializeToOutputStream() throws Exception {
        var outputStream = new ByteArrayOutputStream();
        try(var jsonb = jsonb(new TestConfig(false, false))) {

            jsonb.toJson(DATA, outputStream);
        }

        then(outputStream.toString()).isEqualTo(repeatedJson(false));
    }

    @Test void shouldSerializeToOutputStreamWithType() throws Exception {
        var outputStream = new ByteArrayOutputStream();
        try(var jsonb = jsonb(new TestConfig(false, false))) {

            jsonb.toJson(DATA, List.class, outputStream);
        }

        then(outputStream.toString()).isEqualTo(repeatedJson(false));
    }

    private String toJson(Object object, TestConfig testConfig) {
        try (var jsonb = jsonb(testConfig)) {
            return jsonb.toJson(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Jsonb jsonb(TestConfig testConfig);

    protected record TestConfig(boolean formatted, boolean nullValues) {
        public static Stream<TestConfig> stream() {
            return Stream.of(
                    new TestConfig(false, false),
                    new TestConfig(true, false),
                    new TestConfig(false, true),
                    new TestConfig(true, true));
        }

        public JsonbConfig toJsonConfig() {
            return new JsonbConfig().withFormatting(formatted).withNullValues(nullValues);
        }
    }
}
