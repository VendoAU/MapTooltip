package com.vendoau.maptooltip;

//? >= 1.20 {
import net.minecraft.client.gui.GuiGraphics;
//? } else {
/*import com.mojang.blaze3d.vertex.GuiGraphics;
*///? }

public class GraphicsHelper {

    public static void push(GuiGraphics graphics) {
        //? >= 1.21.6 {
        graphics.pose().pushMatrix();
        //? } else >= 1.20 {
        /*graphics.pose().pushPose();
        *///? } else {
        /*graphics.pushPose();
        *///? }
    }

    public static void pop(GuiGraphics graphics) {
        //? >= 1.21.6 {
        graphics.pose().popMatrix();
        //? } else >= 1.20 {
        /*graphics.pose().popPose();
        *///? } else {
        /*graphics.popPose();
        *///? }
    }

    public static void translate(float x, float y, GuiGraphics graphics) {
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

    public static void scale(float s, GuiGraphics graphics) {
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
