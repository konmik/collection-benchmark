package pass;

import java.util.List;

public abstract class Pass<T> implements Emitter<T> {

    public static <T> Pass<T> stream(List<T> list) {
        return new Pass<T>() {
            @Override
            public void emit(Consumer<T> consumer) {
                for (int i = 0, size = list.size(); i < size; i++) {
                    consumer.call(list.get(i));
                }
            }
        };
    }

    @SafeVarargs
    public static <T> Pass<T> array(T... items) {
        return new Pass<T>() {
            @Override
            public void emit(Consumer<T> consumer) {
                for (int i = 0, size = items.length; i < size; i++) {
                    consumer.call(items[i]);
                }
            }
        };
    }

    public static <T> Pass<T> of(T i0) {
        return new Pass<T>() {
            @Override
            public void emit(Consumer<T> consumer) {
                consumer.call(i0);
            }
        };
    }

    public static <T> Pass<T> of(T i0, T i1) {
        return new Pass<T>() {
            @Override
            public void emit(Consumer<T> consumer) {
                consumer.call(i0);
                consumer.call(i1);
            }
        };
    }

    public static <T> Pass<T> of(T i0, T i1, T i2) {
        return new Pass<T>() {
            @Override
            public void emit(Consumer<T> consumer) {
                consumer.call(i0);
                consumer.call(i1);
                consumer.call(i2);
            }
        };
    }

    public Pass<T> filter(Predicate<T> predicate) {
        return new Pass<T>() {
            @Override
            public void emit(Consumer<T> consumer) {
                Pass.this.emit(it -> {
                    if (predicate.test(it)) {
                        consumer.call(it);
                    }
                });
            }
        };
    }

    public <R> Pass<R> map(Mapper<T, R> mapper) {
        return new Pass<R>() {
            @Override
            public void emit(Consumer<R> consumer) {
                Pass.this.emit(it -> consumer.call(mapper.map(it)));
            }
        };
    }

    public <R> Pass<R> flatMapList(Mapper<T, List<R>> mapper) {
        return new Pass<R>() {
            @Override
            public void emit(Consumer<R> consumer) {
                Pass.this.emit(it -> {
                    List<R> list = mapper.map(it);
                    for (int i = 0, size = list.size(); i < size; i++) {
                        consumer.call(list.get(i));
                    }
                });
            }
        };
    }

    public <R> Pass<R> flatMap(Mapper<T, Pass<R>> mapper) {
        return new Pass<R>() {
            @Override
            public void emit(Consumer<R> consumer) {
                Pass.this.emit(it ->
                    mapper.map(it).emit(consumer));
            }
        };
    }

    public <R> R collect(PassCollector<T, R> collector) {
        return collector.call(this);
    }
}
