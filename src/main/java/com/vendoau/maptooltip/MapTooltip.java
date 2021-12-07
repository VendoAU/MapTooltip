package com.vendoau.maptooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;

@Mod("maptooltip")
@Mod.EventBusSubscriber
public class MapTooltip {

    private static final Minecraft mc = Minecraft.getInstance();
    private static final ResourceLocation MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
    private static float yOffset;

    public MapTooltip() {
        // Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() == Items.FILLED_MAP) {
            yOffset = 3.5F + (event.getToolTip().size() - 1) * 12;
            if (event.getFlags().isAdvanced()) yOffset -= 8;
        }
    }

    @SubscribeEvent
    public static void postDrawScreen(ScreenEvent.DrawScreenEvent.Post event) {
        if (mc.player != null && mc.level != null && event.getScreen() instanceof AbstractContainerScreen<?> screen) {
            Slot slot = screen.getSlotUnderMouse();
            if (slot == null) return;
            ItemStack itemStack = slot.getItem();
            if (itemStack.getItem() == Items.FILLED_MAP) {
                Integer id = MapItem.getMapId(itemStack);
                drawMap(event.getPoseStack(), id, event.getMouseX(), event.getMouseY());
            }
        }
    }

    private static void drawMap(PoseStack matrixStack, Integer id, int x, int y) {
        float xOffset = 11.5F;

        MapItemSavedData mapState = MapItem.getSavedData(id, mc.level);

        matrixStack.pushPose();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, MAP_BACKGROUND);

        matrixStack.translate(x + xOffset, y + yOffset, 300);
        matrixStack.scale(0.5F, 0.5F, 0);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        Matrix4f matrix4f = matrixStack.last().pose();
        buffer.vertex(matrix4f, -7, 135, 0).uv(0, 1).endVertex();
        buffer.vertex(matrix4f, 135, 135, 0).uv(1, 1).endVertex();
        buffer.vertex(matrix4f, 135, -7, 0).uv(1, 0).endVertex();
        buffer.vertex(matrix4f, -7, -7, 0).uv(0, 0).endVertex();
        tesselator.end();

        if (id != null && mapState != null) {
            matrixStack.translate(0, 0, 1);
            MultiBufferSource.BufferSource immediate = MultiBufferSource.immediate(buffer);
            mc.gameRenderer.getMapRenderer().render(matrixStack, immediate, id, mapState, true, 0xF000F0);
            immediate.endBatch();
        }
        matrixStack.popPose();
    }
}
