package micapolos.zexy

import micapolos.zexy.model.Action as ModelAction
import micapolos.zexy.model.Variable as ModelVariable
import micapolos.zexy.model.Void as ModelVoid

class Action2 internal constructor(internal val model: ModelAction) {
  class Builder internal constructor() {
    internal val modelActions: MutableList<ModelAction> = mutableListOf()

    internal fun add(modelAction: ModelAction) {
      modelActions.add(modelAction)
    }

    internal fun buildModelActions() = modelActions.toList()

    infix fun <T : Value<T>> Value<T>.set(value: Value<T>) {
      add(ModelAction.Set(model as ModelVariable<ModelVoid>, value.model))
    }

    infix fun <T : Value<T>> Value<T>.capture(value: Value<T>) {
      add(ModelAction.Capture(model as ModelVariable<ModelVoid>, value.model))
    }

    fun sequence(fn: Builder.() -> Unit) {
      add(ModelAction.Sequence(Builder().apply { fn() }.buildModelActions()))
    }

    infix fun Value<Integer>.select(fn: Builder.() -> Unit) {
      add(ModelAction.Select(modelInteger, Builder().apply { fn() }.buildModelActions()))
    }

    internal fun buildModelOrNull() =
      if (modelActions.isEmpty()) {
        null
      } else {
        ModelAction.Sequence(buildModelActions())
      }.also { modelActions.clear() }

    internal fun buildModel() = buildModelOrNull() ?: ModelAction.Empty
    internal fun buildOrNull() = buildModelOrNull()?.let { Action2(it)}
    internal fun build() = Action2(buildModel())
  }
}

fun action(fn: Action2.Builder.() -> Unit) = Action2.Builder().apply { fn() }.build()
