package com.vendoau.maptooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.jetbrains.annotations.NotNull;

public class MapTooltipComponent implements ClientTooltipComponent, TooltipComponent {

    private final ResourceLocation bg = new ResourceLocation("textures/map/map_background.png");

    private final ItemStack map;

    public MapTooltipComponent(ItemStack map) {
        this.map = map;
    }

    @Override
    public void renderImage(@NotNull Font font, int x, int y, @NotNull PoseStack poseStack, @NotNull ItemRenderer itemRenderer) {
        final Level level = Minecraft.getInstance().level;
        if (level == null) return;

        final Integer id = MapItem.getMapId(map);
        final MapItemSavedData savedData = MapItem.getSavedData(map, level);
        if (id == null || savedData == null) return;

        // Background
        poseStack.pushPose();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, bg);
        GuiComponent.blit(poseStack, x, y, 0, 0, 64, 64, 64, 64);
        poseStack.popPose();

        // Map
        poseStack.pushPose();
        poseStack.translate(x + 3.2F, y + 3.2F, 401);
        poseStack.scale(0.45F, 0.45F, 1);
        MultiBufferSource.BufferSource immediate = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        Minecraft.getInstance().gameRenderer.getMapRenderer().render(poseStack, immediate, id, savedData, true, 15728880);
        immediate.endBatch();
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
