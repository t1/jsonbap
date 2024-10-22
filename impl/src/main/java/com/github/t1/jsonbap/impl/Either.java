package com.github.t1.jsonbap.impl;

import java.util.NoSuchElementException;
import java.util.function.Predicate;

/// holds either a value or an alternative, usually some kind of error
public sealed interface Either<V, O> {
    static <V, O> Either<V, O> value(V value) {return new EitherValue<>(value);}

    static <V, O> Either<V, O> or(O or) {return new EitherOr<>(or);}

    record EitherValue<V, O>(V value) implements Either<V, O> {
        @Override public boolean isOr() {return false;}

        @Override public V get() {return value;}

        @Override public Either<V, O> with(Predicate<V> condition, V newValue) {
            return condition.test(value) ? this : Either.value(newValue);
        }
    }

    record EitherOr<V, O>(O or) implements Either<V, O> {
        @Override public V get() {throw new NoSuchElementException("no value, but: " + or);}

        @Override public boolean isOr() {return true;}

        @Override public Either<V, O> with(Predicate<V> condition, V newValue) {return this;}
    }

    V get();

    boolean isOr();

    /// If the condition is true, return this, otherwise replace the value
    Either<V, O> with(Predicate<V> condition, V newValue);
}
