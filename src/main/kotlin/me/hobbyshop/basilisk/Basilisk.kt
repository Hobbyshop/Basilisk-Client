package me.hobbyshop.basilisk

import me.hobbyshop.basilisk.events.KeyPressedEvent
import me.hobbyshop.basilisk.mod.ModuleManager
import me.hobbyshop.basilisk.mod.impl.TestMod
import me.hobbyshop.basilisk.script.ClientConsole
import me.hobbyshop.basilisk.settings.SettingsManager
import me.hobbyshop.basilisk.util.Logger
import me.hobbyshop.basilisk.util.event.EventManager
import me.hobbyshop.basilisk.util.event.EventTarget
import java.io.File

class Basilisk {

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
        logger = Logger()
        ClientConsole.instance.loadCommands()
        modManager = ModuleManager()

        SettingsManager.loadSettings()
        logger.info("Started client")
    }

    fun shutdown() {
        SettingsManager.saveSettings()
        logger.info("Shut down client")
    }

}