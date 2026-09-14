package micapolos.zexy

import micapolos.zexy.model.Action as ModelAction
import micapolos.zexy.model.Variable as ModelVariable
import micapolos.zexy.model.Void as ModelVoid

class Action2 internal constructor(internal val model: ModelAction) {
  class Builder internal constructor(internal val actions: MutableList<Action2> = mutableListOf()) {
    fun <T: Value<T>> set(variable: Value<T>, value: Value<T>) =
      Action2(ModelAction.Set(variable.model as ModelVariable<ModelVoid>, value.model))

    fun <T: Value<T>> capture(variable: Value<T>, value: Value<T>) =
      Action2(ModelAction.Capture(variable.model as ModelVariable<ModelVoid>, value.model))

    fun <T: Drawing<T>> draw(drawing: Value<T>) =
      Action2(ModelAction.Draw(drawing.modelDrawing))

    fun sequence(fn: Builder.() -> Unit): Action2 =
      Action2(ModelAction.Sequence(Builder().apply { fn() }.actions.map { it.model }))

    fun select(index: Value<Integer>, fn: Action2.Builder.() -> Unit) =
      Action2(ModelAction.Select(index.modelInteger, Builder().apply { fn() }.actions.map { it.model }))
  }
}

fun action(fn: Action2.Builder.() -> Unit): Action2 = Action2.Builder().sequence(fn)