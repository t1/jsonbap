package test;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.eclipse.yasson.JsonBindingProvider;

public class YassonIT extends JsonbIT {
    private static final Jsonb JSONB = JsonbBuilder.newBuilder(JsonBindingProvider.class.getName()).build();

    @Override protected Jsonb jsonb() {return JSONB;}
}
