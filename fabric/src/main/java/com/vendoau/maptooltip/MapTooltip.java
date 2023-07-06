package com.vendoau.maptooltip;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public class MapTooltip implements ModInitializer {

    @Override
    public void onInitialize() {
        TooltipComponentCallback.EVENT.register(this::renderTooltip);
    }

    private ClientTooltipComponent renderTooltip(TooltipComponent component) {
        if (component instanceof MapTooltipComponent mapTooltipComponent) {
            return mapTooltipComponent;
        }
        return null;
    }
}
