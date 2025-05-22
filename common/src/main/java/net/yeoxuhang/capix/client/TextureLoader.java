package net.yeoxuhang.capix.client;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TextureLoader {
    private static final Map<String, ResourceLocation> CACHE = new HashMap<>();

    public static ResourceLocation loadFromUrl(String url, String modId) {
        return CACHE.computeIfAbsent(url, u -> {
            try (InputStream in = new URL(u).openStream()) {
                NativeImage image = NativeImage.read(in);
                DynamicTexture dynamicTexture = new DynamicTexture(image);
                ResourceLocation id = ResourceLocation.tryBuild(modId, "cape/" + Integer.toHexString(u.hashCode()));
                Minecraft.getInstance().getTextureManager().register(id, dynamicTexture);
                return id;
            } catch (Exception e) {
                e.printStackTrace();
                return null; // fallback
            }
        });}
}
