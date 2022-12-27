package me.hobbyshop.basilisk.ui.components.list

import me.hobbyshop.basilisk.ui.UiComponent
import me.hobbyshop.basilisk.util.gui.ScissorsUtil
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11


open class UiListClear(x: Int, y: Int, width: Int, height: Int, protected val entryHeight: Int, var entries: List<UiListEntry>) : UiComponent(x, y, width, height) {

    companion object {
        const val SCROLL_INTENSITY = 12.0F
    }

    protected val mc = Minecraft.getMinecraft()

    protected var mouseX = 0
    protected var mouseY = 0

    protected var scrollingHeight = 0F

    override fun renderComponent(mouseX: Int, mouseY: Int, ingame: Boolean) {
        this.mouseX = mouseX
        this.mouseY = mouseY

        val res = ScaledResolution(mc)

        val bottom = -(entryHeight * entries.size - height)
        if (scrollingHeight < bottom) scrollingHeight = bottom.toFloat()
        if (scrollingHeight > 0) scrollingHeight = 0F

        GL11.glPushMatrix()
        ScissorsUtil.enable()
        GL11.glTranslatef(0F, scrollingHeight, 0F)

        var slotHeight = 0
        for (e in entries) {
            if (e.selected) {
            }

            e.renderEntry(x, y + slotHeight, width, entryHeight, entries.indexOf(e))
            slotHeight += entryHeight
        }

        ScissorsUtil.select(x, y, width, height)
        ScissorsUtil.disable()
        GL11.glPopMatrix()
    }

    fun keyTyped(keyCode: Int) {
        if (keyCode == Keyboard.KEY_UP) scrollingHeight += SCROLL_INTENSITY
        if (keyCode == Keyboard.KEY_DOWN) scrollingHeight -= SCROLL_INTENSITY
    }

    fun handleMouseInput() {
        if (this.hovered(mouseX, mouseY))
            scrollingHeight += Integer.compare(Mouse.getEventDWheel(), 0) * SCROLL_INTENSITY
    }


}