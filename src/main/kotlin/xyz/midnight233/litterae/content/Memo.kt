package xyz.midnight233.litterae.content

import xyz.midnight233.litterae.runtime.Instance
import kotlin.reflect.KProperty

class Memo(val path: (prop: KProperty<*>) -> String) {
    lateinit var address: String

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        if (!this::address.isInitialized) address = path(property)
        return if (Instance.current.memos.containsKey(address)) {
            Instance.current.memos[address]!!
        } else ""
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        if (!this::address.isInitialized) address = path(property)
        Instance.current.memos[address] = value
    }
}