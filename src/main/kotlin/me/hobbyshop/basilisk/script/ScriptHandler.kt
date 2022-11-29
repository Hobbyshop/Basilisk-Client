package me.hobbyshop.basilisk.script

import me.hobbyshop.basilisk.settings.SettingsManager
import org.apache.commons.io.FileUtils
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.jse.JsePlatform
import java.io.File

class ScriptHandler private constructor(val scriptPath: String) {

    companion object {
        fun createDefaultScripts() {
            val defaultsDir = File(Companion::class.java.getResource("/scripts/")!!.path)

            val configDir = File("basilisk/scripts")
            if (!configDir.exists())
                configDir.mkdirs()

            FileUtils.copyDirectory(defaultsDir, configDir)
        }

        fun getScriptByName(name: String): ScriptHandler {
            val file = File("basilisk/scripts/$name")
            return ScriptHandler(file.absolutePath)
        }
    }

    private val chunk: LuaValue

    init {
        val globals = JsePlatform.standardGlobals()
        chunk = globals.loadfile(scriptPath)
    }

    fun invoke() {
        chunk.call(LuaValue.valueOf(scriptPath))
    }

}