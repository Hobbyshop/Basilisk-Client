package me.hobbyshop.basilisk.ui

import me.hobbyshop.basilisk.Basilisk
import me.hobbyshop.basilisk.ui.components.buttons.UiButton
import me.hobbyshop.basilisk.util.CustomFontRenderer
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.PositionedSoundRecord
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.MathHelper
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11
import org.lwjgl.util.glu.Project
import org.newdawn.slick.particles.ConfigurableEmitter.ColorRecord
import java.awt.Color

abstract class UiScreen(val parent: GuiScreen?) : GuiScreen() {

    private var panoramaTimer = 0
    private val backgroundTexture: ResourceLocation = Minecraft.getMinecraft().textureManager.getDynamicTextureLocation("background", DynamicTexture(256, 256))

    val components: MutableList<UiComponent> = mutableListOf()

    init {
        this.mc = Minecraft.getMinecraft()
    }

    abstract fun renderScreen(mouseX: Int, mouseY: Int, ingame: Boolean)
    abstract fun initComponents()

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        val ingame = mc.theWorld != null && mc.thePlayer != null

        if (!ingame) {
            Gui.drawRect(0, 0, width, height, Color(41, 41, 48).rgb)

            val s = "Copyright Monjang Studios. Do not distribute!"
            CustomFontRenderer.text.drawString(Basilisk.NAMEVER, 5, this.height - 10, Color(255, 255, 255, 80).rgb)
            CustomFontRenderer.text.drawString(s, this.width - CustomFontRenderer.text.getWidth(s).toInt() - 5, this.height - 10, Color(255, 255, 255, 80).rgb)
        }

        this.renderScreen(mouseX, mouseY, ingame)

        for (c in this.components) {
            if (c.visible)
                c.renderComponent(mouseX, mouseY, ingame)
        }
    }

    override fun initGui() {
        this.components.clear()
        this.initComponents()
        super.initGui()
    }

    override fun updateScreen() {
        ++panoramaTimer
        super.updateScreen()
    }

    override fun doesGuiPauseGame() = false

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        if (mouseButton != 0) return

        for (component in this.components) {
            if (component !is UiButton) continue
            if (component.hovered(mouseX, mouseY)) {
                this.mc.soundHandler.playSound(PositionedSoundRecord.create(ResourceLocation("gui.button.press"), 1.0F))
                component.onclick()
            }
        }
    }

}