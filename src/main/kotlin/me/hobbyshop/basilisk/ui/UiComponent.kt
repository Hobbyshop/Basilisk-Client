package me.hobbyshop.basilisk.ui

abstract class UiComponent(var x: Int, var y: Int, var width: Int, var height: Int) {

    var visible: Boolean = true

    abstract fun renderComponent(mouseX: Int, mouseY: Int, ingame: Boolean)

    fun moueClicked(mouseButton: Int, mouseX: Int, mouseY: Int) {}

    fun hovered(mouseX: Int, mouseY: Int) = mouseX >= x &&
                                            mouseY >= y &&
                                            mouseX < x + width &&
                                            mouseY < y + height

}