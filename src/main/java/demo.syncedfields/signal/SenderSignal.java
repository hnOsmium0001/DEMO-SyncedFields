package demo.syncedfields.signal;

import demo.syncedfields.SyncedFields;
import demo.syncedfields.network.NetworkHandler;
import demo.syncedfields.network.PacketSignal;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.io.*;

abstract class SenderSignal<M extends Serializable> extends Signal<M> {

    public static <M extends Serializable> SenderSignal<M> forClient(String name, Class<M> typeClass) {
        return new SenderSignal<M>(name, typeClass) {
            @Override
            protected void sendPacket(PacketSignal msg) {
                NetworkHandler.CHANNEL.sendToServer(msg);
            }
        };
    }

    public static <M extends Serializable> SenderSignal<M> forServer(String name, Class<M> typeClass) {
        return new SenderSignal<M>(name, typeClass) {
            @Override
            protected void sendPacket(PacketSignal msg) {
                for (ServerPlayerEntity player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
                    NetworkHandler.CHANNEL.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
                }
            }
        };
    }

    public SenderSignal(String name, Class<M> typeClass) {
        super(name, typeClass);
    }

    @Override
    public void transfer(M value) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(value);
            out.flush();

            byte[] bytes = bos.toByteArray();
            PacketSignal msg = new PacketSignal(getName(), bytes);
            sendPacket(msg);
        } catch (IOException e) {
            SyncedFields.logger.error("Exception when serializing object {}", value, e);
        }
    }

    @Override
    public void receive(M value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void receiveRaw(byte[] bytes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Type getType() {
        return Type.SENDER;
    }

    protected abstract void sendPacket(PacketSignal msg);
}
