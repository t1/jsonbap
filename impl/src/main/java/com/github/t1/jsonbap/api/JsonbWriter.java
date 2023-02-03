package com.github.t1.jsonbap.api;

import java.io.IOException;
import java.io.Writer;

public interface JsonbWriter<T> {
    void toJson(T object, Writer writer) throws IOException;
}
