package com.vendoau.maptooltip;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapTooltip implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("template");
    public static final String VERSION = /*$ mod_version*/ "5.0.0";
    public static final String MINECRAFT = /*$ minecraft*/ "1.21.10";

    @Override
    public void onInitialize() {
        TooltipComponentCallback.EVENT.register(component -> {
            if (component instanceof MapTooltipComponent mapTooltipComponent) {
                return mapTooltipComponent;
            }
            return null;
        });
    }
}