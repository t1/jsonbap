package com.github.t1.jsonbap.impl.writers;

import com.github.t1.jsonbap.api.JsonbWriter;
import com.github.t1.jsonbap.impl.ApJsonbProvider;
import jakarta.json.stream.JsonGenerator;

public class Iterable$$JsonbWriter implements JsonbWriter<Iterable<?>> {
    @Override public void toJson(Iterable<?> object, JsonGenerator out) {
        out.writeStartArray();
        for (Object item : object) {
            if (item != null) {
                ApJsonbProvider.jsonbWriterFor(item).toJson(item, out);
            }
        }
        out.writeEnd();
    }
}
