package com.konmik.collectionbenchmark;

import android.annotation.TargetApi;
import android.os.Build;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.konmik.collectionbenchmark.Pass.filter;
import static com.konmik.collectionbenchmark.Pass.flatMap;
import static com.konmik.collectionbenchmark.Pass.map;
import static com.konmik.collectionbenchmark.Pass.passToList;
import static java.lang.System.nanoTime;
import static java.util.Arrays.asList;
import static solid.collectors.ToList.toList;

public class CollectionBenchmark {

    public static final int ITEMS = 100000000;
    public static final int MAX_LIST_SIZE = 1024;

    private static List<List<Long>> totals = new ArrayList<>();
    private static List<List<Long>> overhead = new ArrayList<>();

    @Test
    public void benchmark() {
        List<String> rows = asList(
                "SIZE",
                "Java 8 streams",
                "Kotlin sequence",
                "Kotlin list",
                "CopyList",
                "Imperative",
                "Solid",
                "Pass");

        for (int size = MAX_LIST_SIZE; size > 1; size /= 2)
            benchmarkSize(size);

        for (int i = 0; i < 2; i++) {
            System.out.println(i == 0 ? "TOTALS" : "OVERHEAD");
            List<List<Long>> report = i == 0 ? totals : overhead;
            for (int r = 0; r < rows.size(); r++) {
                System.out.print(pad(rows.get(r), 20));
                for (List<Long> numbers : report) {
                    System.out.print(pad(numbers.get(r), 7));
                }
                System.out.println();
            }
        }
    }

    private void benchmarkSize(int size) {

        int iterate = ITEMS / size;

        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }

        long begin;

        begin = nanoTime();
        for (int i = 0; i < iterate; i++) {
            Benchmark.INSTANCE.benchmarkKotlin(list);
        }
        long kotlin = nanoTime() - begin;

        begin = nanoTime();
        for (int i = 0; i < iterate; i++) {
            Benchmark.INSTANCE.benchmarkKotlinDirect(list);
        }
        long kotlinDirect = nanoTime() - begin;

        begin = nanoTime();
        for (int i = 0; i < iterate; i++) {
            benchmarkJava(list);
        }
        long java = nanoTime() - begin;

        begin = nanoTime();
        for (int i = 0; i < iterate; i++) {
            benchmarkCopyList(list);
        }
        long copyList = nanoTime() - begin;

        begin = nanoTime();
        for (int i = 0; i < iterate; i++) {
            benchmarkImperative(list);
        }
        long imperative = nanoTime() - begin;

        begin = nanoTime();
        for (int i = 0; i < iterate; i++) {
            benchmarkSolid(list);
        }
        long solid = nanoTime() - begin;

        begin = nanoTime();
        for (int i = 0; i < iterate; i++) {
            benchmarkPass(list);
        }
        long pass = nanoTime() - begin;

        totals.add(asList(
                (long) list.size(),
                java / iterate,
                kotlin / iterate,
                kotlinDirect / iterate,
                copyList / iterate,
                imperative / iterate,
                solid / iterate,
                pass / iterate));

        overhead.add(asList(
                (long) list.size(),
                (java - imperative) / iterate,
                (kotlin - imperative) / iterate,
                (kotlinDirect - imperative) / iterate,
                (copyList - imperative) / iterate,
                (imperative - imperative) / iterate,
                (solid - imperative) / iterate,
                (pass - imperative) / iterate));
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static void benchmarkJava(ArrayList<Integer> list) {
        list.stream()
                .filter(it -> it % 10 != 0)
                .map(Object::toString)
                .flatMap(it -> Stream.of(it, it + 1))
                .collect(Collectors.toList());
    }

    private static void benchmarkPass(ArrayList<Integer> list) {
        passToList(list, list.size() * 2, c ->
                filter(it -> it % 10 != 0,
                        map(Object::toString,
                                flatMap((it, consumer) -> {
                                    consumer.call(it);
                                    consumer.call(it + 1);
                                }, c))));
    }

    private static String pad(Object value, int width) {
        String asString = value.toString();
        StringBuilder builder = new StringBuilder();
        while (builder.length() + asString.length() < width) {
            builder.append(' ');
        }
        return builder.append(asString).toString();
    }

    private static void benchmarkSolid(ArrayList<Integer> list) {
        solid.stream.Stream.stream(list)
                .filter(it -> it % 10 != 0)
                .map(Object::toString)
                .flatMap(it -> solid.stream.Stream.of(it, it + 1))
                .collect(toList(list.size() * 2));
    }

    private static void benchmarkCopyList(ArrayList<Integer> list) {
        List<Integer> filtered = CopyList.filter(list, it -> it % 10 != 0);
        List<String> mapped = CopyList.map(filtered, Object::toString);
        List<String> flatMap = CopyList.flatMap(mapped, it -> asList(it, it + 1));
    }

    private static void benchmarkImperative(ArrayList<Integer> list) {
        ArrayList<String> result = new ArrayList<>(list.size() * 2);
        for (int i = 0; i < list.size(); i++) {
            int it = list.get(i);
            if (it % 10 != 0) {
                result.add(Integer.toString(it));
                result.add(Integer.toString(it) + 1);
            }
        }
    }
}