package powerlessri.syncedfields;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SyncedFields.MODID)
public class SyncedFields {

    public static final String MODID = "syncedfields";

    public static final Logger logger = LogManager.getLogger(MODID);

    public static SyncedFields instance;

    public SyncedFields() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::loadComplete);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> eventBus.addListener(this::clientSetup));

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
        MinecraftForge.EVENT_BUS.addListener(this::serverStopped);
    }

    private void setup(final FMLCommonSetupEvent event) {
        instance = (SyncedFields) ModLoadingContext.get().getActiveContainer().getMod();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
    }

    private void loadComplete(final FMLLoadCompleteEvent event) {
    }

    private void serverStopped(final FMLServerStoppedEvent event) {
        logger.info("waeg;hj stopped {}", ServerLifecycleHooks.getCurrentServer());
    }

    private void serverStarting(final FMLServerStartingEvent event) {
        logger.info("waeg;hj starting {}", ServerLifecycleHooks.getCurrentServer());
    }
}
