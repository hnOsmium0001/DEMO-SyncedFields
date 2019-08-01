package powerlessri.syncedfields.signal;

import java.io.Serializable;

public interface ISignal<M extends Serializable> {

    void transfer(M value);

    void receive(M value);

    void receiveRawData(byte[] bytes);

    String getName();

    Class<M> getTypeClass();

    Type getSignalType();

    boolean isValid();

    void invalidate();

    void validate();

    enum Type {
        SENDER, RECEIVER
    }
}
