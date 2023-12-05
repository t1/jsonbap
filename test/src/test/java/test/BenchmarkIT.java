package test;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Locale;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;
import static org.openjdk.jmh.annotations.Threads.MAX;

@Slow
@Warmup(time = 5, iterations = 5, batchSize = 10)
@Measurement(time = 5, iterations = 5, batchSize = 10)
@Fork(1) @Threads(MAX)
@BenchmarkMode(AverageTime)
@OutputTimeUnit(MICROSECONDS)
public class BenchmarkIT {
    static {
        Locale.setDefault(Locale.US);
    }

    @Benchmark
    public void shouldSerializeYasson() {
        run(new YassonIT());
    }

    @Benchmark
    public void shouldSerializeJsonbap() {
        run(new JsonbapIT());
    }

    @Benchmark
    public void shouldSerializeJsonIter() {
        run(new JsonIterIT());
    }

    @Benchmark
    public void shouldSerializeJackson() {
        run(new JacksonIT());
    }

    @Benchmark
    public void shouldSerializeStringWriter() {
        run(new StringWriterIT());
    }

    @Benchmark
    public void shouldSerializeJsonP() {
        run(new JsonPIT());
    }

    private static void run(AbstractJsonIT it) {
        it.shouldSerialize();
    }

    @Test void benchmark() throws Exception {
        new Runner(new OptionsBuilder().build()).run();
    }
}
