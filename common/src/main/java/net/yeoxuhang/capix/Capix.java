package net.yeoxuhang.capix;

import net.minecraft.resources.ResourceLocation;
import net.yeoxuhang.capix.platform.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Capix {

    public static final String MOD_ID = "capix";
    public static final String MOD_NAME = "Capix";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param mod The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    public static Boolean isModLoaded(String mod){
        return Services.PLATFORM.isModLoaded(mod);
    }

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    public static Boolean isDevEnvironment(){
        return Services.PLATFORM.isDevelopmentEnvironment();
    }

    public static ResourceLocation name(String name){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
