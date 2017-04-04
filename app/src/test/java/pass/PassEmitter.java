package pass;

public interface PassEmitter<T> {
    void emit(Consumer<T> consumer);
}
