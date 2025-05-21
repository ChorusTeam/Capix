package net.yeoxuhang.capix.config;

import net.minecraft.resources.ResourceLocation;
import net.yeoxuhang.capix.api.CapixApi;
import net.yeoxuhang.capix.api.ModCape;

public class CapixManager {
    public static ResourceLocation getCapeForPlayer(String name) {
        for (ModCape cape : CapixApi.getEnabledCapes()) {
            if (cape.shouldRenderFor(name)) {
                return cape.texture;
            }
        } return null;
    }

    public static boolean playerHasThisCape(String name){
        for (ModCape cape : CapixApi.getEnabledCapes()) {
            if (cape.shouldRenderFor(name)) {
                return true;
            }
        } return false;
    }
}

