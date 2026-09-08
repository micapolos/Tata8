package micapolos.zexy3.dsl

import micapolos.zexy3.Action
import micapolos.zexy3.Animation

val instantAnimation: Animation = Animation.Instant
val foreverAnimation: Animation = Animation.Forever
val Action.animation: Animation get() = Animation.WithAction(this)