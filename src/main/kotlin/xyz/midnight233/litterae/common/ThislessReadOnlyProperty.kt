package xyz.midnight233.litterae.common

import kotlin.reflect.KProperty

fun interface ThislessReadOnlyProperty<T> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T?
}