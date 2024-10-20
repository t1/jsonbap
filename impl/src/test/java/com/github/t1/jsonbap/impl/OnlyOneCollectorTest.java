package com.github.t1.jsonbap.impl;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.github.t1.jsonbap.impl.OnlyOneCollector.only;
import static org.assertj.core.api.Assertions.catchRuntimeException;
import static org.assertj.core.api.BDDAssertions.then;

@SuppressWarnings("ResultOfMethodCallIgnored")
class OnlyOneCollectorTest {
    @Test
    void shouldFindNone() {
        var result = Stream.<String>empty().collect(only());

        then(result).isEmpty();
    }

    @Test
    void shouldFindOne() {
        var result = Stream.of("foo").collect(only());

        then(result).contains("foo");
    }

    @Test
    void shouldFailForTwo() {
        var e = catchRuntimeException(() -> Stream.of("foo", "bar").collect(only()));

        then(e).isInstanceOf(IllegalStateException.class)
                .hasMessage("found two: foo and bar");
    }

    @Test
    void shouldFailForThree() {
        var e = catchRuntimeException(() -> Stream.of("foo", "bar", "baz").collect(only()));

        then(e).isInstanceOf(IllegalStateException.class)
                .hasMessage("found two: foo and bar");
    }

    @Test
    void shouldFailForMany() {
        var stream = IntStream.range(0, 100_000).parallel().mapToObj(Integer::toString);

        var e = catchRuntimeException(() -> stream.collect(only()));

        then(e).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("found two: ");
    }
}
