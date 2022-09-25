package me.hobbyshop.basilisk.util.event

import me.hobbyshop.basilisk.util.ArrayHelper
import java.lang.reflect.Method

object EventManager {

    private val registeredEvents: HashMap<Class<out Event>, ArrayHelper<EventData>> = hashMapOf()

    fun register(obj: Any) {
        for (method in obj::class.java.methods) {
            if (this.isMethodValid(method))
                this.register(method, obj)
        }
    }

    fun register(obj: Any, clazz: Class<out Event>) {
        for (method in obj::class.java.methods) {
            if (this.isMethodValid(method, clazz))
                this.register(method, obj)
        }
    }

    private fun register(method: Method, obj: Any) {
        val clazz = method.parameterTypes[0]
        val methodData = EventData(obj, method, method.getAnnotation(EventTarget::class.java).value)

        if (!methodData.target.isAccessible)
            methodData.target.isAccessible = true

        if (this.registeredEvents.containsKey(clazz)) {
            if (this.registeredEvents[clazz]?.contains(methodData)!!) return

            this.registeredEvents[clazz]?.add(methodData)
            this.sortListValue(clazz as Class<out Event>)

        } else {
            this.registeredEvents[clazz as Class<out Event>] = ArrayHelper<EventData>()
            this.registeredEvents[clazz]?.add(methodData)
        }
    }

    fun unregister(obj: Any) {
        for (flexArray in this.registeredEvents.values) {
            for (methodData in flexArray) {

                if (methodData.source == obj)
                    flexArray.remove(methodData)

            }
        }
        this.clearMap(true)
    }

    fun unregister(obj: Any, clazz: Class<out Event>) {
        if (this.registeredEvents.containsKey(clazz)) {
            for (methodData in this.registeredEvents[clazz]!!) {

                if (methodData.source == obj)
                    this.registeredEvents[clazz]?.remove(methodData)

            }
            this.clearMap(true)
        }
    }

    fun clearMap(b: Boolean) {
        val iterator = this.registeredEvents.iterator()

        while (iterator.hasNext()) {
            if (!b || iterator.next().value.isEmpty)
                iterator.remove()
        }
    }

    fun removeEntry(clazz: Class<out Event>) {
        val iterator = this.registeredEvents.iterator()

        while (iterator.hasNext()) {
            if (iterator.next().key == clazz) {
                iterator.remove()
                break
            }
        }
    }

    private fun sortListValue(clazz: Class<out Event>) {
        val flexArray = ArrayHelper<EventData>()

        for (b: Byte in EventPriority.VALUE_ARRAY) {
            for (methodData in this.registeredEvents[clazz]!!) {

                if (methodData.priority == b)
                    flexArray.add(methodData)

            }
        }

        this.registeredEvents[clazz] = flexArray
    }

    private fun isMethodValid(method: Method) =
        method.parameterCount == 1 && method.isAnnotationPresent(EventTarget::class.java)

    private fun isMethodValid(method: Method, clazz: Class<out Event>) =
        this.isMethodValid(method) && method.parameterTypes[0].equals(clazz)

    fun get(clazz: Class<out Event>) =
        this.registeredEvents[clazz]

    fun shutdown() {
        this.registeredEvents.clear()
    }

}