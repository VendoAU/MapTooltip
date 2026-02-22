package com.vendoau.maptooltip;

//? >= 1.17 {

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import org.jetbrains.annotations.NotNull;

//? >= 1.21
import net.minecraft.core.component.DataComponents;

//? >= 1.20 {
import net.minecraft.client.gui.GuiGraphics;
//? } else {
/*import com.mojang.blaze3d.vertex.GuiGraphics;
*///? }

public class MapTooltipComponent implements ClientTooltipComponent, TooltipComponent {

    private final Integer id;

    public MapTooltipComponent(ItemStack item) {
        //? >= 1.20.5 {
        id = item.get(DataComponents.MAP_ID).id();
        //? } else {
        /*id = MapItem.getMapId(item);
        *///? }
    }

    @Override
    public void renderImage(
            //? >= 1.21.2 {
            @NotNull Font font, int x, int y, int i1, int i2, @NotNull GuiGraphics graphics
            //?} else >= 1.20 {
            /*Font font, int x, int y, GuiGraphics graphics
             *///?} else >= 1.18 {
            /*Font font, int x, int y, GuiGraphics graphics, ItemRenderer itemRenderer, int i1
            *///?} else {
            /*Font font, int x, int y, GuiGraphics graphics, ItemRenderer itemRenderer, int i1, TextureManager textureManager
             *///?}
    ) {
        MapTooltip.render(id, x, y, graphics);
    }

    @Override
    public int getHeight(
            //? >= 1.21.2
            @NotNull Font font
    ) {
        return 66;
    }

    @Override
    public int getWidth(@NotNull Font font) {
        return 66;
    }
}
//?}