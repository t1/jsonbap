package test;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.spi.JsonbProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

abstract class JsonbIT extends AbstractJsonIT {
    protected static Jsonb builder(Class<? extends JsonbProvider> provider, boolean formatted) {
        return JsonbBuilder.newBuilder(provider.getName())
                .withConfig(new JsonbConfig().withFormatting(formatted))
                .build();
    }

    @Override String toJson(Object object) {return toJson(object, false);}

    private String toJson(Object object, boolean formatted) {
        try (var jsonb = jsonb(formatted)) {
            return jsonb.toJson(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Jsonb jsonb(boolean formatted) {return formatted ? jsonbFormatted() : jsonb();}

    protected abstract Jsonb jsonb();

    protected abstract Jsonb jsonbFormatted();

    @Test
    void shouldSerializeFormatted() {
        var json = toJson(DATA, true);

        then(json).isEqualTo(prettyPrint(PRETTY_JSON));
    }
}
