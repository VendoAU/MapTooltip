package com.vendoau.maptooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

//? < 1.20
/*import net.minecraft.client.gui.GuiComponent;*/

//? >= 1.20
import net.minecraft.client.gui.GuiGraphics;

//? > 1.21.2 {
import net.minecraft.client.renderer.RenderPipelines;
import org.joml.Matrix3x2fStack;
//?}

public class GraphicsHelper {

    //? >= 1.20
    private final GuiGraphics guiGraphics;

    //? < 1.21.2 {
    /*private final PoseStack pose;
    *///?} else {
    private final Matrix3x2fStack pose;
     //?}

    //? < 1.20 {
    /*public GraphicsHelper(PoseStack pose) {
        this.pose = pose;
    }
    *///?} else {
    public GraphicsHelper(GuiGraphics guiGraphics) {
        this.guiGraphics = guiGraphics;
        pose = guiGraphics.pose();
    }
    //?}

    public void push() {
        //? < 1.21.2 {
        /*pose.pushPose();
        *///?} else {
        pose.pushMatrix();
         //?}
    }

    public void pop() {
        //? < 1.21.2 {
        /*pose.popPose();
        *///?} else {
        pose.popMatrix();
         //?}
    }

    public void translate(float x, float y) {
        translate(x, y, 1);
    }

    public void translate(float x, float y, float z) {
        pose.translate(
                x,
                y
                //? < 1.21.2
                /*, z*/
        );
    }

    public void scale(float scale) {
        scale(scale, scale);
    }

    public void scale(float x, float y) {
        pose.scale(
                x,
                y
                //? < 1.21.2
                /*, 1*/
        );
    }

    public void blit(ResourceLocation sprite, int x, int y, int width, int height) {
        //? < 1.20 {
        /*RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, sprite);
        GuiComponent.blit(pose, x, y, 0, 0, width, height, width, height);
        *///?} else {
        push();
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
        //?}
    }
}
