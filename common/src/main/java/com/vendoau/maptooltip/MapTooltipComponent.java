package com.vendoau.maptooltip;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MapRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.state.MapRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.jetbrains.annotations.NotNull;

public class MapTooltipComponent implements ClientTooltipComponent, TooltipComponent {

    private final ResourceLocation bg = ResourceLocation.parse("textures/map/map_background.png");
    private final MapId id;
    private final MapRenderState mapRenderState;

    public MapTooltipComponent(ItemStack map) {
        id = map.get(DataComponents.MAP_ID);
        mapRenderState = new MapRenderState();
    }

    @Override
    public void renderImage(@NotNull Font font, int x, int y, int p_368529_, int p_368584_,
                            @NotNull GuiGraphics graphics) {
        final PoseStack poseStack = graphics.pose();

        // Background
        poseStack.pushPose();
        graphics.blit(RenderType::guiTextured, bg, x, y, 0, 0, 64, 64, 64, 64);
        poseStack.popPose();

        final MapItemSavedData data = Minecraft.getInstance().level.getMapData(id);
        if (data == null) return;

        // Map
        poseStack.pushPose();
        poseStack.translate(x + 3.2F, y + 3.2F, 401);
        poseStack.scale(0.45F, 0.45F, 1);
        final MapRenderer mapRenderer = Minecraft.getInstance().getMapRenderer();
        mapRenderer.extractRenderState(id, data, this.mapRenderState);
        graphics.drawSpecial(buffer -> mapRenderer.render(mapRenderState, poseStack, buffer, true, 0xF000F0));
        poseStack.popPose();
    }

    @Override
    public int getHeight(@NotNull Font font) {
        return 66;
    }

    @Override
    public int getWidth(@NotNull Font font) {
        return 66;
    }
}
