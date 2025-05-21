package net.yeoxuhang.capix.platform;

import net.neoforged.fml.loading.FMLPaths;
import net.yeoxuhang.capix.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.io.File;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public File configFile() {
        return FMLPaths.CONFIGDIR.get().toFile();
    }
}
