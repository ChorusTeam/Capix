package net.yeoxuhang.capix.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.yeoxuhang.capix.config.CapixManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends Player {
    @Shadow
    protected abstract PlayerInfo getPlayerInfo();

    private AbstractClientPlayerMixin(ClientLevel pClientLevel, GameProfile pGameProfile) {
        super(pClientLevel, pClientLevel.getSharedSpawnPos(), pClientLevel.getSharedSpawnAngle(), pGameProfile);
    }

    @Inject(method = "getSkin", at = @At("HEAD"), cancellable = true)
    private void injectCustomCape(CallbackInfoReturnable<PlayerSkin> cir) {
        String name = this.getGameProfile().getName();
        ResourceLocation customCape = CapixManager.getCapeForPlayer(name);
        if (this.getPlayerInfo() != null){
            PlayerSkin playerSkin = getPlayerInfo().getSkin();
            if (customCape != null) {
                cir.setReturnValue(new PlayerSkin(playerSkin.texture(), playerSkin.textureUrl(), customCape, customCape, playerSkin.model(), playerSkin.secure()));
            }
        }
    }
}