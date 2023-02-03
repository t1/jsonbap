package com.github.t1.jsonbap.impl.writers;

import com.github.t1.jsonbap.api.JsonbWriter;
import com.github.t1.jsonbap.impl.ApJsonbProvider;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class Collection$$JsonbWriter implements JsonbWriter<List<?>> {
    @Override public void toJson(List<?> object, Writer out) throws IOException {
        out.append('[');
        var empty = true;
        for (Object item : object) {
            if (item != null) {
                if (!empty) out.append(',');
                ApJsonbProvider.jsonbWriterFor(item).toJson(item, out);
                empty = false;
            }
        }
        out.append(']');
    }
}
