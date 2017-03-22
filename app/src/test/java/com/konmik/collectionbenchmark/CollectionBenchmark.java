package com.konmik.collectionbenchmark;

import android.annotation.TargetApi;
import android.os.Build;

import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.nanoTime;
import static solid.collectors.ToList.toList;

public class CollectionBenchmark {

    public static final int ITEMS = 10000000;
    public static final int MAX_LIST_SIZE = 1024;

    @Test
    public void benchmark() {
        for (int size = MAX_LIST_SIZE; size > 1; size /= 2)
            benchmarkSize(size);
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

//        begin = nanoTime();
//        for (int i = 0; i < iterate; i++) {
//            benchmarkIMU(list);
//        }
//        long imu = nanoTime() - begin;

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

        System.out.printf("    %s, %s, %s, %s, %s, %s\n",
                pad("size:", list.size(), 10),
                pad("Java 8 streams:", java / iterate, 22),
                pad("Kotlin sequence:", kotlin / iterate, 22),
                pad("Kotlin list:", kotlinDirect / iterate, 19),
//                pad("ImmutableListUtils:", imu / iterate, 25),
                pad("Imperative:", imperative / iterate, 17),
                pad("Solid:", solid / iterate, 12));
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static void benchmarkJava(ArrayList<Integer> list) {
        list.stream()
                .filter(it -> it % 10 != 0)
                .map(Object::toString)
                .flatMap(it -> Stream.of(it, it + 1))
                .collect(Collectors.toList());
    }

    private static String pad(String tag, long value, int width) {
        String asString = Long.toString(value);
        StringBuilder builder = new StringBuilder(tag);
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
                .collect(toList());
    }

    // Can't provide implementation details because the code is proprietary.
    // I can say that it is the simplest possible implementation with creation a full list copy for each call.
//    private static void benchmarkIMU(ArrayList<Integer> list) {
//        List<Integer> filtered = filtered(list, it -> it % 10 != 0);
//        List<String> mapped = mapped(filtered, Object::toString);
//        List<String> flatMap = flatMap(mapped, it -> ImmutableListUtils.list(it, it + 1));
//    }

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