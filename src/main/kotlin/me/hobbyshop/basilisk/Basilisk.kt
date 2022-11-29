package me.hobbyshop.basilisk

import me.hobbyshop.basilisk.events.KeyPressedEvent
import me.hobbyshop.basilisk.mod.ModuleManager
import me.hobbyshop.basilisk.script.ClientConsole
import me.hobbyshop.basilisk.script.ScriptHandler
import me.hobbyshop.basilisk.settings.SettingsManager
import me.hobbyshop.basilisk.ui.screens.UiModMenu
import me.hobbyshop.basilisk.util.KeyBindings
import me.hobbyshop.basilisk.util.Logger
import me.hobbyshop.basilisk.util.event.EventManager
import me.hobbyshop.basilisk.util.event.EventTarget
import net.minecraft.client.Minecraft
import java.io.File

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
        modManager = ModuleManager()

        SettingsManager.loadSettings()
        KeyBindings.registerBindings()

        if (!File("basilisk/scripts").exists())
            ScriptHandler.createDefaultScripts()

        ClientConsole.instance.loadCommands()

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