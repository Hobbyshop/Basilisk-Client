package me.hobbyshop.basilisk.util;

import com.andreapivetta.kolor.Color
import com.andreapivetta.kolor.Kolor
import me.hobbyshop.basilisk.Basilisk

class Logger {

    fun info(info: String) {
        println(Kolor.foreground("[${Basilisk.NAME} - INFO] $info", Color.GREEN))
    }

    fun warning(warn: String) {
        println(Kolor.foreground("[${Basilisk.NAME} - WARNING] $warn", Color.LIGHT_YELLOW))
    }

    fun error(error: String) {
        println(Kolor.foreground("[${Basilisk.NAME} - ERROR] $error", Color.LIGHT_RED))
    }

    fun fatal(fatal: String) {
        println(Kolor.foreground("[${Basilisk.NAME} - FATAL] $fatal", Color.RED))
    }

}