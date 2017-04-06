package com.konmik.collectionbenchmark;

import java.util.ArrayList;
import java.util.List;

import pass.Mapper;
import pass.Predicate;

public class CopyList {

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        ArrayList<T> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            T value = list.get(i);
            if (predicate.test(value)) {
                result.add(value);
            }
        }
        return result;
    }

    public static <T, R> List<R> map(List<T> list, Mapper<T, R> mapper) {
        ArrayList<R> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(mapper.map(list.get(i)));
        }
        return result;
    }

    public static <T, R> List<R> flatMap(List<T> list, Mapper<T, List<R>> mapper) {
        ArrayList<R> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.addAll(mapper.map(list.get(i)));
        }
        return result;
    }
}
