package com.konmik.collectionbenchmark;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pass.Pass;
import pass.PassCollectors;

import static java.lang.System.nanoTime;
import static java.util.Arrays.asList;
import static solid.collectors.ToList.toList;

public class CollectionBenchmark {

    public static final int ITEMS = 100000000;
    public static final int MAX_LIST_SIZE = 1024;

    private static List<List<Long>> totals = new ArrayList<>();
    private static List<List<Long>> overhead = new ArrayList<>();

    public void benchmark1() {

        System.out.println("Sit back and relax, the test needs to run for several minutes.");
        System.out.println("Don't touch you computer and don't run background tasks while it is working.");

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

        ArrayList<Integer> list = generate(size);

        long begin;

        begin = nanoTime();
        for (int i = 0; i < iterate; i++) {
            BenchmarkKotlin.INSTANCE.benchmarkKotlinSequence(list);
        }
        long kotlin = nanoTime() - begin;

        begin = nanoTime();
        for (int i = 0; i < iterate; i++) {
            BenchmarkKotlin.INSTANCE.benchmarkKotlinList(list);
        }
        long kotlinDirect = nanoTime() - begin;

        begin = nanoTime();
        for (int i = 0; i < iterate; i++) {
            benchmarkJava8(list);
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

    @NotNull
    public static ArrayList<Integer> generate(int size) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        return list;
    }

    private static String pad(Object value, int width) {
        String asString = value.toString();
        StringBuilder builder = new StringBuilder();
        while (builder.length() + asString.length() < width) {
            builder.append(' ');
        }
        return builder.append(asString).toString();
    }

    public static void benchmarkJava8(List<Integer> list) {
        list.stream()
            .filter(it -> it % 10 != 0)
            .map(Object::toString)
            .flatMap(it -> Stream.of(it, it + 1))
            .collect(Collectors.toList());
    }

    public static void benchmarkSolid(List<Integer> list) {
        solid.stream.Stream.stream(list)
            .filter(it -> it % 10 != 0)
            .map(Object::toString)
            .flatMap(it -> solid.stream.Stream.of(it, it + 1))
            .collect(toList(list.size() * 2));
    }

    public static void benchmarkCopyList(List<Integer> list) {
        List<Integer> filtered = CopyList.filter(list, it -> it % 10 != 0);
        List<String> mapped = CopyList.map(filtered, Object::toString);
        CopyList.flatMap(mapped, it -> asList(it, it + 1));
    }

    public static void benchmarkImperative(List<Integer> list) {
        ArrayList<String> result = new ArrayList<>(list.size() * 2);
        for (int i = 0; i < list.size(); i++) {
            int it = list.get(i);
            if (it % 10 != 0) {
                String str = Integer.toString(it);
                result.add(str);
                result.add(str + 1);
            }
        }
    }

    public static void benchmarkPass(List<Integer> list) {
        Pass.stream(list)
            .filter(it -> it % 10 != 0)
            .map(Object::toString)
            .flatMap(it -> Pass.of(it, it + 1))
            .collect(PassCollectors.toList(list.size() * 2));
    }

    public static void benchmarkPassUnoptimized(List<Integer> list) {
        Pass.stream(list)
            .filter(it -> it % 10 != 0)
            .map(Object::toString)
            .flatMapList(it -> asList(it, it + 1))
            .collect(PassCollectors.toList());
    }
}