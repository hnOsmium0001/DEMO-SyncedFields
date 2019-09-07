package powerlessri.syncedfields.signal;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SignalTests.MODID)
public class SignalTests {

    public static final String MODID = "signaltests";

    public SignalTests() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);
    }

    private void setup(FMLCommonSetupEvent event) {

    }

}
