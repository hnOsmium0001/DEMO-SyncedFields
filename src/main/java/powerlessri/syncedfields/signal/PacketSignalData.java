package powerlessri.syncedfields.signal;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public final class PacketSignalData {

    public static void encode(PacketSignalData msg, PacketBuffer buf) {
        buf.writeString(msg.name);
        buf.writeByteArray(msg.bytes);
    }

    public static PacketSignalData decode(PacketBuffer buf) {
        return new PacketSignalData(buf.readString(), buf.readByteArray());
    }

    public static void handle(PacketSignalData msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Signals signals = Signals.getInstanceFor(ctx.get().getDirection());
            signals.receive(msg.name, msg.bytes);
        });
    }

    private String name;
    private byte[] bytes;

    public PacketSignalData(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }
}
