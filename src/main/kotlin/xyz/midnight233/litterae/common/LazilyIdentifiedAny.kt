package xyz.midnight233.litterae.common

abstract class LazilyIdentifiedAny {
    var objectIdentifier: String? = null

    companion object {
        fun <T : LazilyIdentifiedAny> referenceOf(instance: T) = ThislessReadOnlyProperty { _, property ->
            if (instance.objectIdentifier == null) instance.objectIdentifier = property.name
            instance
        }
    }
}