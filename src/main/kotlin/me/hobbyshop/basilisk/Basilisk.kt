package me.hobbyshop.basilisk

import me.hobbyshop.basilisk.util.Logger

class Basilisk {

    lateinit var logger: Logger

    companion object Instances {
        @JvmStatic
        val instance = Basilisk()

        const val NAME = "Basilisk Client"
        const val VERSION = "1.0"
        const val NAMEVER = "$NAME v$VERSION"
    }

    fun startup() {
        logger = Logger()

        logger.info("Started client")
    }

    fun shutdown() {
        logger.info("Shut down client")
    }

}