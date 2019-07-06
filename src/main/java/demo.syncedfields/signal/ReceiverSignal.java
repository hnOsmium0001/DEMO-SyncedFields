package demo.syncedfields.signal;

import java.util.function.Consumer;

public class ReceiverSignal<M> extends Signal<M> {

    private final Consumer<M> onReceived;

    ReceiverSignal(String name, Class<M> type, Consumer<M> onReceived) {
        this.onReceived = onReceived;
    }

    @Override
    public void receive(M value) {
        onReceived.accept(value);
    }

    @Override
    public void transfer(M value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Type getType() {
        return Type.RECEIVER;
    }
}
