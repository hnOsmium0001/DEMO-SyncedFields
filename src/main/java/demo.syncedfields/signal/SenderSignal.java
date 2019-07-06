package demo.syncedfields.signal;

public class SenderSignal<M> extends Signal<M> {

    SenderSignal(String name, Class<M> type) {

    }

    @Override
    public void transfer(M value) {

    }

    @Override
    public void receive(M value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Type getType() {
        return Type.SENDER;
    }
}
