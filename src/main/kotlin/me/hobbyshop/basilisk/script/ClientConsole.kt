package me.hobbyshop.basilisk.script

import me.hobbyshop.basilisk.Basilisk
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class ClientConsole {

    companion object {
        val instance: ClientConsole = ClientConsole()

        private val commands: HashMap<String, ScriptHandler> = hashMapOf()
    }

    val history: MutableList<String> = mutableListOf()

    fun loadCommands() {
        commands.clear()

        val config = File("basilisk/scripts/cmd-config.yml")
        val reader = BufferedReader(InputStreamReader(FileInputStream(config)))

        var line: String?
        do {
            line = reader.readLine()

            if (line == null || line == "" || line.startsWith('#'))
                continue

            val splits = line.split(':')

            val cmdName = splits[0].trim()
            val scriptPath = splits.subList(1, splits.size).joinToString("").trim()

            commands[cmdName] = ScriptHandler.getScriptByName(scriptPath)
        } while (line != null)

        reader.close()
    }

    fun send(message: String) {
        history.add(message)
    }

    fun performCommand(command: String) {
        this.send("> $command")
        println("[CONSOLE] > $command")

        val args = command.split(' ').toMutableList()
        val cmd = args.removeFirst()

        if (!commands.containsKey(cmd)) {
            Basilisk.instance.logger.error("The command $command could not be found")
            return
        }

        commands[cmd]!!.invoke()
    }

}