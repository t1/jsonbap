package test;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.spi.JsonbProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

abstract class JsonbIT extends AbstractJsonIT {
    protected static Jsonb builder(Class<? extends JsonbProvider> provider, boolean formatted, boolean nullValues) {
        return JsonbBuilder.newBuilder(provider.getName())
                .withConfig(new JsonbConfig().withFormatting(formatted).withNullValues(nullValues))
                .build();
    }

    @Override String toJson(Object object) {return toJson(object, false, false);}

    @Test void shouldSerializeFormatted() {
        var json = toJson(DATA, true, false);

        then(json).isEqualTo(prettyPrint(prettyPrint(repeatedJson(false))));
    }

    @Test void shouldSerializeNullValues() {
        var json = toJson(DATA, true, true);

        then(json).isEqualTo(prettyPrint(prettyPrint(repeatedJson(true))));
    }

    @Test void shouldSerializeFormattedNullValues() {
        var json = toJson(DATA, true, true);

        then(json).isEqualTo(prettyPrint(prettyPrint(repeatedJson(true))));
    }

    private String toJson(Object object, boolean formatted, boolean nullValues) {
        try (var jsonb = jsonb(formatted, nullValues)) {
            return jsonb.toJson(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Jsonb jsonb(boolean formatted, boolean nullValues);
}
