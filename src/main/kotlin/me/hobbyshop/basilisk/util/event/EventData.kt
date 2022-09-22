package me.hobbyshop.basilisk.util.event

import java.lang.reflect.Method

class EventData(val source: Any, val target: Method, val priority: Byte)