package com.vendoau.maptooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

//? >= 1.21.11 {
import net.minecraft.resources.Identifier;
//? } else {
/*import net.minecraft.resources.ResourceLocation;
*///? }

//? >= 1.21.6 {
import net.minecraft.client.renderer.RenderPipelines;
//? } else {
/*import net.minecraft.client.renderer.RenderType;
*///? }

//? >= 1.21.3 {
import net.minecraft.client.renderer.state.MapRenderState;
import net.minecraft.client.renderer.MapRenderer;
//? }

//? >= 1.20.5
import net.minecraft.world.level.saveddata.maps.MapId;

//? >= 1.20 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//? } else {
/*import com.mojang.blaze3d.vertex.GuiGraphicsExtractor;
import net.minecraft.client.gui.GuiComponent;
*///? }

public class MapTooltip {

    //? >= 1.21.11 {
    private static final Identifier MAP_SPRITE = Identifier.withDefaultNamespace("textures/map/map_background.png");
    //? } else >= 1.21 {
    /*private static final ResourceLocation MAP_SPRITE = ResourceLocation.withDefaultNamespace("textures/map/map_background.png");
    *///? } else {
    /*private static final ResourceLocation MAP_SPRITE = new ResourceLocation("textures/map/map_background.png");
    *///? }


    public static void render(Integer id, int x, int y, GuiGraphicsExtractor graphics) {
        if (id == null) return;

        final Minecraft mc = Minecraft.getInstance();
        final Level level = mc.level;
        if (level == null) return;

        //? >= 1.20.5 {
        final MapItemSavedData data = level.getMapData(new MapId(id));
        //? } else {
        /*final MapItemSavedData data = level.getMapData("map_" + id);
        *///? }
        if (data == null) return;

        int offsetX = 0;
        int offsetY = -2;

        renderBackground(x + offsetX, y + offsetY, graphics);
        renderMap(id, data, x + offsetX + 4, y + offsetY + 4, graphics);
    }

    public static void renderBackground(int x, int y, GuiGraphicsExtractor graphics) {
        GraphicsHelper.push(graphics);

        //? >= 1.21.6 {
        graphics.blit(RenderPipelines.GUI_TEXTURED, MAP_SPRITE, x, y, 0, 0, 66, 66, 66, 66);
        //? } else >= 1.21.3 {
        /*graphics.blit(RenderType::guiTextured, MAP_SPRITE, x, y, 0, 0, 66, 66, 66, 66);
        *///? } else >= 1.20 {
        /*graphics.blit(MAP_SPRITE, x, y, 0, 0, 66, 66, 66, 66);
        *///? } else >= 1.17 {
        /*RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, MAP_SPRITE);
        GuiComponent.blit(graphics, x, y, 0, 0, 66, 66, 66, 66);
        *///? } else {
        /*Minecraft.getInstance().getTextureManager().bind(MAP_SPRITE);
        GuiComponent.blit(graphics, x, y, 0, 0, 66, 66, 66, 66);
        *///? }

        GraphicsHelper.pop(graphics);
    }

    public static void renderMap(Integer id, MapItemSavedData data, int x, int y, GuiGraphicsExtractor graphics) {
        final Minecraft mc = Minecraft.getInstance();

        GraphicsHelper.push(graphics);
        GraphicsHelper.translate(x, y, graphics);
        GraphicsHelper.scale(0.45F, graphics);

        //? > 1.21.11 {
        final MapRenderState mapRenderState = new MapRenderState();
        mc.getMapRenderer().extractRenderState(new MapId(id), data, mapRenderState);
        graphics.map(mapRenderState);
        //? } else >= 1.21.6 {
        /*final MapRenderState mapRenderState = new MapRenderState();
        mc.getMapRenderer().extractRenderState(new MapId(id), data, mapRenderState);
        graphics.submitMapRenderState(mapRenderState);
        *///? } else >= 1.21.3 {
        /*final MapRenderer mapRenderer = mc.getMapRenderer();
        final MapRenderState mapRenderState = new MapRenderState();
        mapRenderer.extractRenderState(new MapId(id), data, mapRenderState);
        graphics.drawSpecial((multiBufferSource) -> mapRenderer.render(mapRenderState, graphics.pose(), multiBufferSource, true, 0xf000f0));
        *///? } else >= 1.20.5 {
        /*mc.gameRenderer.getMapRenderer().render(graphics.pose(), graphics.bufferSource(), new MapId(id), data, true, 0xf000f0);
        *///? } else >= 1.20 {
        /*mc.gameRenderer.getMapRenderer().render(graphics.pose(), graphics.bufferSource(), id, data, true, 0xf000f0);
        *///? } else >= 1.18 {
        /*final MultiBufferSource.BufferSource immediate = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        Minecraft.getInstance().gameRenderer.getMapRenderer().render(graphics, immediate, id, data, true, 0xf000f0);
        immediate.endBatch();
        *///? } else >= 1.17 {
        /*final MultiBufferSource.BufferSource immediate = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        Minecraft.getInstance().gameRenderer.getMapRenderer().render(graphics, immediate, id, data, true, 0xf000f0);
        immediate.endBatch();
        *///? } else {
        /*final MultiBufferSource.BufferSource immediate = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        Minecraft.getInstance().gameRenderer.getMapRenderer().render(graphics, immediate, data, true, 0xf000f0);
        immediate.endBatch();
        *///? }

        GraphicsHelper.pop(graphics);
    }
}