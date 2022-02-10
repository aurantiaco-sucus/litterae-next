package xyz.midnight233.litterae.common

import kotlin.reflect.KProperty

interface Reference<out T> {
    val value: T
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = value
}