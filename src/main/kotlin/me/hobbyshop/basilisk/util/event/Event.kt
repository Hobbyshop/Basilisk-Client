package me.hobbyshop.basilisk.util.event

abstract class Event {

    companion object {

        private fun call(e: Event) {
            val dataList = EventManager.get(e::class.java) ?: return

            for (dat in dataList)
                dat.target.invoke(dat.source, e)
        }

    }

    var cancelled: Boolean = false

    enum class State(string: String, number: Int) {
        PRE("PRE", 0),
        POST("POST", 1)
    }

    fun call(): Event {
        this.cancelled = false
        call(this)
        return this
    }

}