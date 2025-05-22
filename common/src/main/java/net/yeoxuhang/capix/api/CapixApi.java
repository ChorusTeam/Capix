package net.yeoxuhang.capix.api;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.yeoxuhang.capix.config.CapixConfig;
import net.yeoxuhang.capix.client.gui.CapeSettingsScreen;

import java.util.*;
import java.util.stream.Collectors;

public class CapixApi {
    private static final Map<String, Map<String, ModCape>> MOD_CAPES = new HashMap<>();

    public static void registerCape(String modId, String capeName, ResourceLocation textureLocation, String link){
        ModCape cape = new ModCape(modId, textureLocation, link);
        registerCape(modId, capeName, cape);
    }

    /*public static void registerCape(String modId, String capeName, String textureLink, String link){
        ModCape cape = new ModCape(modId, textureLink, link);
        registerCape(modId, capeName, cape);
    }*/


    public static void registerCape(String modId, String capeName, ModCape cape) {
        MOD_CAPES.computeIfAbsent(modId, k -> new HashMap<>()).put(capeName, cape);
        CapixConfig.getInstance().setCapeEnabledIfAbsent(modId, capeName, false);
    }

    public static Collection<ModCape> getEnabledCapes() {
        return MOD_CAPES.entrySet().stream()
                .flatMap(entry -> entry.getValue().entrySet().stream()
                        .filter(e -> CapixConfig.getInstance().isCapeEnabled(entry.getKey(), e.getKey()))
                        .map(Map.Entry::getValue))
                .collect(Collectors.toList());
    }

    public static Collection<ModCape> getAllCapes() {
        return MOD_CAPES.values().stream()
                .flatMap(map -> map.values().stream())
                .collect(Collectors.toList());
    }

    public static void reload() {
        CapixConfig.getInstance().load();
        getAllCapes().forEach(ModCape::reload);
    }

    public static Screen getScreen(Screen parent) {
        return new CapeSettingsScreen(parent);
    }

    public static Set<String> getAllModIds() {
        return MOD_CAPES.keySet();
    }

    public static Map<String, ModCape> getCapesForMod(String modId) {
        return MOD_CAPES.getOrDefault(modId, Map.of());
    }
}
