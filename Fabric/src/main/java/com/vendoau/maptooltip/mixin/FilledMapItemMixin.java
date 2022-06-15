package com.vendoau.maptooltip.mixin;

import com.vendoau.maptooltip.MapTooltipComponent;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(FilledMapItem.class)
public abstract class FilledMapItemMixin extends Item {

    public FilledMapItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        return MapTooltipComponent.of(stack);
    }
}