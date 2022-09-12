package me.hobbyshop.basilisk.settings

abstract class SettingsContainer {

    val settings: MutableList<Setting<*>> = mutableListOf()

}