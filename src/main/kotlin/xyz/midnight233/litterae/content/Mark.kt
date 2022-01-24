package xyz.midnight233.litterae.content

import kotlin.reflect.KProperty

class Mark(val path: (prop: KProperty<*>) -> String) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        TODO()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        TODO()
    }
}