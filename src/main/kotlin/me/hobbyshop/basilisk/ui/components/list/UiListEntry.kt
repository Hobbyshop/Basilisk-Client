package me.hobbyshop.basilisk.ui.components.list

abstract class UiListEntry {

    var selected = false

    abstract fun renderEntry(x: Int, y: Int, width: Int, height: Int, index: Int)

}