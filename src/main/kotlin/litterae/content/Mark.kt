package litterae.content

import litterae.runtime.Instance
import kotlin.reflect.KProperty

class Mark(val path: (KProperty<*>) -> String) {
    lateinit var address: String

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
        if (!this::address.isInitialized) address = path(property)
        return Instance.current.marks.contains(address)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        if (!this::address.isInitialized) address = path(property)
        if (value && !Instance.current.marks.contains(address)) Instance.current.marks += address
        else if (!value && Instance.current.marks.contains(address)) Instance.current.marks -= address
    }
}