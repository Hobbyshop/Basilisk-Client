package me.hobbyshop.basilisk.mod

import me.hobbyshop.basilisk.Basilisk
import me.hobbyshop.basilisk.settings.SettingsManager
import org.reflections.Reflections

class ModuleManager {

    val modules: MutableList<Module> = mutableListOf()

    init {
        val reflections = Reflections("me.hobbyshop.basilisk.mod")

        for (clazz in reflections.getTypesAnnotatedWith(ModuleData::class.java)) {
            if (!(clazz.newInstance() is Module)) {
                Basilisk.instance.logger.error("Class ${clazz.simpleName} is annotated with ModuleData.class but does not implement the Module class")
                continue
            }
            val mod = clazz.newInstance() as Module
            modules.add(mod)
            SettingsManager.register(mod)

            Basilisk.instance.logger.info("Initialized mod: ${mod.getData().name}")
        }
    }

    fun getModule(type: Class<Module>): Module {
        val mod = modules.stream().filter {
            return@filter it.javaClass == type
        }.findFirst()

        if (!mod.isPresent)
            Basilisk.instance.logger.error("Unable to find entry of type ${type.simpleName} in modules")

        return mod.get()
    }

}