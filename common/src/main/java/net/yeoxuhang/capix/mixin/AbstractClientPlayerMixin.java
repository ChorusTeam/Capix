package net.yeoxuhang.capix.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.yeoxuhang.capix.api.CapixApi;
import net.yeoxuhang.capix.config.CapixManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends Player {

    private AbstractClientPlayerMixin(ClientLevel pClientLevel, GameProfile pGameProfile) {
        super(pClientLevel, pClientLevel.getSharedSpawnPos(), pClientLevel.getSharedSpawnAngle(), pGameProfile);
        CapixApi.reload();
    }

    @ModifyReturnValue(method = "getSkin", at = @At("TAIL"))
    private PlayerSkin injectCustomCape(PlayerSkin playerSkin) {
        String name = this.getUUID().toString();
        ResourceLocation customCape = CapixManager.getCapeForPlayer(name);
        return new PlayerSkin(playerSkin.texture(), playerSkin.textureUrl(), customCape == null ? playerSkin.capeTexture() : customCape, customCape == null ? playerSkin.capeTexture() : customCape, playerSkin.model(), true);
    }
}