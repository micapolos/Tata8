package micapolos.zexy

import micapolos.zexy.model.Action as ModelAction
import micapolos.zexy.model.Variable as ModelVariable
import micapolos.zexy.model.Void as ModelVoid

class Action2 internal constructor(internal val model: ModelAction) {
  class Builder internal constructor(internal val actions: MutableList<Action2> = mutableListOf()) {
    fun <T: Value<T>> Value<T>.set(value: Value<T>) {
      actions.add(Action2(ModelAction.Set(model as ModelVariable<ModelVoid>, value.model)))
    }

    fun <T: Value<T>> Value<T>.capture(value: Value<T>) {
      actions.add(Action2(ModelAction.Capture(model as ModelVariable<ModelVoid>, value.model)))
    }

    fun <T: Drawing<T>> draw(drawing: Value<T>) {
      actions.add(Action2(ModelAction.Draw(drawing.modelDrawing)))
    }

    fun sequence(fn: Builder.() -> Unit) {
      actions.add(Action2(ModelAction.Sequence(Builder().apply { fn() }.actions.map { it.model })))
    }

    fun Value<Integer>.select(fn: Builder.() -> Unit) {
      actions.add(Action2(ModelAction.Select(modelInteger, Builder().apply { fn() }.actions.map { it.model })))
    }

    internal fun build(): Action2 = Action2(ModelAction.Sequence(actions.map { it.model }))
  }
}

fun action(fn: Action2.Builder.() -> Unit): Action2 = Action2.Builder().apply { fn() }.build()