package com.vendoau.maptooltip;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.jetbrains.annotations.NotNull;

public class MapTooltipComponent implements ClientTooltipComponent, TooltipComponent {

    private final ResourceLocation bg = new ResourceLocation("textures/map/map_background.png");

    private final Integer id;
    private final MapItemSavedData data;

    public MapTooltipComponent(ItemStack map) {
        id = MapItem.getMapId(map);
        data = MapItem.getSavedData(id, Minecraft.getInstance().level);
    }

    @Override
    public void renderImage(@NotNull Font font, int x, int y, @NotNull GuiGraphics graphics) {
        final Level level = Minecraft.getInstance().level;
        if (level == null || id == null || data == null) return;

        final PoseStack poseStack = graphics.pose();

        // Background
        poseStack.pushPose();
        graphics.blit(bg, x, y, 0, 0, 64, 64, 64, 64);
        poseStack.popPose();

        // Map
        poseStack.pushPose();
        poseStack.translate(x + 3.2F, y + 3.2F, 401);
        poseStack.scale(0.45F, 0.45F, 1);
        Minecraft.getInstance().gameRenderer.getMapRenderer().render(poseStack, graphics.bufferSource(), id, data, true, 15728880);
        poseStack.popPose();
    }

    @Override
    public int getHeight() {
        return 66;
    }

    @Override
    public int getWidth(@NotNull Font font) {
        return 66;
    }
}
