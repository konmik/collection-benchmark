package pass;

public interface PassCollector<T, R> {
    R call(Emitter<T> emitter);
}
