package me.hobbyshop.basilisk.ui

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.MathHelper
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11
import org.lwjgl.util.glu.Project

abstract class UiScreen(val parent: GuiScreen) : GuiScreen() {

    private var panoramaTimer = 0
    private val backgroundTexture: ResourceLocation = Minecraft.getMinecraft().textureManager.getDynamicTextureLocation("background", DynamicTexture(256, 256))
    private val panoramaTiles = arrayOf(
        ResourceLocation("textures/gui/title/background/panorama_0.png"),
        ResourceLocation("textures/gui/title/background/panorama_1.png"),
        ResourceLocation("textures/gui/title/background/panorama_2.png"),
        ResourceLocation("textures/gui/title/background/panorama_3.png"),
        ResourceLocation("textures/gui/title/background/panorama_4.png"),
        ResourceLocation("textures/gui/title/background/panorama_5.png")
    )

    val components: MutableList<UiComponent> = mutableListOf()

    init {
        this.mc = Minecraft.getMinecraft()
    }

    abstract fun renderScreen(mouseX: Int, mouseY: Int, ingame: Boolean)
    abstract fun initComponents()

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        val ingame = mc.theWorld != null && mc.thePlayer != null

        if (!ingame) {
            GL11.glEnable(GL11.GL_ALPHA)
            this.renderSkybox(mouseX, mouseY, partialTicks)
            GL11.glDisable(GL11.GL_ALPHA)
        }

        this.renderScreen(mouseX, mouseY, ingame)

