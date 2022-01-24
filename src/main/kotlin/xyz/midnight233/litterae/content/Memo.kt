package xyz.midnight233.litterae.content

import kotlin.reflect.KProperty

class Memo(val path: (prop: KProperty<*>) -> String) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        TODO()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        TODO()
    }
}