package me.hobbyshop.basilisk.util

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import org.lwjgl.opengl.GL11

object ScissorsUtil {

    fun enable() {
        GL11.glEnable(GL11.GL_SCISSOR_TEST)
    }

    fun disable() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST)
    }

    fun select(x: Int, y: Int, width: Int, height: Int) {
        val res = ScaledResolution(Minecraft.getMinecraft())
        val scale = res.scaleFactor
        val transY = res.scaledHeight - y - height

        GL11.glScissor(x * scale, transY * scale, width * scale, height * scale)
    }

}