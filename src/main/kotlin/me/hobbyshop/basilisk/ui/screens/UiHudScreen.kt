package me.hobbyshop.basilisk.ui.screens

import me.hobbyshop.basilisk.ui.UiScreen
import me.hobbyshop.basilisk.ui.components.buttons.UiButton
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation

class UiHudScreen(parent: GuiScreen?) : UiScreen(parent) {

    private var mouseClicked: Boolean = false
    private var logoOpacity: Float = 150.0F

    private lateinit var backButton: UiButton

    override fun renderScreen(mouseX: Int, mouseY: Int, ingame: Boolean) {
        if (mouseClicked) {
            if (logoOpacity > 0) logoOpacity -= 15.0F
        } else {
            if (logoOpacity < 150.0F) logoOpacity += 15.0F
        }

        GlStateManager.pushMatrix()
        GlStateManager.enableBlend()
        GlStateManager.enableAlpha()
        GlStateManager.color(1.0F, 1.0F, 1.0F, logoOpacity)
        mc.textureManager.bindTexture(ResourceLocation("basilisk/logo.png"))
        Gui.drawModalRectWithCustomSizedTexture(this.width / 2 - 20, this.height / 2 - 20, 0F, 0F, 40, 40, 40F, 40F)

        this.backButton.renderComponent(mouseX, mouseY, ingame)
        GlStateManager.popMatrix()
    }

    override fun initComponents() {
        this.backButton = UiButton("BACK", this.width / 2 - 40, this.height / 2 + 25, 80, 18) {
            mc.displayGuiScreen(parent)
        }
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        this.mouseClicked = true
        super.mouseClicked(mouseX, mouseY, mouseButton)
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, state: Int) {
        this.mouseClicked = false
        super.mouseReleased(mouseX, mouseY, state)
    }

}