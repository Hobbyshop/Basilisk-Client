package me.hobbyshop.basilisk.script

import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.jse.JsePlatform

class ScriptHandler(val scriptPath: String) {

    private val chunk: LuaValue

    init {
        val globals = JsePlatform.standardGlobals()
        chunk = globals.loadfile(scriptPath)
    }

    fun invoke() {
        chunk.call(LuaValue.valueOf(scriptPath))
    }

}