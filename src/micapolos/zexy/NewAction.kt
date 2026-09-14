package micapolos.zexy

import micapolos.zexy.examples.Zexy
import micapolos.zexy.model.Action as ModelAction
import micapolos.zexy.model.Variable as ModelVariable
import micapolos.zexy.model.Void as ModelVoid

class Action2 internal constructor(internal val model: ModelAction) {
  @Zexy
  class Builder internal constructor() {
    internal val modelActions: MutableList<ModelAction> = mutableListOf()

    internal fun add(modelAction: ModelAction) {
      modelActions.add(modelAction)
    }

    internal fun buildModelActions() = modelActions.toList()

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

context(actionBuilder: Action2.Builder)
infix fun <T : Value<T>> Value<T>.bind2(value: Value<T>) {
  actionBuilder.add(ModelAction.Bind(model as ModelVariable<ModelVoid>, value.model))
}

context(actionBuilder: Action2.Builder)
infix fun <T : Value<T>> Value<T>.set2(value: Value<T>) {
  actionBuilder.add(ModelAction.Set(model as ModelVariable<ModelVoid>, value.model))
}

fun actionModel(fn: Action2.Builder.() -> Unit) = Action2.Builder().apply { fn() }.buildModel()
fun action(fn: Action2.Builder.() -> Unit) = Action2.Builder().apply { fn() }.build()
