package xyz.midnight233.litterae.content

import xyz.midnight233.litterae.compose.CompositionLambda

class Action(
    val name: String,
    val category: ActionCategory,
    val description: String? = null,
    val lambda: CompositionLambda
)