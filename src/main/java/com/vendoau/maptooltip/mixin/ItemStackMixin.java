package com.vendoau.maptooltip.mixin;

import com.vendoau.maptooltip.MapTooltip;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    public abstract Item getItem();

    @Inject(method = "getTooltip", at = @At("RETURN"))
    public void getTooltip(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir) {
        if (getItem() == Items.FILLED_MAP) {
            float yOffset = 3.5f + (cir.getReturnValue().size() - 1) * 12;
            if (context.isAdvanced()) yOffset -= 8;
            MapTooltip.getInstance().setYOffset(yOffset);
        }
    }
}
