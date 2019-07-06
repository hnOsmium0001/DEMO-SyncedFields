package demo.syncedfields.signal;

import java.util.*;

public class Signals {

    private static final Map<String, Set<Signal<?>>> registeredReceivers = new HashMap<>();
    private static final Map<String, Class<?>> receiverTypes = new HashMap<>();

    static void putValue(Object value, String name) {
        receiverTypes.get(name).cast(value);
    }

    static void register(String name, Signal<?> signalObject) {
        getSet(name).add(signalObject);
    }

    private static Set<Signal<?>> getSet(String name) {
        return registeredReceivers.get(name);
    }

}
