package com.vendoau.maptooltip;

import com.mojang.blaze3d.vertex.Tesselator;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

//? >= 1.21
import net.minecraft.world.level.saveddata.maps.MapId;

//? >= 1.21.2
import net.minecraft.client.renderer.state.MapRenderState;


//? >= 1.17
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;

public class MapTooltip implements ModInitializer {

    //? < 1.21 {
    /*private static final ResourceLocation MAP_SPRITE = new ResourceLocation("textures/map/map_background.png");
    *///?} else {
    private static final ResourceLocation MAP_SPRITE = ResourceLocation.withDefaultNamespace("textures/map/map_background.png");
     //?}

    @Override
    public void onInitialize() {
        //? >= 1.17 {
        TooltipComponentCallback.EVENT.register(component -> {
            if (component instanceof MapTooltipComponent mapTooltipComponent) {
                return mapTooltipComponent;
            }
            return null;
        });
        //?}
    }

    public static void render(Integer id, int x, int y, GraphicsHelper graphics) {
        if (id == null) return;

        final Minecraft mc = Minecraft.getInstance();
        final Level level = mc.level;
        if (level == null) return;

        //? < 1.21 {
        /*final MapItemSavedData data = level.getMapData("map_" + id);
        *///?} else {
        final MapItemSavedData data = level.getMapData(new MapId(id));
        //?}
        if (data == null) return;

        int offsetX = 0;
        int offsetY = -2;

        //? < 1.17 {
        /*offsetX = 8;
        offsetY = 12;
        *///?}

        // Background
        graphics.push();
        //? < 1.17
        /*graphics.translate(0, 0, 401);*/
        graphics.blit(MAP_SPRITE, x + offsetX, y + offsetY, 66, 66);
        graphics.pop();

        // Map
        graphics.push();
        graphics.translate(
                x + offsetX + 4,
                y + offsetY + 4
                //? < 1.20
                /*, 402*/
        );
        graphics.scale(0.45F);

        //? >= 1.21.2 {
        final MapRenderState mapRenderState = new MapRenderState();
        mc.getMapRenderer().extractRenderState(new MapId(id), data, mapRenderState);
        graphics.guiGraphics().submitMapRenderState(mapRenderState);
        //?} else >= 1.20 {
        /*mc.gameRenderer.getMapRenderer().render(
                graphics.pose(),
                graphics.guiGraphics().bufferSource(),
                //? < 1.21 {
                id,
                //?} else {
                /^new MapId(id),
               ^///?}
                data,
                true,
                0xf000f0);
        graphics.guiGraphics().flush();
        *///?} else >= 1.17 {
        /*final MultiBufferSource.BufferSource immediate = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        Minecraft.getInstance().gameRenderer.getMapRenderer().render(graphics.pose(), immediate, id, data, true, 0xf000f0);
        immediate.endBatch();
        *///?} else {
        /*final MultiBufferSource.BufferSource immediate = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        Minecraft.getInstance().gameRenderer.getMapRenderer().render(graphics.pose(), immediate, data, true, 0xf000f0);
        immediate.endBatch();
        *///?}

        graphics.pop();
    }
}