package test;

import jakarta.json.bind.Jsonb;

abstract class JsonbIT extends AbstractJsonIT {
    @Override String toJson(Object object) {
        @SuppressWarnings("resource")
        var jsonb = jsonb();
        return jsonb.toJson(object);
    }

    protected abstract Jsonb jsonb();
}
