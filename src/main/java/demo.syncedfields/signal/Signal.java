package demo.syncedfields.signal;

public abstract class Signal<M> {

    public enum Type {
        SENDER, RECEIVER
    }

    Signal() {
    }

    public abstract void transfer(M value);

    public abstract void receive(M value);

    public abstract Type getType();
}
