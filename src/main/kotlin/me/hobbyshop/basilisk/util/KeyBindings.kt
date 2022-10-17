package me.hobbyshop.basilisk.util

import net.minecraft.client.Minecraft
import net.minecraft.client.settings.KeyBinding
import org.apache.commons.lang3.ArrayUtils
import org.lwjgl.input.Keyboard

object KeyBindings {

    val MOD_SCREEN = KeyBinding("Settings Menu", Keyboard.KEY_RSHIFT, "Basilisk")

    fun registerBindings() {
        Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.add(Minecraft.getMinecraft().gameSettings.keyBindings, MOD_SCREEN)
    }

}