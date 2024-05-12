package com.vendoau.maptooltip;

import com.mojang.datafixers.util.Either;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

@Mod(Constants.MOD_ID)
@EventBusSubscriber
public class MapTooltip {

    @SubscribeEvent
    public static void renderTooltip(RenderTooltipEvent.GatherComponents event) {
        final ItemStack item = event.getItemStack();
        if (!item.is(Items.FILLED_MAP)) return;

        final var elements = event.getTooltipElements();
        elements.add(1, Either.right(new MapTooltipComponent(item)));
    }
}