package net.yeoxuhang.capix;

import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import net.yeoxuhang.capix.platform.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class Capix {

    public static final String MOD_ID = "capix";
    public static final String MOD_NAME = "Capix";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static Supplier<Boolean> isModLoaded(String mod){
        return Suppliers.memoize(() -> Services.PLATFORM.isModLoaded(mod));
    }

    public static Supplier<Boolean> isDevEnvironment(){
        return Suppliers.memoize(Services.PLATFORM::isDevelopmentEnvironment);
    }

    public static ResourceLocation name(String name){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
