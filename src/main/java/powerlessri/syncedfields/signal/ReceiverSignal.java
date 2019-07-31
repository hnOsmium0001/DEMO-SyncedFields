package powerlessri.syncedfields.signal;

import powerlessri.syncedfields.SyncedFields;

import java.io.*;
import java.util.function.Consumer;

class ReceiverSignal<M extends Serializable> extends Signal<M> {

    private final Consumer<M> onReceived;

    public ReceiverSignal(String name, Class<M> typeClass, Consumer<M> onReceived) {
        super(name, typeClass);
        this.onReceived = onReceived;
    }

    @Override
    public void receive(M value) {
        onReceived.accept(value);
    }

    @Override
    public void receiveRaw(byte[] bytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes)) {
            ObjectInput in = new ObjectInputStream(bis);
            Object value = in.readObject();

            // We just want a ClassCastException to propagate if the type is messed up, so don't really need a assignability check here
            receive(getTypeClass().cast(value));
        } catch (IOException | ClassNotFoundException e) {
            SyncedFields.logger.error("Exception when deserializing byte array {}", bytes, e);
        }
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
