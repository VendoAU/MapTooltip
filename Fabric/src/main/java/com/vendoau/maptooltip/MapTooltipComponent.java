package com.vendoau.maptooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.TooltipData;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapState;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Optional;

public class MapTooltipComponent implements TooltipComponent {

    private final Identifier bg = new Identifier("textures/map/map_background.png");

    private final MapTooltipData data;

    public MapTooltipComponent(MapTooltipData data) {
        this.data = data;
    }

    public static Optional<TooltipData> of(ItemStack map) {
        final Integer id = FilledMapItem.getMapId(map);
        final MapState state = FilledMapItem.getMapState(id, MinecraftClient.getInstance().world);
        return Optional.of(new MapTooltipData(id, state));
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, MatrixStack matrices, ItemRenderer itemRenderer) {
        final World world = MinecraftClient.getInstance().world;
        if (world == null) return;

        final Integer id = data.id();
        final MapState state = data.state();
        if (id == null || state == null) return;

        // Background
        matrices.push();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, bg);
        DrawableHelper.drawTexture(matrices, x, y, 0, 0, 64, 64, 64, 64);
        matrices.pop();

        // Map
        matrices.push();
        matrices.translate(x + 3.2F, y + 3.2F, 401);
        matrices.scale(0.45F, 0.45F, 1);
        VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        MinecraftClient.getInstance().gameRenderer.getMapRenderer().draw(matrices, immediate, id, state, true, 15728880);
        immediate.draw();
        matrices.pop();
    }

    @Override
    public int getHeight() {
        return 66;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return 66;
    }
}
