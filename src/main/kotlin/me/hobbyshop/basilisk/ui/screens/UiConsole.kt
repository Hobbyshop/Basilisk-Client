package me.hobbyshop.basilisk.ui.screens

import me.hobbyshop.basilisk.Basilisk
import me.hobbyshop.basilisk.script.ClientConsole
import me.hobbyshop.basilisk.ui.UiScreen
import me.hobbyshop.basilisk.ui.components.buttons.UiImageButton
import me.hobbyshop.basilisk.ui.components.input.UiTextField
import me.hobbyshop.basilisk.util.gui.CustomFontRenderer
import me.hobbyshop.basilisk.util.gui.GuiUtils
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiScreen
import net.minecraft.util.ResourceLocation
import org.lwjgl.input.Keyboard
import org.lwjgl.opengl.GL11
import java.awt.Color

class UiConsole(parent: GuiScreen?) : UiScreen(parent) {

    private lateinit var inputBox: UiTextField

    override fun renderScreen(mouseX: Int, mouseY: Int, ingame: Boolean) {
        GuiUtils.drawRoundedRect(this.width / 2 - 120, this.height / 2 - 80, 240, 160, 5, Color(46, 46, 53))
        GuiUtils.drawRoundedRect(this.width / 2 - 120, this.height / 2 - 80, 30, 160, 5, Color(51, 51, 58))

        mc.textureManager.bindTexture(ResourceLocation("basilisk/logo.png"))
        GL11.glPushMatrix()
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glEnable(GL11.GL_ALPHA_TEST)
        GL11.glColor3f(1.0F, 1.0F, 1.0F)
        Gui.drawModalRectWithCustomSizedTexture(this.width / 2 - 110, this.height / 2 - 70, 0F, 0F, 10, 10, 10F, 10F)
        GL11.glPopMatrix()

        this.renderConsole(mouseX, mouseY)
    }

    override fun initComponents() {
        this.components.add(UiImageButton(ResourceLocation("basilisk/icons/back.png"), "Back", this.width / 2 - 110, this.height / 2 + 60, 10, this.height / 2 + 50) {
            mc.displayGuiScreen(parent)
        })

        this.inputBox = UiTextField(CustomFontRenderer.monospace, this.width / 2 - 75, this.height / 2 + 45, 180, 20)
        this.inputBox.focused = true
        this.inputBox.cursorChar = '_'
        this.components.add(inputBox)
    }

    private fun renderConsole(mouseX: Int, mouseY: Int) {
        GuiUtils.drawRoundedRect(this.width / 2 - 75, this.height / 2 - 65, 180, 130, 5, Color(37, 37, 45))

        var lineOffset = 0
        for (line in ClientConsole.instance.history) {
            CustomFontRenderer.monospace.drawString(line, this.width / 2 - 70F, this.height / 2 - 60F + lineOffset, Color(134, 153, 169).rgb)
            lineOffset += CustomFontRenderer.FONT_HEIGHT
        }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        this.inputBox.mouseClick(mouseButton, mouseX, mouseY)
        super.mouseClicked(mouseX, mouseY, mouseButton)
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (keyCode == Keyboard.KEY_RETURN) {
            if (inputBox.content.isNotEmpty())
                ClientConsole.instance.performCommand(inputBox.content)

            this.inputBox.content = ""
            return
        }
        this.inputBox.keyTyped(keyCode, typedChar)
        super.keyTyped(typedChar, keyCode)
    }

}