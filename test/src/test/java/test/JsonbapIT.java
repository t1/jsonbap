package test;

import com.github.t1.jsonbap.impl.ApJsonbProvider;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.spi.JsonbProvider;

public class JsonbapIT extends JsonbIT {
    protected static final Class<? extends JsonbProvider> JSONB_PROVIDER = ApJsonbProvider.class;
    private static final Jsonb JSONB = builder(JSONB_PROVIDER, false);
    private static final Jsonb JSONB_FORMATTED = builder(JSONB_PROVIDER, true);

    @Override protected Jsonb jsonb() {return JSONB;}

    @Override protected Jsonb jsonbFormatted() {return JSONB_FORMATTED;}
}
