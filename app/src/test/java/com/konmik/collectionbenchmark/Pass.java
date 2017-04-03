package com.konmik.collectionbenchmark;

import java.util.ArrayList;
import java.util.List;

public class Pass {

    public interface Consumer<T> {
        void call(T value);
    }

    public interface Transformation<T, R> {
        Consumer<T> call(Consumer<R> consumer);
    }

    public interface Predicate<T> {
        boolean test(T value);
    }

    public interface Mapper<T, R> {
        R map(T value);
    }

    public interface FlatMapper<T, R> {
        void fmap(T value, Consumer<R> consumer);
    }

    public static <T, R> List<R> passToList(List<T> list, int initialCapacity, Transformation<T, R> transformation) {
        ArrayList<R> result = new ArrayList<>(initialCapacity);
        Consumer<T> consumer = transformation.call(result::add);
        for (int i = 0; i < list.size(); i++) {
            consumer.call(list.get(i));
        }
        return result;
    }

    public static <T, R> Consumer<T> map(Mapper<T, R> mapper, Consumer<R> consumer) {
        return t -> consumer.call(mapper.map(t));
    }

    public static <T> Consumer<T> filter(Predicate<T> test, Consumer<T> consumer) {
        return t -> {
            if (test.test(t)) {
                consumer.call(t);
            }
        };
    }

    public static <T, R> Consumer<T> flatMap(FlatMapper<T, R> mapper, Consumer<R> consumer) {
        return t -> mapper.fmap(t, consumer);
    }
}
