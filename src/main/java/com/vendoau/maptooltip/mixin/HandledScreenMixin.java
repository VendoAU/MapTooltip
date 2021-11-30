package com.vendoau.maptooltip.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.vendoau.maptooltip.MapTooltip;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class HandledScreenMixin {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final Identifier MAP_BACKGROUND = new Identifier("textures/map/map_background.png");

    @Shadow
    @Nullable
    protected Slot focusedSlot;

    @Inject(method = "render", at = @At("TAIL"))
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (focusedSlot != null && focusedSlot.hasStack() && focusedSlot.getStack().getItem() == Items.FILLED_MAP) {
            Integer id = FilledMapItem.getMapId(focusedSlot.getStack());
            drawMap(matrices, id, mouseX, mouseY);
        }
    }

    private void drawMap(MatrixStack matrices, Integer id, int x, int y) {
        float xOffset = 11.5f;
        float yOffset = MapTooltip.getInstance().getYOffset();

        MapState mapState = FilledMapItem.getMapState(id, mc.world);

        matrices.push();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, MAP_BACKGROUND);

        matrices.translate(x + xOffset, y + yOffset, 300);
        matrices.scale(0.5f, 0.5f, 0);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        buffer.vertex(matrix4f, -7, 135, 0).texture(0, 1).next();
        buffer.vertex(matrix4f, 135, 135, 0).texture(1, 1).next();
        buffer.vertex(matrix4f, 135, -7, 0).texture(1, 0).next();
        buffer.vertex(matrix4f, -7, -7, 0).texture(0, 0).next();
        tessellator.draw();

        if (id != null && mapState != null) {
            matrices.translate(0, 0, 1);
            VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(buffer);
            mc.gameRenderer.getMapRenderer().draw(matrices, immediate, id, mapState, true, 0xF000F0);
            immediate.draw();
        }
        matrices.pop();
    }
}
