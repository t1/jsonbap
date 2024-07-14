package test;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.spi.JsonbProvider;

public class YassonIT extends JsonbIT {
    protected static final Class<? extends JsonbProvider> JSONB_PROVIDER = org.eclipse.yasson.JsonBindingProvider.class;
    private static final Jsonb JSONB = builder(JSONB_PROVIDER, false);
    private static final Jsonb JSONB_FORMATTED = builder(JSONB_PROVIDER, true);

    @Override protected Jsonb jsonb() {return JSONB;}

    @Override protected Jsonb jsonbFormatted() {return JSONB_FORMATTED;}
}
