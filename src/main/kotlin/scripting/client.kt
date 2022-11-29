package scripting

import me.hobbyshop.basilisk.script.ClientConsole
import net.minecraft.client.Minecraft
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.TwoArgFunction
import org.luaj.vm2.lib.ZeroArgFunction

class client : TwoArgFunction() {

    override fun call(arg1: LuaValue?, env: LuaValue): LuaValue {
        val lib = tableOf()

        lib.set("exit", exit())
        lib.set("reload_console", reload_console())

        env.set("client", lib)
        return lib
    }

    private class exit : ZeroArgFunction() {
        override fun call(): LuaValue {
            Minecraft.getMinecraft().shutdown()
            return CALL
        }
    }

    private class reload_console : ZeroArgFunction() {
        override fun call(): LuaValue {
            ClientConsole.instance.loadCommands()
            return CALL
        }
    }

}