        for (c in this.components) {
            if (c.visible)
                c.renderComponent(mouseX, mouseY, ingame)
        }
    }

    override fun initGui() {
        this.initComponents()
        super.initGui()
    }

    override fun updateScreen() {
        ++panoramaTimer
        super.updateScreen()
    }

    override fun doesGuiPauseGame() = false

    /**
     * Draws the main menu panorama
     */
    private fun drawPanorama(p_73970_1_: Int, p_73970_2_: Int, p_73970_3_: Float) {
        val tessellator = Tessellator.getInstance()
        val worldrenderer = tessellator.worldRenderer
        GlStateManager.matrixMode(5889)
        GlStateManager.pushMatrix()
        GlStateManager.loadIdentity()
        Project.gluPerspective(120.0f, 1.0f, 0.05f, 10.0f)
        GlStateManager.matrixMode(5888)
        GlStateManager.pushMatrix()
        GlStateManager.loadIdentity()
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f)
        GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f)
        GlStateManager.enableBlend()
        GlStateManager.disableAlpha()
        GlStateManager.disableCull()
        GlStateManager.depthMask(false)
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        val i = 8
        for (j in 0 until i * i) {
            GlStateManager.pushMatrix()
            val f = ((j % i).toFloat() / i.toFloat() - 0.5f) / 64.0f
            val f1 = ((j / i).toFloat() / i.toFloat() - 0.5f) / 64.0f
            val f2 = 0.0f
            GlStateManager.translate(f, f1, f2)
            GlStateManager.rotate(
                MathHelper.sin((this.panoramaTimer as Float + p_73970_3_) / 400.0f) * 25.0f + 20.0f,
                1.0f,
                0.0f,
                0.0f
            )
            GlStateManager.rotate(-(this.panoramaTimer as Float + p_73970_3_) * 0.1f, 0.0f, 1.0f, 0.0f)
            for (k in 0..5) {
                GlStateManager.pushMatrix()
                if (k == 1) {
                    GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f)
                }
                if (k == 2) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f)
                }
                if (k == 3) {
                    GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f)
                }
                if (k == 4) {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f)
                }
                if (k == 5) {
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f)
                }
                this.mc.getTextureManager().bindTexture(panoramaTiles.get(k))
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR)
                val l = 255 / (j + 1)
                val f3 = 0.0f
                worldrenderer.pos(-1.0, -1.0, 1.0).tex(0.0, 0.0).color(255, 255, 255, l).endVertex()
                worldrenderer.pos(1.0, -1.0, 1.0).tex(1.0, 0.0).color(255, 255, 255, l).endVertex()
                worldrenderer.pos(1.0, 1.0, 1.0).tex(1.0, 1.0).color(255, 255, 255, l).endVertex()
                worldrenderer.pos(-1.0, 1.0, 1.0).tex(0.0, 1.0).color(255, 255, 255, l).endVertex()
                tessellator.draw()
                GlStateManager.popMatrix()
            }
            GlStateManager.popMatrix()
            GlStateManager.colorMask(true, true, true, false)
        }
        worldrenderer.setTranslation(0.0, 0.0, 0.0)
        GlStateManager.colorMask(true, true, true, true)
        GlStateManager.matrixMode(5889)
        GlStateManager.popMatrix()
        GlStateManager.matrixMode(5888)
        GlStateManager.popMatrix()
        GlStateManager.depthMask(true)
        GlStateManager.enableCull()
        GlStateManager.enableDepth()
    }

    /**
     * Rotate and blurs the skybox view in the main menu
     */
    private fun rotateAndBlurSkybox(p_73968_1_: Float) {
        this.mc.getTextureManager().bindTexture(this.backgroundTexture)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR)
        GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256)
        GlStateManager.enableBlend()
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
        GlStateManager.colorMask(true, true, true, false)
        val tessellator = Tessellator.getInstance()
        val worldrenderer = tessellator.worldRenderer
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR)
        GlStateManager.disableAlpha()
        val i = 3
        for (j in 0 until i) {
            val f = 1.0f / (j + 1).toFloat()
            val k: Int = this.width
            val l: Int = this.height
            val f1 = (j - i / 2).toFloat() / 256.0f
            worldrenderer.pos(k.toDouble(), l.toDouble(), this.zLevel as Double).tex((0.0f + f1).toDouble(), 1.0)
                .color(1.0f, 1.0f, 1.0f, f).endVertex()
            worldrenderer.pos(k.toDouble(), 0.0, this.zLevel as Double).tex((1.0f + f1).toDouble(), 1.0)
                .color(1.0f, 1.0f, 1.0f, f).endVertex()
            worldrenderer.pos(0.0, 0.0, this.zLevel as Double).tex((1.0f + f1).toDouble(), 0.0)
                .color(1.0f, 1.0f, 1.0f, f).endVertex()
            worldrenderer.pos(0.0, l.toDouble(), this.zLevel as Double).tex((0.0f + f1).toDouble(), 0.0)
                .color(1.0f, 1.0f, 1.0f, f).endVertex()
        }
        tessellator.draw()
        GlStateManager.enableAlpha()
        GlStateManager.colorMask(true, true, true, true)
    }

    /**
     * Renders the skybox in the main menu
     */
    private fun renderSkybox(p_73971_1_: Int, p_73971_2_: Int, p_73971_3_: Float) {
        this.mc.getFramebuffer().unbindFramebuffer()
        GlStateManager.viewport(0, 0, 256, 256)
        drawPanorama(p_73971_1_, p_73971_2_, p_73971_3_)
        rotateAndBlurSkybox(p_73971_3_)
        rotateAndBlurSkybox(p_73971_3_)
        rotateAndBlurSkybox(p_73971_3_)
        rotateAndBlurSkybox(p_73971_3_)
        rotateAndBlurSkybox(p_73971_3_)
        rotateAndBlurSkybox(p_73971_3_)
        rotateAndBlurSkybox(p_73971_3_)
        this.mc.getFramebuffer().bindFramebuffer(true)
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight)
        val f = if (this.width > this.height) 120.0f / this.width as Float else 120.0f / this.height as Float
        val f1 = this.height as Float * f / 256.0f
        val f2 = this.width as Float * f / 256.0f
        val i: Int = this.width
        val j: Int = this.height
        val tessellator = Tessellator.getInstance()
        val worldrenderer = tessellator.worldRenderer
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR)
        worldrenderer.pos(0.0, j.toDouble(), this.zLevel as Double).tex((0.5f - f1).toDouble(), (0.5f + f2).toDouble())
            .color(1.0f, 1.0f, 1.0f, 1.0f).endVertex()
        worldrenderer.pos(i.toDouble(), j.toDouble(), this.zLevel as Double)
            .tex((0.5f - f1).toDouble(), (0.5f - f2).toDouble()).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex()
        worldrenderer.pos(i.toDouble(), 0.0, this.zLevel as Double).tex((0.5f + f1).toDouble(), (0.5f - f2).toDouble())
            .color(1.0f, 1.0f, 1.0f, 1.0f).endVertex()
        worldrenderer.pos(0.0, 0.0, this.zLevel as Double).tex((0.5f + f1).toDouble(), (0.5f + f2).toDouble())
            .color(1.0f, 1.0f, 1.0f, 1.0f).endVertex()
        tessellator.draw()
    }

}