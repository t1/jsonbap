package test;

import com.github.t1.jsonbap.impl.ApJsonbProvider;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.spi.JsonbProvider;

public class JsonbapIT extends JsonbIT {
    // this extra effort is for the performance when doing performance tests: we want every variant to be initiated only once
    private static final Class<? extends JsonbProvider> JSONB_PROVIDER = ApJsonbProvider.class;
    private static final Jsonb JSONB = builder(JSONB_PROVIDER, false, false);
    private static final Jsonb JSONB_FORMATTED = builder(JSONB_PROVIDER, true, false);
    private static final Jsonb JSONB_NULL_VALUES = builder(JSONB_PROVIDER, false, true);
    private static final Jsonb JSONB_FORMATTED_NULL_VALUES = builder(JSONB_PROVIDER, true, true);

    @Override protected Jsonb jsonb(boolean formatted, boolean nullValues) {
        return formatted ?
                nullValues ? JSONB_FORMATTED_NULL_VALUES : JSONB_FORMATTED :
                nullValues ? JSONB_NULL_VALUES : JSONB;
    }
}
