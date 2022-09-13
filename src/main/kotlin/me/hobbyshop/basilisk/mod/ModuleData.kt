package me.hobbyshop.basilisk.mod

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ModuleData(val name: String, val description: String, val category: ModCategory)
