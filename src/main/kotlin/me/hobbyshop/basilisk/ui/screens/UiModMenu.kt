package me.hobbyshop.basilisk.ui.screens

import me.hobbyshop.basilisk.Basilisk
import me.hobbyshop.basilisk.mod.ModCategory
import me.hobbyshop.basilisk.mod.Module
import me.hobbyshop.basilisk.ui.UiScreen
import me.hobbyshop.basilisk.ui.components.buttons.UiImageButton
import me.hobbyshop.basilisk.ui.components.buttons.UiRadioButton
import me.hobbyshop.basilisk.ui.components.list.UiListClear
import me.hobbyshop.basilisk.ui.components.list.UiListEntry
import me.hobbyshop.basilisk.util.gui.CustomFontRenderer
import me.hobbyshop.basilisk.util.gui.GuiUtils
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiScreen
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11
import java.awt.Color

class UiModMenu(parent: GuiScreen?) : UiScreen(parent) {

    companion object {
        private lateinit var filterButtons: Array<UiRadioButton>
    }

    private var filter: ModCategory? = null

    private lateinit var modList: UiListClear

    override fun renderScreen(mouseX: Int, mouseY: Int, ingame: Boolean) {
        GuiUtils.drawRoundedRect(this.width / 2 - 120, this.height / 2 - 80, 240, 160, 5, Color(46, 46, 53))
        GuiUtils.drawRoundedRect(this.width / 2 - 120, this.height / 2 - 80, 80, 160, 5, Color(51, 51, 58))

        GuiUtils.drawRoundedRect(this.width / 2 - 115, this.height / 2 + 56, 70, 18, 5, Color(34, 34, 40, 200))

        mc.textureManager.bindTexture(ResourceLocation("basilisk/logo.png"))
        GL11.glPushMatrix()
        GL11.glEnable(GL11.GL_BLEND)
        GL11.glEnable(GL11.GL_ALPHA_TEST)
        GL11.glColor3f(1.0F, 1.0F, 1.0F)
        Gui.drawModalRectWithCustomSizedTexture(this.width / 2 - 110, this.height / 2 - 70, 0F, 0F, 10, 10, 10F, 10F)
        CustomFontRenderer.title.drawStringScaled(Basilisk.NAME, this.width / 2 - 95, this.height / 2 - 68, -1, 0.65)
        GL11.glPopMatrix()

        GL11.glColor3f(1.0F, 1.0F, 1.0F)
    }

    override fun initComponents() {
        filterButtons = arrayOf(
            UiRadioButton("ALL", this.width / 2 - 115, this.height / 2 - 48, 70, 14) {
                if (filter != null)
                    filter = null

                this.setModListEntries()
            },
            UiRadioButton("HUD", this.width / 2 - 115, this.height / 2 - 30, 70, 14) {
                if (filter != ModCategory.HUD)
                    filter = ModCategory.HUD

                this.setModListEntries()
            },
            UiRadioButton("PVP", this.width / 2 - 115, this.height / 2 - 12, 70, 14) {
                if (filter != ModCategory.PVP)
                    filter = ModCategory.PVP

                this.setModListEntries()
            },
            UiRadioButton("MISC", this.width / 2 - 115, this.height / 2 + 6, 70, 14) {
                if (filter != ModCategory.MISC)
                    filter = ModCategory.MISC

                this.setModListEntries()
            }
        )
        this.components.addAll(filterButtons)

        filter = null
        this.setRadioButton(0)

        modList = UiListClear(this.width / 2 - 30, this.height / 2 - 70, 140, 140, 25, emptyList())
        this.setModListEntries()
        this.components.add(modList)

        this.components.add(UiImageButton(ResourceLocation("basilisk/icons/hud.png"), "Edit HUD", this.width / 2 - 80 - 24, this.height / 2 + 61, 8, this.height / 2 + 49) {
            mc.displayGuiScreen(UiHudScreen(this))
        })
        this.components.add(UiImageButton(ResourceLocation("basilisk/icons/settings.png"), "Client Settings", this.width / 2 - 80 - 4, this.height / 2 + 61, 8, this.height / 2 + 49) {

        })
        this.components.add(UiImageButton(ResourceLocation("basilisk/icons/console.png"), "Console", this.width / 2 - 80 - 4 + 20, this.height / 2 + 61, 8, this.height / 2 + 49) {
            mc.displayGuiScreen(UiConsole(this))
        })
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        for (i in filterButtons.indices) {
            if (filterButtons[i].hovered(mouseX, mouseY))
                this.setRadioButton(i)
        }
        super.mouseClicked(mouseX, mouseY, mouseButton)
    }

    private fun setRadioButton(index: Int) {
        for (b in filterButtons)
            b.active = false

        filterButtons[index].active = true
    }

    private fun setModListEntries() {
        val listEntries = mutableListOf<UiModEntry>()
        for (mod in Basilisk.instance.modManager.modules) {
            when (filter) {
                ModCategory.HUD -> {
                    if (mod.getData().category == ModCategory.HUD)
                        listEntries.add(UiModEntry(mod))
                }

                ModCategory.PVP -> {
                    if (mod.getData().category == ModCategory.PVP)
                        listEntries.add(UiModEntry(mod))
                }

                ModCategory.MISC -> {
                    if (mod.getData().category == ModCategory.MISC)
                        listEntries.add(UiModEntry(mod))
                }

                else -> {
                    listEntries.add(UiModEntry(mod))
                }
            }
        }

        this.modList.entries = listEntries
    }

    class UiModEntry(private val mod: Module) : UiListEntry() {

        private var hover = 0

        override fun renderEntry(x: Int, y: Int, width: Int, height: Int, index: Int) {
            val mouseX = Minecraft.getMinecraft().mouseHelper.deltaX
            val mouseY = Minecraft.getMinecraft().mouseHelper.deltaY

            if (mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height) {
                if (hover < 10)
                    hover += 3
            } else {
                if (hover > 0)
                    hover -= 3
            }

            GuiUtils.drawRoundedRect(x, y + 5, width, height - 5, 3, Color(34 - hover, 34 - hover, 40 - (hover / 1.5).toInt(), 200))

            GL11.glPushMatrix()
            GL11.glEnable(GL11.GL_BLEND)
            GL11.glEnable(GL11.GL_ALPHA_TEST)
            GL11.glColor3f(1.0F, 1.0F, 1.0F)

            CustomFontRenderer.text.drawString(mod.getData().name, 5, height / 2 - CustomFontRenderer.FONT_HEIGHT / 2, -1)
            GL11.glPopMatrix()
        }

    }

}