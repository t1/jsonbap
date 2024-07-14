package com.github.t1.jsonbap.api;

import jakarta.json.stream.JsonGenerator;

public interface JsonbWriter<T> {
    void toJson(T object, JsonGenerator out);
}
