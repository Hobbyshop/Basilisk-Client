package me.hobbyshop.basilisk.ui.components.buttons

import me.hobbyshop.basilisk.util.gui.CustomFontRenderer
import me.hobbyshop.basilisk.util.gui.GuiUtils
import net.minecraft.client.renderer.GlStateManager
import java.awt.Color

class UiRadioButton(text: String, x: Int, y: Int, width: Int, height: Int, onclick: () -> Unit) : UiButton(text, x, y, width, height, onclick) {

    var active = false

    override fun renderComponent(mouseX: Int, mouseY: Int, ingame: Boolean) {
        if (this.hovered(mouseX, mouseY)) {
            if (hover < 10)
                hover += 3
        } else {
            if (hover > 0)
                hover -= 3
        }

        GlStateManager.resetColor()
        var color = Color(34 - hover, 34 - hover, 40 - (hover / 1.5).toInt(), 200)
        if (!this.active)
            color = Color(34, 34, 40, hover * 4)

        GuiUtils.drawRoundedRect(x, y, width, height, 5, color)
        CustomFontRenderer.text.drawCenteredString(text.toCharArray().joinToString(" ").uppercase(), x + width / 2.0F, y + (height - 5) / 2.0F, -1)
    }

}