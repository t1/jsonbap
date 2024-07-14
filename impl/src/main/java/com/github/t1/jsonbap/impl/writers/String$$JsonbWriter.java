package com.github.t1.jsonbap.impl.writers;

import com.github.t1.jsonbap.api.JsonbWriter;
import com.github.t1.jsonbap.impl.JsonGeneratorContext;
import jakarta.json.stream.JsonGenerator;

public class String$$JsonbWriter implements JsonbWriter<String, JsonGeneratorContext> {
    @Override public void toJson(String object, JsonGenerator writer, JsonGeneratorContext context) {
        writer.write(object);
    }
}
