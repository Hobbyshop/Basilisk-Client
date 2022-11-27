package scripting

import me.hobbyshop.basilisk.Basilisk
import me.hobbyshop.basilisk.script.ClientConsole
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.OneArgFunction
import org.luaj.vm2.lib.TwoArgFunction

class logger : TwoArgFunction() {

    override fun call(arg1: LuaValue?, env: LuaValue): LuaValue {
        val lib = tableOf()

        lib.set("raw", raw())
        lib.set("info", info())
        lib.set("warning", warn())
        lib.set("error", error())
        lib.set("fatal", fatal())

        env.set("logger", lib)
        return lib
    }

    private class raw : OneArgFunction() {
        override fun call(text: LuaValue): LuaValue {
            ClientConsole.instance.send(text.toString())
            println(text.toString())
            return LuaValue.CALL
        }
    }

    private class info : OneArgFunction() {
        override fun call(text: LuaValue): LuaValue {
            Basilisk.instance.logger.info(text.toString())
            return LuaValue.CALL
        }
    }

    private class warn : OneArgFunction() {
        override fun call(text: LuaValue): LuaValue {
            Basilisk.instance.logger.warning(text.toString())
            return CALL
        }
    }

    private class error : OneArgFunction() {
        override fun call(text: LuaValue): LuaValue {
            Basilisk.instance.logger.error(text.toString())
            return LuaValue.CALL
        }
    }

    private class fatal : OneArgFunction() {
        override fun call(text: LuaValue): LuaValue {
            Basilisk.instance.logger.fatal(text.toString())
            return LuaValue.CALL
        }
    }

}