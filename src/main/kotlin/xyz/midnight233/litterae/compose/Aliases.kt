package xyz.midnight233.litterae.compose

typealias CompositionLambda = Composition.() -> Unit

fun composition(content: CompositionLambda) = content