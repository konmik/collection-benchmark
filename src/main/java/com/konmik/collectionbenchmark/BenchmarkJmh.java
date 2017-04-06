package com.konmik.collectionbenchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.konmik.collectionbenchmark.BenchmarkJmh.RUNS;
import static com.konmik.collectionbenchmark.CollectionBenchmark.benchmarkCopyList;
import static com.konmik.collectionbenchmark.CollectionBenchmark.benchmarkImperative;
import static com.konmik.collectionbenchmark.CollectionBenchmark.benchmarkJava8;
import static com.konmik.collectionbenchmark.CollectionBenchmark.benchmarkPass;
import static com.konmik.collectionbenchmark.CollectionBenchmark.benchmarkPassUnoptimized;
import static com.konmik.collectionbenchmark.CollectionBenchmark.benchmarkSolid;
import static com.konmik.collectionbenchmark.CollectionBenchmark.generate;

@Fork(2)
@Warmup(iterations = 2)
@Measurement(iterations = RUNS / 2)
@Threads(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class BenchmarkJmh {

    static final int RUNS = 30;

    @State(Scope.Thread)
    public static class Params {

        @Param({"2", "8", "32", "128", "1024"})
        String size;

        List<Integer> list;

        @Setup
        public void setup() {
            list = generate(Integer.valueOf(size));
        }
    }

    @Benchmark
    public static void kotlinSequence(Params params) {
        BenchmarkKotlin.INSTANCE.benchmarkKotlinSequence(params.list);
    }

    @Benchmark
    public static void kotlinList(Params params) {
        BenchmarkKotlin.INSTANCE.benchmarkKotlinList(params.list);
    }

    @Benchmark
    public static void java8(Params params) {
        benchmarkJava8(params.list);
    }

    @Benchmark
    public static void solid(Params params) {
        benchmarkSolid(params.list);
    }

    @Benchmark
    public static void copyList(Params params) {
        benchmarkCopyList(params.list);
    }

    @Benchmark
    public static void pass(Params params) {
        benchmarkPass(params.list);
    }

    @Benchmark
    public static void passUnoptimized(Params params) {
        benchmarkPassUnoptimized(params.list);
    }

    @Benchmark
    public static void imperative(Params params) {
        benchmarkImperative(params.list);
    }
}
