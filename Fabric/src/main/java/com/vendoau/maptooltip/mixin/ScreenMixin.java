package com.vendoau.maptooltip.mixin;

import com.vendoau.maptooltip.MapTooltipComponent;
import com.vendoau.maptooltip.MapTooltipData;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Screen.class)
public class ScreenMixin {

    @Inject(method = "method_32635", at = @At("HEAD"), cancellable = true)
    private static void something(List<TooltipComponent> list, TooltipData data, CallbackInfo ci) {
        if (data instanceof MapTooltipData mapTooltipData) {
            list.add(1, new MapTooltipComponent(mapTooltipData));
            ci.cancel();
        }
    }
}