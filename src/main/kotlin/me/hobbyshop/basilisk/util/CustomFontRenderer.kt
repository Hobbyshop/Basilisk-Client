package me.hobbyshop.basilisk.util

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.StringUtils
import org.lwjgl.opengl.GL11
import org.newdawn.slick.UnicodeFont
import org.newdawn.slick.font.effects.ColorEffect
import java.awt.Color
import java.awt.Font
import java.util.regex.Pattern

class CustomFontRenderer(val filePath: String, val size: Int) {

    companion object {
        private val COLOR_CODE_PATTERN = Pattern.compile("§[0123456789abcdefklmnor]")

        val FONT_HEIGHT = 9

        val title = CustomFontRenderer("/assets/minecraft/client/fonts/Comic-Regular.ttf", 22)
        var text = CustomFontRenderer("/assets/minecraft/client/fonts/Comic-Bold.ttf", 12)
    }

    private val colorCodes: Array<Int> = arrayOf(
        0,
        170,
        43520,
        43690,
        11141120,
        11141290,
        16755200,
        11184810,
        5592405,
        5592575,
        5635925,
        5636095,
        16733525,
        16733695,
        16777045,
        16777215
    )
    private val cachedStringWidth: MutableMap<String, Float> = mutableMapOf()
    private var prevScaleFactor: Int = ScaledResolution(Minecraft.getMinecraft()).scaleFactor
    private var antialiasingFactor: Float

    private var font: UnicodeFont

    init {
        val res = ScaledResolution(Minecraft.getMinecraft())
        prevScaleFactor = res.scaleFactor

        font = UnicodeFont(this.getFont(filePath).deriveFont(size * prevScaleFactor / 2.0F))
        font.addAsciiGlyphs()
        font.effects.add(ColorEffect(Color.WHITE))
        font.loadGlyphs()

        antialiasingFactor = res.scaleFactor.toFloat()
    }

    private fun getFont(path: String): Font {
        val stream = CustomFontRenderer::class.java.getResourceAsStream(path)
        return Font.createFont(0, stream)
    }


    fun drawString(text: String, x: Int, y: Int, color: Int) {
        this.drawString(text, x.toFloat(), y.toFloat(), color)
    }

    fun drawString(text: String, x: Float, y: Float, color: Int) {
        if (text == "") return

        val res = ScaledResolution(Minecraft.getMinecraft())

        if (res.scaleFactor != prevScaleFactor) {
            this.prevScaleFactor = res.scaleFactor
            font = UnicodeFont(this.getFont(filePath).deriveFont(size * prevScaleFactor / 2.0F))
            font.addAsciiGlyphs()
            font.effects.add(ColorEffect(Color.WHITE))
            font.loadGlyphs()

            this.antialiasingFactor = res.scaleFactor.toFloat()
        }

        GL11.glPushMatrix()
        GL11.glScaled(1.0 / antialiasingFactor, 1.0 / antialiasingFactor, 1.0 / antialiasingFactor)

        var x = x * antialiasingFactor
        var y = y * antialiasingFactor
        val prevX = x

        val chars = text.toCharArray()
        val parts = COLOR_CODE_PATTERN.split(text)
        var currentColor = color

        val r = (color shr 16 and 0xFF) / 255.0F
        val g = (color shr 8 and 0xFF) / 255.0F
        val b = (color and 0xFF) / 255.0F
        val a = (color shr 24 and 0xFF) / 255.0F
        GL11.glColor4f(r, g, b, a)
        GL11.glDisable(GL11.GL_LIGHTING)
        GL11.glEnable(GL11.GL_BLEND)
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        GL11.glBlendFunc(770, 771)

        var index = 0
        for (s in parts) {
            for (s2 in s.split('\n')) {
                for (s3 in s2.split('\r')) {

                    this.font.drawString(x, y, s3, org.newdawn.slick.Color(currentColor))
                    x += this.font.getWidth(s3)
                    index += s3.length
                    if (index < chars.size && chars[index] == '\r') {
                        x = prevX
                        ++index
                    }
                }

                if (index < chars.size && chars[index] == '\n') {
                    x = prevX
                    y += this.getHeight(s2) * 2.0F
                    ++index
                }
            }

            if (index < chars.size) {
                val colorCode = chars[index]

                if (colorCode == '§') {
                    val colorChar = chars[index + 1]
                    val colorIndex = "123456789abcdef".indexOf(colorChar)

                    if (colorIndex < 0) {
                        if (colorChar == 'r') currentColor = color
                    } else {
                        currentColor = colorCodes[colorIndex]
                    }
                    index += 2
                }
            }
        }

        GL11.glColor3f(1.0F, 1.0F, 1.0F)
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)
        GL11.glPopMatrix()
    }

    fun drawStringScaled(text: String, x: Int, y: Int, color: Int, scale: Double) {
        GL11.glPushMatrix()
        GL11.glTranslated(x.toDouble(), y.toDouble(), 0.0)
        GL11.glScaled(scale, scale, scale)
        this.drawString(text, 0, 0, color)
        GL11.glPopMatrix()
    }

    fun drawStringScaled(text: String, x: Float, y: Float, color: Int, scale: Double) {
        GL11.glPushMatrix()
        GL11.glTranslated(x.toDouble(), y.toDouble(), 0.0)
        GL11.glScaled(scale, scale, scale)
        this.drawString(text, 0, 0, color)
        GL11.glPopMatrix()
    }

    fun drawStringWithShadow(text: String, x: Float, y: Float, color: Int) {
        this.drawString(StringUtils.stripControlCodes(text), x - 0.8F, y + 0.8F, 0x00000000)
        this.drawString(text, x, y, color)
    }

    fun drawCenteredString(text: String, x: Float, y: Float, color: Int) {
        this.drawString(text, x - (getWidth(text).toInt() shr 1), y, color)
    }

    fun drawCenteredStringScaled(text: String, x: Float, y: Float, color: Int, scale: Double) {
        GL11.glPushMatrix()
        GL11.glTranslated(x.toDouble(), y.toDouble(), 0.0)
        GL11.glScaled(scale, scale, scale)
        this.drawCenteredString(text, 0.0F, 0.0F, color)
        GL11.glPopMatrix()
    }

    fun drawCenteredStringWithShadow(text: String, x: Float, y: Float, color: Int) {
        this.drawCenteredString(StringUtils.stripControlCodes(text), x - 0.5F, y + 0.5F, 0)
        this.drawCenteredString(text, x, y, color)
    }

    fun drawRightFormattedString(text: String, x: Float, y: Float, color: Int) {
        this.drawString(text, x - getWidth(text).toInt(), y, color)
    }

    fun drawRightFormattedStringWithShadow(text: String, x: Float, y: Float, color: Int) {
        this.drawStringWithShadow(text, x - getWidth(text).toInt(), y, color)
    }

    fun drawSplitString(lines: Array<String>, x: Float, y: Float, color: Int) {
        this.drawString(lines.joinToString("\n\r"), x, y, color)
    }

    fun getHeight(text: String) = this.font.getHeight(text) / 2.0F

    fun getCharWidth(char: Char) = this.font.getWidth(char.toString())

    fun getWidth(text: String): Float {
        if (cachedStringWidth.size > 1000) cachedStringWidth.clear()

        return cachedStringWidth.computeIfAbsent(text) { this.font.getWidth(text) / antialiasingFactor }
    }

}