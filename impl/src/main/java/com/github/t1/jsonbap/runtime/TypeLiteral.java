package com.github.t1.jsonbap.runtime;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/// Is there a simpler way to do type literals for JSON-B?
@SuppressWarnings("unused")
public class TypeLiteral<T> {
    public static Type genericType(TypeLiteral<?> typeLiteral) {
        return ((ParameterizedType) typeLiteral.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
