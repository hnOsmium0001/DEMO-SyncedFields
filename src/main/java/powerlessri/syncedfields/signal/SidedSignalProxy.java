package powerlessri.syncedfields.signal;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.io.Serializable;

class SidedSignalProxy<M extends Serializable> implements ISignal<M> {

    private boolean adaptedToDedicatedServer;
    private ISignal<M> handle;

    protected SidedSignalProxy(ISignal<M> handle) {
        this(handle.getName(), handle.getTypeClass(), handle);
    }

    protected SidedSignalProxy(String name, Class<M> typeClass, ISignal<M> handle) {
        this.adaptedToDedicatedServer = ServerLifecycleHooks.getCurrentServer().isDedicatedServer();
        this.handle = handle;
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
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            handle.invalidate();
        } else if (server.isDedicatedServer() && isAdaptedToIntegratedServer()) {
            // Adapt to dedicated server
            handle.invalidate();
            // TODO new handle object
            adaptedToDedicatedServer = true;
        } else if (!server.isDedicatedServer() && isAdaptedToDedicatedServer()) {
            // Adapt to integrated server
            handle.invalidate();
            // TODO new handle object
            adaptedToDedicatedServer = false;
        }
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

    public boolean isAdaptedToDedicatedServer() {
        return adaptedToDedicatedServer;
    }

    public boolean isAdaptedToIntegratedServer() {
        return !adaptedToDedicatedServer;
    }
}
