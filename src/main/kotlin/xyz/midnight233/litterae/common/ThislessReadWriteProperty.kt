package xyz.midnight233.litterae.common

import kotlin.reflect.KProperty

interface ThislessReadWriteProperty<T> {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T?
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
}