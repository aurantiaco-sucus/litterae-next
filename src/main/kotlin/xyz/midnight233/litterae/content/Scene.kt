package xyz.midnight233.litterae.content

import xyz.midnight233.litterae.compose.CompositionLambda

class Scene(val segment: Segment, private val index: Int, val name: String, val builder: Scene.() -> Unit) {
    var immediateEvent: CompositionLambda? = null
    var actions = mutableListOf<Action>()

    fun rebuild() {
        immediateEvent = null
        actions = mutableListOf()
        builder(this)
    }

    fun immediate(content: CompositionLambda) {
        immediateEvent = content
    }

    fun action(
        name: String,
        category: ActionCategory = ActionCategory.Context,
        description: String? = null,
        content: CompositionLambda
    ) {
        actions += Action(name, category, description, content)
    }

    fun mark() = Mark { prop -> "Segment(${segment.identifier}):Scene(${index}):Mark(${prop.name})" }
    fun memo() = Memo { prop -> "Segment(${segment.identifier}):Scene(${index}):Memo(${prop.name})" }
}