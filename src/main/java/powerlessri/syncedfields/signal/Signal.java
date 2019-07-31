package powerlessri.syncedfields.signal;

import java.io.Serializable;

public abstract class Signal<M extends Serializable> {

    public enum Type {
        SENDER, RECEIVER
    }

    private final String name;
    private final Class<M> typeClass;

    protected Signal(String name, Class<M> typeClass) {
        this.name = name;
        this.typeClass = typeClass;
    }

    public abstract void transfer(M value);

    public abstract void receive(M value);

    public abstract void receiveRaw(byte[] bytes);

    public abstract Type getType();

    public String getName() {
        return name;
    }

    public Class<M> getTypeClass() {
        return typeClass;
    }
}
