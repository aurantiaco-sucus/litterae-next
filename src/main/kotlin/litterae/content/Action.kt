package litterae.content

import litterae.compose.CompositionLambda

class Action(
    val name: String,
    val category: ActionCategory,
    val description: String? = null,
    val lambda: CompositionLambda
)