package powerlessri.syncedfields.signal;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.io.Serializable;

final class ClientSignalProxy<M extends Serializable> implements ISignal<M> {

    private boolean adaptedToDedicatedServer;
    private ISignal<M> handle;

    private final ISignal<M> clientSignal;
    private final ISignal<M> sharedSignal;

    public ClientSignalProxy(ISignal<M> clientSignal, ISignal<M> integratedServerSignal) {
        this.adaptedToDedicatedServer = ServerLifecycleHooks.getCurrentServer().isDedicatedServer();
        this.clientSignal = clientSignal;
        this.sharedSignal = new SharedSignal<>(clientSignal, integratedServerSignal);
    }

    @Override
    public void transfer(M value) {
        handle.transfer(value);
    }

    @Override
    public void receive(M value) {
        handle.receive(value);
    }

    @Override
    public void receiveRawData(byte[] bytes) {
        handle.receiveRawData(bytes);
    }

    private void checkForConnection() {
        MinecraftServer connectedServer = ServerLifecycleHooks.getCurrentServer();
        if (connectedServer == null) {
            handle.invalidate();
        } else if (connectedServer.isDedicatedServer() && isAdaptedToIntegratedServer()) {
            // Adapt to dedicated server
            handle.invalidate();
            setCurrentHandle(clientSignal);
            adaptedToDedicatedServer = true;
        } else if (!connectedServer.isDedicatedServer() && isAdaptedToDedicatedServer()) {
            // Adapt to integrated server
            handle.invalidate();
            setCurrentHandle(sharedSignal);
            adaptedToDedicatedServer = false;
        }
    }

    private void setCurrentHandle(ISignal<M> handle) {
        this.handle.invalidate();
        this.handle = handle;
        handle.validate();
    }

    @Override
    public String getName() {
        return handle.getName();
    }

    @Override
    public Class<M> getTypeClass() {
        return handle.getTypeClass();
    }

    @Override
    public Type getSignalType() {
        return handle.getSignalType();
    }

    @Override
    public boolean isValid() {
        return handle.isValid();
    }

    @Override
    public void invalidate() {
        handle.invalidate();
    }

    @Override
    public void validate() {
        handle.validate();
    }

    public boolean isAdaptedToDedicatedServer() {
        return adaptedToDedicatedServer;
    }

    public boolean isAdaptedToIntegratedServer() {
        return !adaptedToDedicatedServer;
    }
}
