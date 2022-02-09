package xyz.midnight233.litterae.content

import xyz.midnight233.litterae.common.LazilyIdentifiedAny
import xyz.midnight233.litterae.compose.CompositionLambda

class Scene(val segment: Segment, val name: String, val builder: Scene.() -> Unit) : LazilyIdentifiedAny() {
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

    fun action(name: String, category: ActionCategory, description: String? = null, content: CompositionLambda) {
        actions += Action(name, category, description, content)
    }

    fun mark() = Mark { prop -> "Segment(${segment.identity}):Scene(${name}):Mark(${prop.name})" }
    fun memo() = Memo { prop -> "Segment(${segment.identity}):Scene(${name}):Memo(${prop.name})" }
}