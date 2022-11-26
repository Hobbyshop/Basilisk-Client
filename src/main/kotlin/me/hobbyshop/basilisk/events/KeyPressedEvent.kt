package me.hobbyshop.basilisk.events

import me.hobbyshop.basilisk.util.event.Event

/**
 * Called whenever a key on the users keyboard is pressed
 */
class KeyPressedEvent(val keyCode: Int) : Event()