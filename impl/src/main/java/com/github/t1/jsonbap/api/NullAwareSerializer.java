package com.github.t1.jsonbap.api;

/**
 * Marker interface for serializers that can recognize certain objects as <code>null</code>.
 * E.g. the built-in serializers for Optionals (also the primitive ones) handle an empty value
 * exactly like <code>null</code>.
 */
public interface NullAwareSerializer<T> {
    boolean isNull(T object);
}
