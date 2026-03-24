package com.vendoau.maptooltip;

//? neoforge {

/*import com.mojang.datafixers.util.Either;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.function.Function;

@Mod(MapTooltipCommon.MOD_ID)
public class MapTooltipNeoforge {

    public MapTooltipNeoforge(IEventBus modEventBus) {
        modEventBus.addListener(this::registerTooltip);
        NeoForge.EVENT_BUS.addListener(this::renderTooltip);
    }

    @SubscribeEvent
    private void registerTooltip(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(MapTooltipComponent.class, Function.identity());
    }

    @SubscribeEvent
    private void renderTooltip(RenderTooltipEvent.GatherComponents event) {
        final ItemStack item = event.getItemStack();
        if (!item.is(Items.FILLED_MAP)) return;

        event.getTooltipElements().add(1, Either.right(new MapTooltipComponent(item)));
    }
}
*///? }
