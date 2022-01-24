package xyz.midnight233.litterae.compose

typealias CompositionLambda = Composition.() -> Unit
typealias DetectionLambda = Detection.() -> Boolean

fun composition(content: CompositionLambda) = content