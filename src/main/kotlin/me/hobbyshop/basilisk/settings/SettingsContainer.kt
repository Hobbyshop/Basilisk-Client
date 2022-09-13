package me.hobbyshop.basilisk.settings

import me.hobbyshop.basilisk.Basilisk

abstract class SettingsContainer {

    val settings: MutableList<Setting<*>> = mutableListOf()

    protected fun getSetting(name: String): Setting<*> {
        val setting = settings.stream().filter {
            return@filter it.name == name
        }.findFirst()

        if (!setting.isPresent)
            Basilisk.instance.logger.error("Could not find setting $name")

        return setting.get()
    }

    protected fun setSetting(name: String, value: Setting<*>) {
        var i = 0
        while (name != settings[i].name)
            ++i

        settings[i] = value
    }

}