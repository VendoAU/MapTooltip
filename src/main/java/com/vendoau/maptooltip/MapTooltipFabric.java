package com.vendoau.maptooltip;

import net.fabricmc.api.ModInitializer;

//? >= 1.17
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;

public class MapTooltipFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        //? >= 1.17 {
        TooltipComponentCallback.EVENT.register(component -> {
            if (component instanceof MapTooltipComponent mapTooltipComponent) {
                return mapTooltipComponent;
            }
            return null;
        });
        //?}
    }
}
