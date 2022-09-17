package me.hobbyshop.basilisk.ui.components.buttons

import me.hobbyshop.basilisk.util.gui.CustomFontRenderer
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

class UiImageButton(private val image: ResourceLocation, text: String, x: Int, y: Int, size: Int, private val textY: Int, onclick: () -> Unit) : UiButton(
    text,
    x,
    y,
    size,
    size,
    onclick
) {

    override fun renderComponent(mouseX: Int, mouseY: Int, ingame: Boolean) {
        if (this.hovered(mouseX, mouseY)) {
            if (hover < 10)
                hover += 3

            this.renderText()
        } else {
            if (hover > 0)
                hover -= 3
        }
        Minecraft.getMinecraft().textureManager.bindTexture(image)

        GL11.glPushMatrix()
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glEnable(GL11.GL_ALPHA_TEST)
        GL11.glColor3f(1.0F, 1.0F, 1.0F)
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0F, 0F, width, height, width.toFloat(), height.toFloat())
        GL11.glPopMatrix()
    }

    private fun renderText() =
        CustomFontRenderer.text.drawCenteredString(text.uppercase(), x + width / 2.0F, textY.toFloat(), -1)

}