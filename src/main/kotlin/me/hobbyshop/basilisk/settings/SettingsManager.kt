package me.hobbyshop.basilisk.settings

import com.google.gson.Gson
import me.hobbyshop.basilisk.Basilisk
import java.io.File
import java.nio.charset.Charset

object SettingsManager {

    private val gson = Gson()

    private val settingsDir = File("basilisk/settings")
    var containers: HashMap<Class<out SettingsContainer>, SettingsContainer> = hashMapOf()

    init {
        if (!settingsDir.exists())
            settingsDir.mkdirs()
    }

    fun register(instance: SettingsContainer) {
        containers[instance.javaClass] = instance
    }

    fun loadSettings() {
        for (container in containers) {
            val file = this.getConfigFile(container.value)
            if (!file.exists()) {
                Basilisk.instance.logger.warning("Settings container ${container::class.java.simpleName} has no config file")
                return
            }
            val content = file.readText(Charset.defaultCharset())
            containers[container.key] = gson.fromJson(content, container.key)
        }
    }

    fun saveSettings() {
        for (container in containers) {
            val file = this.getConfigFile(container.value)
            if (!file.exists())
                file.createNewFile()

            file.writeText("{\"settings\":" + gson.toJson(container.value.settings) + "}")
            Basilisk.instance.logger.info("Saved settings container: ${container::class.java.simpleName}")
        }
    }

    private fun getConfigFile(container: SettingsContainer) =
        File(settingsDir, container::class.java.simpleName + ".json")

}