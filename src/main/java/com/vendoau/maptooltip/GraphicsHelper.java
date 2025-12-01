package com.vendoau.maptooltip;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

//? > 1.21.2
import net.minecraft.client.renderer.RenderPipelines;

public class GraphicsHelper {

    private final GuiGraphics guiGraphics;

    public GraphicsHelper(GuiGraphics guiGraphics) {
        this.guiGraphics = guiGraphics;
    }

    public void push() {
        //? < 1.21.2 {
        /*guiGraphics.pose().pushPose();
         *///?} else {
        guiGraphics.pose().pushMatrix();
        //?}
    }

    public void pop() {
        //? < 1.21.2 {
        /*guiGraphics.pose().popPose();
         *///?} else {
        guiGraphics.pose().popMatrix();
        //?}
    }

    public void translate(float x, float y) {
        guiGraphics.pose().translate(
                x,
                y
                //? < 1.21.2
                /*, 1*/
        );
    }

    public void scale(float scale) {
        scale(scale, scale);
    }

    public void scale(float x, float y) {
        guiGraphics.pose().scale(
                x,
                y
                //? < 1.21.2
                /*, 1*/
        );
    }

    public void blit(ResourceLocation sprite, int x, int y, int width, int height) {
        guiGraphics.blit(
                //? >= 1.21.2
                RenderPipelines.GUI_TEXTURED,
                sprite,
                x,
                y,
                0,
                0,
                width,
                height,
                width,
                height
        );
    }
}
