package net.yeoxuhang.capix;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.yeoxuhang.capix.api.CapixApi;
import net.yeoxuhang.capix.client.gui.CapeSettingsScreen;

@Mod(value = Capix.MOD_ID, dist = Dist.CLIENT)
public class CapixForge {

    public CapixForge() {
        Capix.LOG.debug("NeoForge Loaded");
        CapixApi.registerCape(Capix.MOD_ID, "Example Cape", Capix.name("textures/capix_cape.png"), "https://raw.githubusercontent.com/ChorusTeam/Capix/master/namelist.txt");
        CapixApi.reload();
        CapixApi.getEnabledCapes().forEach(c -> {
            Capix.LOG.debug("Registered cape: " + c.modId);
        });
        registerScreen(ModLoadingContext.get());
    }

    public static void registerScreen(ModLoadingContext modLoadingContext) {
        modLoadingContext.registerExtensionPoint(IConfigScreenFactory.class, () -> (minecraft, screen) -> new CapeSettingsScreen(screen));
    }
}
