package com.vendoau.maptooltip;

import com.mojang.datafixers.util.Either;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.event.TickEvent;

@Mod(Constants.MOD_ID)
@EventBusSubscriber(modid = Constants.MOD_ID)
public class MapTooltip {

    private static boolean loadNextTick;

    @SubscribeEvent
    public static void renderTooltip(RenderTooltipEvent.GatherComponents event) {
        final ItemStack item = event.getItemStack();
        if (!item.is(Items.FILLED_MAP)) return;

        final var elements = event.getTooltipElements();
        elements.add(1, Either.right(new MapTooltipComponent(item)));
    }

    @SubscribeEvent
    public static void joinServer(ClientPlayerNetworkEvent.LoggingIn event) {
        loadNextTick = true;
    }

    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent event) {
        if (loadNextTick) {
            final Minecraft mc = Minecraft.getInstance();
            MapCache.load(mc.getCurrentServer(), mc.level);
            loadNextTick = false;
        }
    }

    @SubscribeEvent
    public static void leaveServer(ClientPlayerNetworkEvent.LoggingOut event) {
        final Minecraft mc = Minecraft.getInstance();
        MapCache.save(mc.getCurrentServer(), mc.level);
    }
}