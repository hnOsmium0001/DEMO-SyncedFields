package powerlessri.syncedfields.network;

import com.google.common.base.Preconditions;
import powerlessri.syncedfields.SyncedFields;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.*;

public final class NetworkHandler {

    private NetworkHandler() {
    }

    public static final String PROTOCOL_VERSION = Integer.toString(0);
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(SyncedFields.MODID, "Main"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void init() {
        // Both sides
        registerMessage(PacketSignal.class, PacketSignal::encode, PacketSignal::decode, PacketSignal::handle);
    }

    private static int nextID = 0;

    private static <MSG> void registerMessage(Class<MSG> messageType, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder, BiConsumer<MSG, Supplier<NetworkEvent.Context>> handler) {
        Preconditions.checkState(nextID < 0xFF, "Too many messages!");
        CHANNEL.registerMessage(nextID, messageType, encoder, decoder, handler);
        nextID++;
    }
}
