package me.hobbyshop.basilisk

import me.hobbyshop.basilisk.events.KeyPressedEvent
import me.hobbyshop.basilisk.mod.ModuleManager
import me.hobbyshop.basilisk.script.ClientConsole
import me.hobbyshop.basilisk.settings.SettingsManager
import me.hobbyshop.basilisk.ui.screens.UiModMenu
import me.hobbyshop.basilisk.util.KeyBindings
import me.hobbyshop.basilisk.util.Logger
import me.hobbyshop.basilisk.util.event.EventManager
import me.hobbyshop.basilisk.util.event.EventTarget
import net.minecraft.client.Minecraft

class Basilisk {

    private val mc = Minecraft.getMinecraft()

    lateinit var logger: Logger
    lateinit var modManager: ModuleManager

    companion object Instances {
        @JvmStatic
        val instance = Basilisk()

        const val NAME = "Basilisk Client"
        const val VERSION = "1.0"
        const val NAMEVER = "$NAME v$VERSION"
    }

    fun startup() {
        EventManager.register(this)

        logger = Logger()
        ClientConsole.instance.loadCommands()
        KeyBindings.registerBindings()
        modManager = ModuleManager()
        SettingsManager.loadSettings()

        logger.info("Started client")
    }

    fun shutdown() {
        SettingsManager.saveSettings()

        EventManager.unregister(this)
        logger.info("Shut down client")
    }

    @EventTarget
    fun onKeyPressed(event: KeyPressedEvent) {
        if (event.keyCode == KeyBindings.MOD_SCREEN.keyCode && mc.theWorld != null)
            mc.displayGuiScreen(UiModMenu(null))
    }

}