package xyz.midnight233.litterae.common

import kotlin.reflect.KProperty

abstract class LatelyIdentifiedAny {
    lateinit var objectIdentifier: String

    companion object {
        fun <T : LatelyIdentifiedAny> MutableCollection<T>.mutableReference() = object : ThislessReadWriteProperty<T> {

            override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
                val instance = this@mutableReference.find { it.objectIdentifier == property.name } ?: return null
                if (!instance::objectIdentifier.isInitialized) instance.objectIdentifier = property.name
                return instance
            }

            override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
                val instance = this@mutableReference.find { it.objectIdentifier == property.name }
                if (instance != null) this@mutableReference -= instance
                if (!value::objectIdentifier.isInitialized) value.objectIdentifier = property.name
                this@mutableReference += value
            }

        }

        fun <T : LatelyIdentifiedAny> MutableCollection<T>.reference() = object : ThislessReadOnlyProperty<T> {

            override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
                val instance = this@reference.find { it.objectIdentifier == property.name } ?: return null
                if (!instance::objectIdentifier.isInitialized) instance.objectIdentifier = property.name
                return instance
            }

        }
    }
}