package com.github.t1.jsonbap.impl.writers;

import com.github.t1.jsonbap.api.JsonbWriter;
import com.github.t1.jsonbap.impl.JsonGeneratorContext;
import jakarta.json.stream.JsonGenerator;

public class Iterable$$JsonbWriter implements JsonbWriter<Iterable<?>, JsonGeneratorContext> {
    @Override public void toJson(Iterable<?> object, JsonGenerator out, JsonGeneratorContext context) {
        out.writeStartArray();
        for (Object item : object) {
            if (item != null) {
                context.serialize(item, out);
            }
        }
        out.writeEnd();
    }
}
