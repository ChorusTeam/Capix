package net.yeoxuhang.capix.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.yeoxuhang.capix.Capix;
import net.yeoxuhang.capix.api.CapixApi;

public class CapixClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Capix.LOG.debug("Fabric Loaded");
        EntityModelLayerRegistry.registerModelLayer(Cape.LAYER_LOCATION, Cape::createBodyLayer);
        CapixApi.registerCape(Capix.MOD_ID, "Example Cape", Capix.name("textures/capix_cape.png"), "https://raw.githubusercontent.com/ChorusTeam/Capix/master/namelist.txt");
        CapixApi.reload();
        CapixApi.getEnabledCapes().forEach(c -> {
            Capix.LOG.debug("Registered cape: " + c.modId);
        });
    }
}
