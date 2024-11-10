package test;

import com.github.t1.jsonbap.test.Person;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.spi.JsonbProvider;

import java.math.BigDecimal;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class YassonIT extends JsonbIT {
    // this extra effort is for the performance when doing performance tests: we want every variant to be initiated only once
    private static final Class<? extends JsonbProvider> JSONB_PROVIDER = org.eclipse.yasson.JsonBindingProvider.class;
    private static final Map<TestConfig, Jsonb> JSONB_MAP = TestConfig.stream()
            .collect(toMap(identity(), testConfig -> builder(JSONB_PROVIDER, testConfig).build()));

    @Override protected Jsonb jsonb(TestConfig testConfig) {return JSONB_MAP.get(testConfig);}

    @Override Person cheat(Person person) {
        return super.cheat(person)
                .withIncome(person.getIncome() == null ? null : new BigDecimal(123456789)) // why are the trailing ".01" missing?
                .withPets(null); // Yasson doesn't use the builder with `@Singular` for `pets`
    }
}
