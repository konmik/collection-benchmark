package pass;

public interface FlatMapper<T, R> {
    void map(T value, Consumer<R> consumer);
}
