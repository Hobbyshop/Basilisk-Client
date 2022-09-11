package me.hobbyshop.basilisk.ui.components.buttons

import me.hobbyshop.basilisk.ui.UiComponent
import me.hobbyshop.basilisk.util.gui.CustomFontRenderer
import me.hobbyshop.basilisk.util.gui.GuiUtils
import net.minecraft.client.renderer.GlStateManager
import java.awt.Color

open class UiButton(protected val text: String, x: Int, y: Int, width: Int, height: Int, val onclick: () -> Unit) : UiComponent(x, y, width, height) {

    private var hover = 0

    override fun renderComponent(mouseX: Int, mouseY: Int, ingame: Boolean) {
        if (this.hovered(mouseX, mouseY)) {
            if (hover < 10)
                hover += 3
        } else {
            if (hover > 0)
                hover -= 3
        }
        GlStateManager.resetColor()
        GuiUtils.drawRoundedRect(x, y, width, height, 5, Color(34 - hover, 34 - hover, 40 - (hover / 1.5).toInt(), 200))
        CustomFontRenderer.text.drawCenteredString(text.toCharArray().joinToString(" ").uppercase(), x + width / 2.0F, y + (height - 5) / 2.0F, -1)
    }

}