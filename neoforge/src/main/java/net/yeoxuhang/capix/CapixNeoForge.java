package net.yeoxuhang.capix;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.yeoxuhang.capix.api.CapixApi;
import net.yeoxuhang.capix.client.gui.CapeSettingsScreen;

@Mod(value = Capix.MOD_ID, dist = Dist.CLIENT)
public class CapixNeoForge {

    public CapixNeoForge() {
        IEventBus eventBus = NeoForge.EVENT_BUS;
        Capix.LOG.debug("NeoForge Loaded");
        if (Capix.isDevEnvironment()){
            CapixApi.registerCape(Capix.MOD_ID, "Example Cape", "textures/capix_cape.png", "https://raw.githubusercontent.com/ChorusTeam/Capix/master/namelist.txt");
            CapixApi.registerCape(Capix.MOD_ID, "Example Url Cape", "https://github.com/ChorusTeam/Capix/blob/master/http_cape.png?raw=true", "https://raw.githubusercontent.com/ChorusTeam/Capix/master/namelist.txt");
        }
        CapixApi.reload();
        CapixApi.getEnabledCapes().forEach(c -> {
            Capix.LOG.debug("Registered cape: " + c.modId);
        });
        registerScreen(ModLoadingContext.get());
        eventBus.addListener(this::onReloadListener);
        eventBus.register(this);
    }

    /**
     * Bind configuration screen to {@link Capix}
     * **/
    public static void registerScreen(ModLoadingContext modLoadingContext) {
        modLoadingContext.registerExtensionPoint(IConfigScreenFactory.class, () -> (minecraft, screen) -> new CapeSettingsScreen(screen));
    }


    @SubscribeEvent
    public void onReloadListener(AddReloadListenerEvent event) {
        event.addListener(new CapeDataPackLoader());
    }
}
