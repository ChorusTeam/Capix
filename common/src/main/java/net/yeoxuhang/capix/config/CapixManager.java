package net.yeoxuhang.capix.config;

import net.minecraft.resources.ResourceLocation;
import net.yeoxuhang.capix.api.CapixApi;
import net.yeoxuhang.capix.api.ModCape;
import net.yeoxuhang.capix.client.TextureLoader;

public class CapixManager {
    public static ResourceLocation getCapeForPlayer(String name) {
        for (ModCape cape : CapixApi.getEnabledCapes()) {
            if (cape.shouldRenderFor(name)) {
                return TextureLoader.load(cape.texture, cape.modId);
            }
        } return null;
    }
}

