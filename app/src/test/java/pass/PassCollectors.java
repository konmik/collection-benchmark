package pass;

import java.util.ArrayList;
import java.util.List;

public class PassCollectors {
    public static <T> PassCollector<T, List<T>> toList() {
        return emitter -> {
            ArrayList<T> list = new ArrayList<>();
            emitter.emit(list::add);
            return list;
        };
    }

    public static <T> PassCollector<T, List<T>> toList(int initialCapacity) {
        return emitter -> {
            ArrayList<T> list = new ArrayList<>(initialCapacity);
            emitter.emit(list::add);
            return list;
        };
    }
}
