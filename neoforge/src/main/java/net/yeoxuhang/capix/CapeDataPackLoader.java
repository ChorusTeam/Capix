package net.yeoxuhang.capix;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.yeoxuhang.capix.api.CapixApi;

import java.util.Map;

public class CapeDataPackLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new Gson();

    public CapeDataPackLoader() {
        super(GSON, "capes");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager resourceManager, ProfilerFiller profiler) {
        for (Map.Entry<ResourceLocation, JsonElement> entry : jsonMap.entrySet()) {
            try {
                JsonObject json = GsonHelper.convertToJsonObject(entry.getValue(), "cape");

                String name = GsonHelper.getAsString(json, "name");
                String texture = GsonHelper.getAsString(json, "texture");
                String modId = entry.getKey().getNamespace();
                System.out.println(name);
                System.out.println(texture.replace(modId + ":", ""));
                System.out.println(modId);
                CapixApi.registerCape(modId, name, texture.replace(modId + ":", ""), null);
                CapixApi.reload();
            } catch (Exception e) {
                System.err.println("[Capix] Failed to parse cape " + entry.getKey() + ": " + e.getMessage());
            }
        }
    }
}
