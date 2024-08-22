package com.github.t1.jsonbap.impl;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * A stream collector that collects only one element and throws an exception if there are more.
 */
@RequiredArgsConstructor
public class OnlyOneCollector<T> implements Collector<T, T, Optional<T>> {
    static <T> Collector<T, T, Optional<T>> only() {
        return only((l, r) -> new IllegalStateException("found two: " + l + " and " + r));
    }

    static <T> Collector<T, T, Optional<T>> only(BiFunction<T, T, RuntimeException> foundTwoException) {
        return new OnlyOneCollector<>(foundTwoException);
    }

    private final BiFunction<T, T, RuntimeException> foundTwoException;
    private T found;

    @Override public Supplier<T> supplier() {return () -> null;}

    @Override public BiConsumer<T, T> accumulator() {
        return (found, next) -> {
            if (this.found != null)
                throw foundTwoException.apply(this.found, next);
            this.found = next;
        };
    }

    @Override public BinaryOperator<T> combiner() {
        return (l, r) -> {
            throw new IllegalStateException("this should be unreachable: the accumulator should have failed before");
        };
    }

    @Override public Function<T, Optional<T>> finisher() {return t -> Optional.ofNullable(found);}

    @Override public Set<Characteristics> characteristics() {return Set.of();}
}
