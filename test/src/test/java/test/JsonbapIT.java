package test;

import com.github.t1.jsonbap.impl.ApJsonbProvider;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

public class JsonbapIT extends JsonbIT {
    private static final Jsonb JSONB = JsonbBuilder.newBuilder(ApJsonbProvider.class.getName()).build();

    @Override protected Jsonb jsonb() {return JSONB;}
}
