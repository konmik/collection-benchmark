package pass;

public interface PassCollector<T, R> {
    R call(PassEmitter<T> emitter);
}
