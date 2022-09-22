package me.hobbyshop.basilisk.util.event

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class EventTarget(val value: Byte = 2)
