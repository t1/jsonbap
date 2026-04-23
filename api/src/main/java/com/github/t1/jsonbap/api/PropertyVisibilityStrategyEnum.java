package com.github.t1.jsonbap.api;

import jakarta.json.bind.config.PropertyVisibilityStrategy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Allows to define the strategy to decide if a Java property is visible or not.
 * This never overrides {@link jakarta.json.bind.annotation.JsonbTransient JsonbTransient} annotations.
 */
public enum PropertyVisibilityStrategyEnum implements PropertyVisibilityStrategy {
    /// This is the default visibility strategy that makes all public getters and setters,
    /// as well as public fields visible.
    PUBLIC;
    // what else?

    @Override public boolean isVisible(Field field) {throw new UnsupportedOperationException("don't call this");}

    @Override public boolean isVisible(Method method) {throw new UnsupportedOperationException("don't call this");}
}
