package demo.syncedfields.network;

import demo.syncedfields.signal.Signals;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public final class PacketSignal {

    public static void encode(PacketSignal msg, PacketBuffer buf) {
        buf.writeString(msg.name);
        buf.writeByteArray(msg.bytes);
    }

    public static PacketSignal decode(PacketBuffer buf) {
        return new PacketSignal(buf.readString(), buf.readByteArray());
    }

    public static void handle(PacketSignal msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Signals signals = Signals.getInstanceFor(ctx.get().getDirection());
            signals.receive(msg.name, msg.bytes);
        });
    }

    private String name;
    private byte[] bytes;

    public PacketSignal(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }
}
