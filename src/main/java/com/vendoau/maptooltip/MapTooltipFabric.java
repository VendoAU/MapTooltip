package com.vendoau.maptooltip;

//? fabric {
import net.fabricmc.api.ModInitializer;

//? >= 1.17
import net.fabricmc.fabric.api.client.rendering.v1.ClientTooltipComponentCallback;

public class MapTooltipFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        //? >= 1.17 {
        ClientTooltipComponentCallback.EVENT.register(component -> {
            if (component instanceof MapTooltipComponent mapTooltipComponent) {
                return mapTooltipComponent;
            }
            return null;
        });
        //?}
    }
}
//? }
