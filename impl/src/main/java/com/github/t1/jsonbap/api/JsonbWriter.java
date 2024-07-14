package com.github.t1.jsonbap.api;

import jakarta.json.stream.JsonGenerator;

/**
 * Write an object to a {@link JsonGenerator}.
 *
 * @param <T> the object to be written
 * @param <C> the context; we don't want a (circular) dependency between the <code>api</code> package
 *          and the <code>impl</code> package
 */
public interface JsonbWriter<T, C> {
    void toJson(T object, JsonGenerator out, C context);
}
