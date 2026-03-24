package com.vendoau.maptooltip;

//? >= 1.20 {
import net.minecraft.client.gui.GuiGraphicsExtractor;
//? } else {
/*import com.mojang.blaze3d.vertex.GuiGraphicsExtractor;
*///? }

public class GraphicsHelper {

    public static void push(GuiGraphicsExtractor graphics) {
        //? >= 1.21.6 {
        graphics.pose().pushMatrix();
        //? } else >= 1.20 {
        /*graphics.pose().pushPose();
        *///? } else {
        /*graphics.pushPose();
        *///? }
    }

    public static void pop(GuiGraphicsExtractor graphics) {
        //? >= 1.21.6 {
        graphics.pose().popMatrix();
        //? } else >= 1.20 {
        /*graphics.pose().popPose();
        *///? } else {
        /*graphics.popPose();
        *///? }
    }

    public static void translate(float x, float y, GuiGraphicsExtractor graphics) {
        //? >= 1.20 {
        graphics.pose().translate(
        //? } else {
        /*graphics.translate(
        *///? }
                x,
                y
                //? < 1.21.6
                //, 1
        );
    }

    public static void scale(float s, GuiGraphicsExtractor graphics) {
        //? >= 1.20 {
        graphics.pose().scale(
        //? } else {
        /*graphics.scale(
        *///? }
                s,
                s
                //? < 1.21.6
                //, 1
        );
    }
}
