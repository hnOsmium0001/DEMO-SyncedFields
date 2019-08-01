package powerlessri.syncedfields.signal;

import java.io.Serializable;

public abstract class Signal<M extends Serializable> implements ISignal<M> {

    private final String name;
    private final Class<M> typeClass;
    private boolean valid = true;

    protected Signal(String name, Class<M> typeClass) {
        this.name = name;
        this.typeClass = typeClass;
    }

    public String getName() {
        return name;
    }

    public Class<M> getTypeClass() {
        return typeClass;
    }

    public boolean isValid() {
        return valid;
    }

    public void invalidate() {
        valid = false;
    }

    @Override
    public void validate() {
        valid = true;
    }
}
