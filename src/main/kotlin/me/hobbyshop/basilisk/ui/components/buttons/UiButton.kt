package me.hobbyshop.basilisk.ui.components.buttons

import me.hobbyshop.basilisk.ui.UiComponent
import me.hobbyshop.basilisk.util.CustomFontRenderer
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import java.awt.Color

class UiButton(private val text: String, x: Int, y: Int, width: Int, height: Int, val onclick: () -> Unit) : UiComponent(x, y, width, height) {

    private var hover = 0

    override fun renderComponent(mouseX: Int, mouseY: Int, ingame: Boolean) {
        if (this.hovered(mouseX, mouseY)) {
            if (hover < 12)
                hover += 3
        } else {
            if (hover > 0)
                hover -= 3
        }
        GlStateManager.resetColor()
        Gui.drawRect(x, y, x + width, y + height, Color(34 - hover, 34 - hover, 40 - hover, 200).rgb)
        CustomFontRenderer.text.drawCenteredString(text.toCharArray().joinToString(" ").uppercase(), x + width / 2.0F, y + (height - 5) / 2.0F, -1)
    }

}