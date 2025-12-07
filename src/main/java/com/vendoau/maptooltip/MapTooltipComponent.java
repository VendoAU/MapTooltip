package com.vendoau.maptooltip;

//? >= 1.17 {

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;

//? >= 1.20
import net.minecraft.client.gui.GuiGraphics;

//? >= 1.21
import net.minecraft.core.component.DataComponents;

public class MapTooltipComponent implements ClientTooltipComponent, TooltipComponent {

    private final Integer id;

    public MapTooltipComponent(ItemStack item) {
        //? < 1.20.5 {
        /*id = MapItem.getMapId(item);
         *///?} else {
        id = item.get(DataComponents.MAP_ID).id();
        //?}
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
        //? < 1.20 {
        /*final GraphicsHelper graphics = new GraphicsHelper(pose);
         *///?} else {
        final GraphicsHelper graphics = new GraphicsHelper(guiGraphics);
        //?}
        MapTooltip.render(id, x, y, graphics);
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
//?}