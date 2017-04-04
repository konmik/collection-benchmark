package pass;

import java.util.List;

public abstract class Pass<T> implements PassEmitter<T> {

    public static <T> Pass<T> stream(List<T> list) {
        return new Pass<T>() {
            @Override
            public void emit(Consumer<T> consumer) {
                for (int i = 0; i < list.size(); i++) {
                    consumer.call(list.get(i));
                }
            }
        };
    }

    public Pass<T> filter(Predicate<T> predicate) {
        Pass<T> me = this;
        return new Pass<T>() {
            @Override
            public void emit(Consumer<T> consumer) {
                me.emit(it -> {
                    if (predicate.test(it)) {
                        consumer.call(it);
                    }
                });
            }
        };
    }

    public <R> Pass<R> map(Mapper<T, R> mapper) {
        Pass<T> me = this;
        return new Pass<R>() {
            @Override
            public void emit(Consumer<R> consumer) {
                me.emit(it -> consumer.call(mapper.map(it)));
            }
        };
    }

    public <R> Pass<R> flatMap(FlatMapper<T, R> mapper) {
        Pass<T> me = this;
        return new Pass<R>() {
            @Override
            public void emit(Consumer<R> consumer) {
                me.emit(it -> mapper.map(it, consumer));
            }
        };
    }

    public <R> R collect(PassCollector<T, R> collector) {
        return collector.call(this);
    }
}
