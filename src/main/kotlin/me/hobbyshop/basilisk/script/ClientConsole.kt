package me.hobbyshop.basilisk.script

import me.hobbyshop.basilisk.Basilisk
import java.io.BufferedReader
import java.io.InputStreamReader

class ClientConsole {

    companion object {
        val instance: ClientConsole = ClientConsole()

        private val commands: HashMap<String, String> = hashMapOf()
    }

    val history: MutableList<String> = mutableListOf()

    fun loadCommands() {
        val stream = javaClass.getResourceAsStream("/scripts/cmd-config.yml")
        val reader = BufferedReader(InputStreamReader(stream))

        var line: String?
        do {
            line = reader.readLine()

            if (line == null || line == "" || line.startsWith('#'))
                continue

            val splits = line.split(':')

            val cmdName = splits[0]
            val scriptPath = splits.subList(1, splits.size).joinToString("")

            commands[cmdName] = scriptPath
        } while (line != null)

        reader.close()
    }

    fun send(message: String) {
        history.add(message)
    }

    fun performCommand(command: String) {
        val args = command.split(' ').toMutableList()
        val cmd = args.removeFirst()

        if (!commands.containsKey(cmd)) {
            Basilisk.instance.logger.error("The command $command could not be found")
            return
        }

        // perform command
        Basilisk.instance.logger.info("$cmd - $args")
    }

}