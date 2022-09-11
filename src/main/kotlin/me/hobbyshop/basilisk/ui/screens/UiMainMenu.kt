package me.hobbyshop.basilisk.ui.screens

import me.hobbyshop.basilisk.Basilisk
import me.hobbyshop.basilisk.ui.UiScreen
import me.hobbyshop.basilisk.ui.components.buttons.UiButton
import me.hobbyshop.basilisk.util.CustomFontRenderer
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiMultiplayer
import net.minecraft.client.gui.GuiSelectWorld
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

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
    }

    override fun initComponents() {
        this.components.add(UiButton("Singleplayer", this.width / 2 - 60, this.height / 2 + 7, 120, 18) {
            mc.displayGuiScreen(GuiSelectWorld(this))
        })
        this.components.add(UiButton("Multiplayer", this.width / 2 - 60, this.height / 2 + 30, 120, 18) {
            mc.displayGuiScreen(GuiMultiplayer(this))
        })
    }

}