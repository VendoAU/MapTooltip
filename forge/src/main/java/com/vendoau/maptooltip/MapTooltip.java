package com.vendoau.maptooltip;

import com.mojang.datafixers.util.Either;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;

@Mod(Constants.MOD_ID)
@Mod.EventBusSubscriber
public class MapTooltip {

    public MapTooltip() {
        // Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }

    @SubscribeEvent
    public static void renderTooltip(RenderTooltipEvent.GatherComponents event) {
        final ItemStack item = event.getItemStack();
        if (!item.is(Items.FILLED_MAP)) return;

        final var elements = event.getTooltipElements();
        elements.add(1, Either.right(new MapTooltipComponent(item)));
    }
}