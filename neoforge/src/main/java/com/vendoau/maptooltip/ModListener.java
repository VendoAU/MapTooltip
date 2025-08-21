package com.vendoau.maptooltip;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;

import java.util.function.Function;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ModListener {

    @SubscribeEvent
    public static void registerTooltip(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(MapTooltipComponent.class, Function.identity());
    }
}