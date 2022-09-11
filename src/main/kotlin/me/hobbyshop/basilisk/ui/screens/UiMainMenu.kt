package me.hobbyshop.basilisk.ui.screens

import me.hobbyshop.basilisk.Basilisk
import me.hobbyshop.basilisk.ui.UiScreen
import me.hobbyshop.basilisk.ui.components.buttons.UiButton
import me.hobbyshop.basilisk.ui.components.buttons.UiImageButton
import me.hobbyshop.basilisk.util.gui.CustomFontRenderer
import me.hobbyshop.basilisk.util.gui.GuiUtils
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiMultiplayer
import net.minecraft.client.gui.GuiOptions
import net.minecraft.client.gui.GuiSelectWorld
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11
import java.awt.Color

class UiMainMenu : UiScreen(null) {

    private val logo = ResourceLocation("basilisk/logo.png")

    override fun renderScreen(mouseX: Int, mouseY: Int, ingame: Boolean) {
        mc.textureManager.bindTexture(logo)
        GL11.glPushMatrix()
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glEnable(GL11.GL_ALPHA_TEST)
        GL11.glColor3f(1.0F, 1.0F, 1.0F)
        Gui.drawModalRectWithCustomSizedTexture(this.width / 2 - 20, this.height / 2 - 60, 0F, 0F, 40, 40, 40F, 40F)
        CustomFontRenderer.title.drawCenteredString(Basilisk.NAME, this.width.toFloat() / 2, this.height.toFloat() / 2 - 15, -1)
        GL11.glPopMatrix()

        GuiUtils.drawRoundedRect(this.width / 2 - 40, this.height - 23, 80, 18, 5, Color(34, 34, 40, 200))
    }

    override fun initComponents() {
        this.components.add(UiButton("Singleplayer", this.width / 2 - 70, this.height / 2 + 7, 140, 18) {
            mc.displayGuiScreen(GuiSelectWorld(this))
        })
        this.components.add(UiButton("Multiplayer", this.width / 2 - 70, this.height / 2 + 30, 140, 18) {
            mc.displayGuiScreen(GuiMultiplayer(this))
        })

        this.components.add(UiImageButton(ResourceLocation("basilisk/icons/settings.png"), "Game Settings", this.width / 2 - 26, this.height - 18, 8, this.height - 30) {
            mc.displayGuiScreen(GuiOptions(this, mc.gameSettings))
        })
        this.components.add(UiImageButton(ResourceLocation("basilisk/icons/logo.png"), "Client Settings", this.width / 2 - 4, this.height - 18, 8, this.height - 30) {

        })
        this.components.add(UiImageButton(ResourceLocation("basilisk/icons/close.png"), "Quit", this.width / 2 + 17, this.height - 18, 8, this.height - 30) {
            mc.shutdown()
        })
    }

}