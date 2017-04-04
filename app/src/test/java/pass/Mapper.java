package pass;

public interface Mapper<T, R> {
    R map(T value);
}
