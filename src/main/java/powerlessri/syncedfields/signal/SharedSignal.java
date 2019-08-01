package powerlessri.syncedfields.signal;

import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.io.Serializable;

final class SharedSignal<M extends Serializable> implements ISignal<M> {

    private ISignal<M> clientSignal;
    private ISignal<M> serverSignal;

    public SharedSignal(ISignal<M> clientSignal, ISignal<M> serverSignal) {
        this.clientSignal = clientSignal;
        this.serverSignal = serverSignal;
    }

    public ISignal<M> getCurrentSignal() {
        if (ServerLifecycleHooks.getCurrentServer().isOnExecutionThread()) {
            return serverSignal;
        } else {
            return clientSignal;
        }
    }

    @Override
    public void transfer(M value) {
        getCurrentSignal().transfer(value);
    }

    @Override
    public void receive(M value) {
        getCurrentSignal().receive(value);
    }

    @Override
    public void receiveRawData(byte[] bytes) {
        getCurrentSignal().receiveRawData(bytes);
    }

    @Override
    public String getName() {
        return getCurrentSignal().getName();
    }

    @Override
    public Class<M> getTypeClass() {
        return getCurrentSignal().getTypeClass();
    }

    @Override
    public Type getSignalType() {
        return getCurrentSignal().getSignalType();
    }

    @Override
    public boolean isValid() {
        return getCurrentSignal().isValid();
    }

    @Override
    public void invalidate() {
        getCurrentSignal().invalidate();
    }

    @Override
    public void validate() {
        getCurrentSignal().validate();
    }
}
