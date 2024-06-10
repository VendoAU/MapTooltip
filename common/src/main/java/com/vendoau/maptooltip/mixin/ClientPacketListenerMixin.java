package com.vendoau.maptooltip.mixin;

import com.vendoau.maptooltip.MapCache;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Inject(method = "handleMapItemData", at = @At(value = "TAIL"))
    private void applyToMap(ClientboundMapItemDataPacket packet, CallbackInfo ci) {
        // TODO: Find a better place to do this that only gets called when the map actually changes
        MapCache.update(packet.mapId());
    }
}
