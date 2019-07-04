package demo.syncedfields;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SyncedFields {

    public static final String MODID = "syncedfields";

    public static final Logger logger = LogManager.getLogger(MODID);

    public static SyncedFields instance;

    public SyncedFields() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
        eventBus.addListener(this::serverStarting);
        eventBus.addListener(this::loadComplete);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> eventBus.addListener(this::clientSetup));

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        instance = (SyncedFields) ModLoadingContext.get().getActiveContainer().getMod();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
    }

    private void serverStarting(final FMLServerStartingEvent event) {
    }

    private void loadComplete(final FMLLoadCompleteEvent event) {
    }
}
