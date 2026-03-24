package com.vendoau.maptooltip.mixin;

//? < 1.17 {

/*import com.mojang.blaze3d.vertex.GuiGraphicsExtractor;
import com.vendoau.maptooltip.MapTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin {

    @Shadow
    @Nullable
    protected Slot hoveredSlot;

    @Inject(method = "render", at = @At("TAIL"))
    private void render(GuiGraphicsExtractor poseStack, int x, int y, float delta, CallbackInfo ci) {
        if (hoveredSlot == null) return;

        final Level level = Minecraft.getInstance().level;
        if (level == null) return;

        final ItemStack hoveredItem = hoveredSlot.getItem();
        if (hoveredItem.getItem() == Items.FILLED_MAP) {
            final Integer id = MapItem.getMapId(hoveredItem);
            MapTooltip.render(id, x, y, poseStack);
        }
    }
}
*///?}