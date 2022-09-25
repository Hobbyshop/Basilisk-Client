package me.hobbyshop.basilisk.util;

import com.andreapivetta.kolor.Color
import com.andreapivetta.kolor.Kolor
import me.hobbyshop.basilisk.Basilisk
import me.hobbyshop.basilisk.script.ClientConsole

class Logger {

    private val clientConsole = ClientConsole.instance

    fun info(info: String) {
        println(Kolor.foreground("[${Basilisk.NAME} - INFO] $info", Color.GREEN))
        clientConsole.send("[INFO] $info")
    }

    fun warning(warn: String) {
        println(Kolor.foreground("[${Basilisk.NAME} - WARNING] $warn", Color.LIGHT_YELLOW))
        clientConsole.send("[WARNING] $warn")
    }

    fun error(error: String) {
        println(Kolor.foreground("[${Basilisk.NAME} - ERROR] $error", Color.LIGHT_RED))
        clientConsole.send("[ERROR] $error")
    }

    fun fatal(fatal: String) {
        println(Kolor.foreground("[${Basilisk.NAME} - FATAL] $fatal", Color.RED))
        clientConsole.send("[FATAL] $fatal")
    }

}