package com.vendoau.maptooltip;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;

public class MapTooltip implements ModInitializer {

    @Override
    public void onInitialize() {
        TooltipComponentCallback.EVENT.register(MapTooltip::renderTooltip);
    }

    public static TooltipComponent renderTooltip(TooltipData data) {
        if (data instanceof MapTooltipData mapTooltipData)
        {
            return new MapTooltipComponent(mapTooltipData);
        }
        return null;
    }
}
