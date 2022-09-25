package me.hobbyshop.basilisk.script

class ClientConsole {

    companion object {
        val instance: ClientConsole = ClientConsole()
    }

    val history: MutableList<String> = mutableListOf()

    fun send(message: String) {
        history.add(message)
    }

}