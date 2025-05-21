package net.yeoxuhang.capix.config;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.yeoxuhang.capix.api.CapixApi;
import net.yeoxuhang.capix.api.ModCape;
import net.yeoxuhang.capix.platform.Services;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CapixConfig {
    private static final File CONFIG_FILE = new File(Services.PLATFORM.configFile(), "capix.json");
    private static CapixConfig INSTANCE;
    private final Map<String, Map<String, Boolean>> configData = new HashMap<>();

    public static CapixConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CapixConfig();
            INSTANCE.load();
        }
        return INSTANCE;
    }

    public void load() {
        if (!CONFIG_FILE.exists()) {
            save();
            return;
        }
        try (Reader reader = new FileReader(CONFIG_FILE)) {
            Type type = new TypeToken<Map<String, Map<String, Boolean>>>() {}.getType();
            Map<String, Map<String, Boolean>> map = new Gson().fromJson(reader, type);
            configData.clear();
            configData.putAll(map);
        } catch (IOException ignored) {}

        for (String modId : CapixApi.getAllModIds()) {
            Map<String, ModCape> capes = CapixApi.getCapesForMod(modId);
            configData.computeIfAbsent(modId, k -> new HashMap<>());
            Map<String, Boolean> capeMap = configData.get(modId);
            for (String capeName : capes.keySet()) {
                capeMap.putIfAbsent(capeName, true);
            }
        }
        save();
    }

    public void save() {
        try (Writer writer = new FileWriter(CONFIG_FILE)) {
            new GsonBuilder().setPrettyPrinting().create().toJson(configData, writer);
        } catch (IOException ignored) {}
    }

    public boolean isCapeEnabled(String modId, String capeName) {
        return configData.getOrDefault(modId, Map.of()).getOrDefault(capeName, true);
    }

    public void setCapeEnabled(String modId, String capeName, boolean enabled) {
        configData.computeIfAbsent(modId, k -> new HashMap<>()).put(capeName, enabled);
        save();
    }

    public void setCapeEnabledIfAbsent(String modId, String capeName, boolean enabled) {
        configData.computeIfAbsent(modId, k -> new HashMap<>()).putIfAbsent(capeName, enabled);
        save();
    }

    public Set<String> getAllMods() {
        return configData.keySet();
    }

    public void clearAllCapeStates() {
        for (String modId : configData.keySet()) {
            Map<String, Boolean> capeStates = getCapeStatesForMod(modId);
            for (String capeName : capeStates.keySet()) {
                setCapeEnabled(modId, capeName, false);
            }
        }
    }


    public Map<String, Boolean> getCapeStatesForMod(String modId) {
        return configData.getOrDefault(modId, Map.of());
    }
}
