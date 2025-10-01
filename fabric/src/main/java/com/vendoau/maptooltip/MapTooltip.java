package com.vendoau.maptooltip;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public class MapTooltip implements ModInitializer {

    @Override
    public void onInitialize() {
        TooltipComponentCallback.EVENT.register(this::renderTooltip);
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            client.execute(() -> MapCache.load(handler.getServerData(), handler.getLevel()));
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            MapCache.save(handler.getServerData(), handler.getLevel());
        });
    }

    private ClientTooltipComponent renderTooltip(TooltipComponent component) {
        if (component instanceof MapTooltipComponent mapTooltipComponent) {
            return mapTooltipComponent;
        }
        return null;
    }
}
