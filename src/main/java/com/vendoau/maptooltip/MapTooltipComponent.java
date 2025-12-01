package com.vendoau.maptooltip;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

//? if >= 1.21 {
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.saveddata.maps.MapId;
import com.mojang.blaze3d.vertex.PoseStack;
//?}

//? if >= 1.21.2 {
import net.minecraft.client.renderer.state.MapRenderState;
import org.joml.Matrix3x2fStack;
import net.minecraft.client.renderer.RenderPipelines;
//?}

// TODO: Move this to common
public class MapTooltipComponent implements ClientTooltipComponent, TooltipComponent {

    private static final ResourceLocation MAP_SPRITE = ResourceLocation.withDefaultNamespace("container/cartography_table/map");

    private final Minecraft mc;
    //? if < 1.20.5 {
    /*private final Integer id;
    *///?} else {
    private final MapId id;
    //?}
    //? >= 1.21.2
    private final MapRenderState mapRenderState;

    public MapTooltipComponent(ItemStack item) {
        mc = Minecraft.getInstance();
        //? if < 1.20.5 {
        /*id = MapItem.getMapId(item);
        *///?} else {
        id = item.get(DataComponents.MAP_ID);
        //?}
        //? >= 1.21.2
        mapRenderState = new MapRenderState();
    }

    //? if < 1.21.2 {
    /*@Override
    public void renderImage(Font font, int x, int y, GuiGraphics graphics) {
        final Level level = mc.level;
        if (level == null) return;

        final MapItemSavedData data = level.getMapData(id);
        if (data == null) return;

        final PoseStack pose = graphics.pose();

        // Background
        graphics.blitSprite(MAP_SPRITE, x, y, 66, 66);

        // Map
        graphics.pose().pushPose();
        graphics.pose().translate(x + 4, y + 4, 1);
        graphics.pose().scale(0.45F, 0.45F, 1);
        mc.gameRenderer.getMapRenderer().render(pose, graphics.bufferSource(), id, data, true, 0xf000f0);
        graphics.flush();
        graphics.pose().popPose();

    }
    *///?} else {
    @Override
    public void renderImage(Font font, int x, int y, int i1, int i2, GuiGraphics guiGraphics) {
        final Level level = mc.level;
        if (level == null) return;

        final MapItemSavedData data = level.getMapData(id);
        if (data == null) return;

        final Matrix3x2fStack pose = guiGraphics.pose();

        // Background
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, MAP_SPRITE, x, y, 66, 66);

        // Map
        pose.pushMatrix();
        pose.translate(x + 4, y + 4);
        pose.scale(0.45F, 0.45F);
        mc.getMapRenderer().extractRenderState(id, data, mapRenderState);
        guiGraphics.submitMapRenderState(mapRenderState);
        pose.popMatrix();
    }
    //?}

    //? if < 1.21.2 {
    /*@Override
    public int getHeight() {
        return 66;
    }
    *///?} else {
    @Override
    public int getHeight(Font font) {
        return 66;
    }
    //?}


    @Override
    public int getWidth(Font font) {
        return 66;
    }
}
