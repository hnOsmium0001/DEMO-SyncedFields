package powerlessri.syncedfields.signal;

import com.google.common.base.Preconditions;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.*;

public class Signals {

    private static Signals CLIENT_INSTANCE;
    private static Signals SERVER_INSTANCE;

    public static Signals getClientInstance() {
        Preconditions.checkState(ServerLifecycleHooks.getCurrentServer().getWorlds().iterator().next().isRemote, "Trying to access the client instance on server!");
        return CLIENT_INSTANCE;
    }

    public static Signals getServerInstance() {
        Preconditions.checkState(!ServerLifecycleHooks.getCurrentServer().getWorlds().iterator().next().isRemote, "Trying to access the server instance on client!");
        return SERVER_INSTANCE;
    }

    public static Signals getInstanceFor(NetworkDirection direction) {
        switch (direction) {
            case PLAY_TO_SERVER: return SERVER_INSTANCE;
            case PLAY_TO_CLIENT: return CLIENT_INSTANCE;
            case LOGIN_TO_SERVER:
            case LOGIN_TO_CLIENT:
            default:
                throw new IllegalStateException();
        }
    }

    private final Map<String, Set<Signal<?>>> registeredReceivers = new HashMap<>();

    public void register(String name, Signal<?> signalObject) {
        Preconditions.checkArgument(signalObject.getType() == Signal.Type.RECEIVER);
        getRegisteredReceivers(name).add(signalObject);
    }

    public void receive(String name, byte[] bytes) {
        for (Signal<?> signal : registeredReceivers.get(name)) {
            signal.receiveRaw(bytes);
        }
    }

    public Set<Signal<?>> getRegisteredReceivers(String name) {
        if (registeredReceivers.containsKey(name)) {
            return registeredReceivers.get(name);
        }
        Set<Signal<?>> set = new HashSet<>();
        registeredReceivers.put(name, set);
        return set;
    }
}
