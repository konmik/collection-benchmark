package pass;

public interface PassOperator<U, T> {
    Pass<T> apply(Pass<U> upstream);
}
