package me.hobbyshop.basilisk.util.gui

import me.hobbyshop.basilisk.util.MathUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import org.lwjgl.opengl.GL11
import java.awt.Color


object GuiUtils {

    fun drawRoundedRect(x: Int, y: Int, width: Int, height: Int, cornerRadius: Int, color: Color) {
        Gui.drawRect(x, y + cornerRadius, x + cornerRadius, y + height - cornerRadius, color.getRGB())
        Gui.drawRect(x + cornerRadius, y, x + width - cornerRadius, y + height, color.getRGB())
        Gui.drawRect(x + width - cornerRadius, y + cornerRadius, x + width, y + height - cornerRadius, color.getRGB())
        drawArc(x + cornerRadius, y + cornerRadius, cornerRadius, 0, 90, color)
        drawArc(x + width - cornerRadius, y + cornerRadius, cornerRadius, 270, 360, color)
        drawArc(x + width - cornerRadius, y + height - cornerRadius, cornerRadius, 180, 270, color)
        drawArc(x + cornerRadius, y + height - cornerRadius, cornerRadius, 90, 180, color)
    }

    fun drawArc(x: Int, y: Int, radius: Int, startAngle: Int, endAngle: Int, color: Color) {
        GL11.glPushMatrix()
        GL11.glEnable(3042)
        GL11.glDisable(3553)
        GL11.glBlendFunc(770, 771)
        GL11.glColor4f(
            color.getRed().toFloat() / 255,
            color.getGreen().toFloat() / 255,
            color.getBlue().toFloat() / 255,
            color.getAlpha().toFloat() / 255
        )
        val worldRenderer = Tessellator.getInstance().worldRenderer
        worldRenderer.begin(6, DefaultVertexFormats.POSITION)
        worldRenderer.pos(x.toDouble(), y.toDouble(), 0.0).endVertex()
        for (i in (startAngle / 360.0 * 100).toInt()..(endAngle / 360.0 * 100).toInt()) {
            val angle = Math.PI * 2 * i / 100 + Math.toRadians(180.0)
            worldRenderer.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0.0).endVertex()
        }
        Tessellator.getInstance().draw()
        GL11.glEnable(3553)
        GL11.glDisable(3042)
        GL11.glPopMatrix()
    }

    fun drawRoundedOutline(x: Int, y: Int, x2: Int, y2: Int, radius: Float, width: Float, color: Int) {
        val f1 = (color shr 24 and 0xFF) / 255.0f
        val f2 = (color shr 16 and 0xFF) / 255.0f
        val f3 = (color shr 8 and 0xFF) / 255.0f
        val f4 = (color and 0xFF) / 255.0f
        GL11.glColor4f(f2, f3, f4, f1)
        drawRoundedOutline(x.toFloat(), y.toFloat(), x2.toFloat(), y2.toFloat(), radius, width)
    }

    fun drawRoundedOutline(x: Float, y: Float, x2: Float, y2: Float, radius: Float, width: Float) {
        val i = 18
        val j = 90 / i
        GlStateManager.disableTexture2D()
        GlStateManager.enableBlend()
        GlStateManager.disableCull()
        GlStateManager.enableColorMaterial()
        GlStateManager.blendFunc(770, 771)
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        if (width != 1.0f) GL11.glLineWidth(width)
        GL11.glBegin(3)
        GL11.glVertex2f(x + radius, y)
        GL11.glVertex2f(x2 - radius, y)
        GL11.glEnd()
        GL11.glBegin(3)
        GL11.glVertex2f(x2, y + radius)
        GL11.glVertex2f(x2, y2 - radius)
        GL11.glEnd()
        GL11.glBegin(3)
        GL11.glVertex2f(x2 - radius, y2 - 0.1f)
        GL11.glVertex2f(x + radius, y2 - 0.1f)
        GL11.glEnd()
        GL11.glBegin(3)
        GL11.glVertex2f(x + 0.1f, y2 - radius)
        GL11.glVertex2f(x + 0.1f, y + radius)
        GL11.glEnd()
        var f1 = x2 - radius
        var f2 = y + radius
        GL11.glBegin(3)
        var k: Int
        k = 0
        while (k <= i) {
            val m = 90 - k * j
            GL11.glVertex2f(
                (f1 + radius * MathUtils.getRightAngle(m)).toFloat(),
                (f2 - radius * MathUtils.getAngle(m)).toFloat()
            )
            k++
        }
        GL11.glEnd()
        f1 = x2 - radius
        f2 = y2 - radius
        GL11.glBegin(3)
        k = 0
        while (k <= i) {
            val m = k * j + 270
            GL11.glVertex2f(
                (f1 + radius * MathUtils.getRightAngle(m)).toFloat(),
                (f2 - radius * MathUtils.getAngle(m)).toFloat()
            )
            k++
        }
        GL11.glEnd()
        GL11.glBegin(3)
        f1 = x + radius
        f2 = y2 - radius
        k = 0
        while (k <= i) {
            val m = k * j + 90
            GL11.glVertex2f(
                (f1 + radius * MathUtils.getRightAngle(m)).toFloat(),
                (f2 + radius * MathUtils.getAngle(m)).toFloat()
            )
            k++
        }
        GL11.glEnd()
        GL11.glBegin(3)
        f1 = x + radius
        f2 = y + radius
        k = 0
        while (k <= i) {
            val m = 270 - k * j
            GL11.glVertex2f(
                (f1 + radius * MathUtils.getRightAngle(m)).toFloat(),
                (f2 + radius * MathUtils.getAngle(m)).toFloat()
            )
            k++
        }
        GL11.glEnd()
        if (width != 1.0f) GL11.glLineWidth(1.0f)
        GlStateManager.enableCull()
        GlStateManager.disableBlend()
        GlStateManager.disableColorMaterial()
        GlStateManager.enableTexture2D()
    }

    fun drawLine(x: Float, x1: Float, y: Float, thickness: Float, colour: Int, smooth: Boolean) {
        drawLines(floatArrayOf(x, y, x1, y), thickness, Color(colour, true), smooth)
    }

    fun drawVerticalLine(x: Float, y: Float, y1: Float, thickness: Float, colour: Int, smooth: Boolean) {
        drawLines(floatArrayOf(x, y, x, y1), thickness, Color(colour, true), smooth)
    }

    fun drawLines(points: FloatArray, thickness: Float, colour: Color, smooth: Boolean) {
        GL11.glPushMatrix()
        GL11.glDisable(3553)
        GL11.glBlendFunc(770, 771)
        if (smooth) {
            GL11.glEnable(2848)
        } else {
            GL11.glDisable(2848)
        }
        GL11.glLineWidth(thickness)
        GL11.glColor4f(
            colour.getRed() / 255.0f,
            colour.getGreen() / 255.0f,
            colour.getBlue() / 255.0f,
            colour.getAlpha() / 255.0f
        )
        GL11.glBegin(1)
        var i = 0
        while (i < points.size) {
            GL11.glVertex2f(points[i], points[i + 1])
            i += 2
        }
        GL11.glEnd()
        GL11.glEnable(2848)
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
        GL11.glEnable(3553)
        GL11.glPopMatrix()
    }

    fun drawOutline(x: Int, y: Int, width: Int, height: Int, thickness: Float, color: Int) {
        drawLine(x.toFloat(), (x + width).toFloat(), y.toFloat(), thickness, color, false)
        drawLine(x.toFloat(), (x + width).toFloat(), (y + height).toFloat(), thickness, color, false)
        drawVerticalLine(x.toFloat(), y.toFloat(), (y + height).toFloat(), thickness, color, false)
        drawVerticalLine((x + width).toFloat(), y.toFloat(), (y + height).toFloat(), thickness, color, false)
    }

    fun drawString(text: String, x: Int, y: Int, shadow: Boolean): Int {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        return drawString(text, x, y, 16777215, shadow)
    }

    fun drawString(text: String, x: Int, y: Int, color: Int, shadow: Boolean): Int {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        val lines = text.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (lines.size > 1) {
            var j = 0
            for (i in lines.indices) j += Minecraft.getMinecraft().fontRendererObj.drawString(
                lines[i],
                x.toFloat(),
                (y + i * (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 2)).toFloat(),
                color,
                shadow
            )
            return j
        }
        return Minecraft.getMinecraft().fontRendererObj.drawString(text, x.toFloat(), y.toFloat(), color, shadow)
    }

    fun drawScaledString(text: String, x: Int, y: Int, shadow: Boolean, scale: Float): Int {
        GlStateManager.pushMatrix()
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        GlStateManager.scale(scale, scale, 1.0f)
        val i = drawString(text, (x / scale).toInt(), (y / scale).toInt(), shadow)
        GlStateManager.scale(Math.pow(scale.toDouble(), -1.0), Math.pow(scale.toDouble(), -1.0), 1.0)
        GlStateManager.popMatrix()
        return i
    }

}