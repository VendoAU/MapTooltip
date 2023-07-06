package com.vendoau.maptooltip.mixin;

import com.vendoau.maptooltip.MapTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(MapItem.class)
public class MapItemMixin extends Item {

    public MapItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack item) {
        return Optional.of(new MapTooltipComponent(item));
    }
}
