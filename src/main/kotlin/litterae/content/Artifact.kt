package litterae.content

import litterae.runtime.Instance

class Artifact(
    val identifier: String,
    val title: String,
    val author: String,
    val version: String,
    val segments: List<Segment>,
    val initializer: (Instance).() -> Unit
)