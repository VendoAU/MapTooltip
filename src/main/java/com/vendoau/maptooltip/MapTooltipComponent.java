package com.vendoau.maptooltip;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

//? >= 1.20
import net.minecraft.client.gui.GuiGraphics;

//? >= 1.21 {
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.saveddata.maps.MapId;
//?}

//? >= 1.21.2
import net.minecraft.client.renderer.state.MapRenderState;

// TODO: Move this to common
public class MapTooltipComponent implements ClientTooltipComponent, TooltipComponent {

    //? < 1.21 {
    /*private final ResourceLocation MAP_SPRITE = new ResourceLocation("textures/map/map_background.png");
    *///?} else {
    private final ResourceLocation MAP_SPRITE = ResourceLocation.withDefaultNamespace("textures/map/map_background.png");
     //?}

    private final Minecraft mc;
    private final Integer id;
    //? >= 1.21.2
    private final MapRenderState mapRenderState;

    public MapTooltipComponent(ItemStack item) {
        mc = Minecraft.getInstance();
        //? < 1.20.5 {
        /*id = MapItem.getMapId(item);
        *///?} else {
        id = item.get(DataComponents.MAP_ID).id();
         //?}
        //? >= 1.21.2
        mapRenderState = new MapRenderState();
    }

    @Override
    public void renderImage(
            //? >= 1.21.2 {
            Font font, int x, int y, int i1, int i2, GuiGraphics guiGraphics
             //?} else >= 1.20 {
            /*Font font, int x, int y, GuiGraphics guiGraphics
             *///?} else >= 1.18 {
            /*Font font, int x, int y, PoseStack pose, ItemRenderer itemRenderer, int i1
            *///?} else {
            /*Font font, int x, int y, PoseStack pose, ItemRenderer itemRenderer, int i1, TextureManager textureManager
            *///?}
    ) {
        if (id == null) return;

        final Level level = mc.level;
        if (level == null) return;

        //? < 1.20.5 {
        /*final MapItemSavedData data = MapItem.getSavedData(id, level);
        *///?} else {
        final MapItemSavedData data = level.getMapData(new MapId(id));
         //?}
        if (data == null) return;

        //? < 1.20 {
        /*final GraphicsHelper graphics = new GraphicsHelper(pose);
        *///?} else {
        final GraphicsHelper graphics = new GraphicsHelper(guiGraphics);
         //?}

        // Background
        graphics.blit(MAP_SPRITE, x, y, 66, 66);

        // Map
        graphics.push();
        graphics.translate(
                x + 4,
                y + 4
                //? < 1.20
                /*, 401*/
        );
        graphics.scale(0.45F);
        //? < 1.20 {
        /*MultiBufferSource.BufferSource immediate = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        Minecraft.getInstance().gameRenderer.getMapRenderer().render(pose, immediate, id, data, true, 0xf000f0);
        immediate.endBatch();
        *///?} else < 1.21.2 {
        /*mc.gameRenderer.getMapRenderer()
                .render(guiGraphics.pose(), guiGraphics.bufferSource(), id, data, true, 0xf000f0);
        guiGraphics.flush();
        *///?} else {
        mc.getMapRenderer().extractRenderState(new MapId(id), data, mapRenderState);
        guiGraphics.submitMapRenderState(mapRenderState);
        //?}
        graphics.pop();
    }

    @Override
    public int getHeight(
            //? >= 1.21.2
            Font font
    ) {
        return 66;
    }

    @Override
    public int getWidth(Font font) {
        return 66;
    }
}
