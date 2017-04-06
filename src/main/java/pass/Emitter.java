package pass;

public interface Emitter<T> {
    void emit(Consumer<T> consumer);
}
