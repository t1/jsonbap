package com.github.t1.jsonbap.impl.writers;

import com.github.t1.jsonbap.api.JsonbWriter;

import java.io.IOException;
import java.io.Writer;

public class String$$JsonbWriter implements JsonbWriter<String> {
    @Override public void toJson(String object, Writer writer) throws IOException {
        writer.append('"').append(object).append('"'); // TODO escape quotes and newline (more?)
    }
}
