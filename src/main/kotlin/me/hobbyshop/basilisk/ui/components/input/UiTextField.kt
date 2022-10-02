package me.hobbyshop.basilisk.ui.components.input

import me.hobbyshop.basilisk.ui.UiComponent
import me.hobbyshop.basilisk.util.gui.CustomFontRenderer
import me.hobbyshop.basilisk.util.gui.GuiUtils
import org.lwjgl.input.Keyboard
import org.lwjgl.opengl.GL11
import java.awt.Color

class UiTextField(val fontRenderer: CustomFontRenderer, x: Int, y: Int, width: Int, height: Int) : UiComponent(x, y, width, height) {

    companion object {
        const val validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!\"\\~$%&/(){}[]=?@+'#-_:.;,<>| "
    }

    var cursorChar: Char = '|'

    var focused: Boolean = false
    var content: String = ""

    override fun renderComponent(mouseX: Int, mouseY: Int, ingame: Boolean) {
        val color = if (focused)
                        Color(24, 24, 33, 200)
                    else
                        Color(34, 34, 40, 200)

        GL11.glPushMatrix()
        GuiUtils.drawRoundedRect(x, y, width, height, 5, color)
        fontRenderer.drawString(this.content, x + height / 2.0F, y + (height - 5) / 2.0F, -1)
        GL11.glDisable(GL11.GL_BLEND)
        GL11.glPopMatrix()
    }

    fun mouseClick(mouseButton: Int, mouseX: Int, mouseY: Int) {
        if (mouseButton != 0) return
        this.focused = hovered(mouseX, mouseY)
    }

    fun keyTyped(keyCode: Int, char: Char) {
        if (!this.focused) return

        if (keyCode == Keyboard.KEY_BACK) {
            if (this.content.isNotEmpty())
                this.content = content.substring(0, content.length - 1)

            return
        }

        if (validChars.contains(char) && !(content.isEmpty() && char == ' '))
            this.content += char
    }

}