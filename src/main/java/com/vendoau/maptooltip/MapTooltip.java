package com.vendoau.maptooltip;

import net.fabricmc.api.ModInitializer;

public class MapTooltip implements ModInitializer {

    private static MapTooltip instance;

    private float yOffset;

    @Override
    public void onInitialize() {
        instance = this;
    }

    public static MapTooltip getInstance() {
        return instance;
    }

    public float getYOffset() {
        return yOffset;
    }

    public void setYOffset(float yOffset) {
        this.yOffset = yOffset;
    }
}
