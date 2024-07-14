package com.github.t1.jsonbap.impl.writers;

import com.github.t1.jsonbap.api.JsonbWriter;
import jakarta.json.stream.JsonGenerator;

public class String$$JsonbWriter implements JsonbWriter<String> {
    @Override public void toJson(String object, JsonGenerator writer) {
        writer.write(object);
    }
}
