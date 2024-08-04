package test;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.spi.JsonbProvider;

import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class JohnzonIT extends JsonbIT {
    // this extra effort is for the performance when doing performance tests: we want every variant to be initiated only once
    private static final Class<? extends JsonbProvider> JSONB_PROVIDER = org.apache.johnzon.jsonb.JohnzonProvider.class;
    private static final Map<TestConfig, Jsonb> JSONB_MAP = TestConfig.stream()
            .collect(toMap(identity(), testConfig -> builder(JSONB_PROVIDER, testConfig)
                    .withConfig(testConfig.toJsonbConfig()
                            .setProperty("johnzon.use-biginteger-stringadapter", false)
                            .setProperty("johnzon.use-bigdecimal-stringadapter", false))
                    .build()));

    @Override protected Jsonb jsonb(TestConfig testConfig) {return JSONB_MAP.get(testConfig);}
}
