package me.hobbyshop.basilisk.mod

import me.hobbyshop.basilisk.settings.Setting
import me.hobbyshop.basilisk.settings.SettingsContainer

abstract class Module : SettingsContainer() {

    init {
        this.settings.add(Setting("enabled", "", true))
    }

    fun onEnabled() {
        println("enablewd")
    }
    fun onDisabled() {
        println("disablewd")
    }

    fun toggleEnabled() {
        this.setSetting("enabled", if (getSetting("enabled").settingValue == true) {
                                            this.onDisabled()
                                            Setting("enabled", "", false)
                                        }
                                        else {
                                            this.onEnabled()
                                            Setting("enabled", "", true)
                                        }

        )
    }

    fun getData() = this.javaClass.getAnnotation(ModuleData::class.java)

}