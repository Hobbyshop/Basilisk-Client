package me.hobbyshop.basilisk.settings

class Setting<T>(val name: String, val description: String, default: T) {

    var settingValue: T

    init {
        settingValue = default
    }

}