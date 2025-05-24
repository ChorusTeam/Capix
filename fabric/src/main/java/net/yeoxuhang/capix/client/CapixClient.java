package net.yeoxuhang.capix.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;
import net.yeoxuhang.capix.CapeDataPackLoader;
import net.yeoxuhang.capix.Capix;
import net.yeoxuhang.capix.api.CapixApi;

public class CapixClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Capix.LOG.debug("Fabric Loaded");
        EntityModelLayerRegistry.registerModelLayer(Cape.LAYER_LOCATION, Cape::createBodyLayer);
        if (Capix.isDevEnvironment()){
            CapixApi.registerCape(Capix.MOD_ID, "Example Cape", "textures/capix_cape.png", "https://raw.githubusercontent.com/ChorusTeam/Capix/master/namelist.txt");
            CapixApi.registerCape(Capix.MOD_ID, "Example Url Cape", "https://github.com/ChorusTeam/Capix/blob/master/http_cape.png?raw=true", "https://raw.githubusercontent.com/ChorusTeam/Capix/master/namelist.txt");
        }
        CapixApi.reload();
        CapixApi.getEnabledCapes().forEach(c -> {
            Capix.LOG.debug("Registered cape: " + c.modId);
        });
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new CapeDataPackLoader());
    }
}
