package micapolos.zexy

import micapolos.zexy.examples.Zexy
import micapolos.zexy.model.Action as ModelAction
import micapolos.zexy.model.Variable as ModelVariable
import micapolos.zexy.model.Void as ModelVoid

class Action internal constructor(internal val model: ModelAction): Value<Action>(model) {
  @Zexy
  class Block internal constructor() {
    internal val modelActions: MutableList<ModelAction> = mutableListOf()

    internal fun add(modelAction: ModelAction) {
      modelActions.add(modelAction)
    }

    internal fun buildModelActions() = modelActions.toList()

    fun sequence(fn: Block.() -> Unit) {
      add(ModelAction.Sequence(Block().apply { fn() }.buildModelActions()))
    }

    infix fun Value<Integer>.select(fn: Block.() -> Unit) {
      add(ModelAction.Select(modelInteger, Block().apply { fn() }.buildModelActions()))
    }

    internal fun buildModelOrNull() =
      if (modelActions.isEmpty()) {
        null
      } else {
        ModelAction.Sequence(buildModelActions())
      }.also { modelActions.clear() }

    internal fun buildModel() = buildModelOrNull() ?: ModelAction.Empty
    internal fun buildOrNull() = buildModelOrNull()?.let { Action(it)}
    internal fun build() = Action(buildModel())
  }
}

context(actionBlock: Action.Block)
val Value<*>.log: Unit get() {
  actionBlock.add(ModelAction.Log(null, model))
}

context(actionBlock: Action.Block)
infix fun Value<*>.logAs(label: String?) {
  actionBlock.add(ModelAction.Log(label, model))
}

context(actionBlock: Action.Block)
infix fun <T : Value<T>> Value<T>.bind(value: Value<T>) {
  actionBlock.add(ModelAction.Bind(model as ModelVariable<ModelVoid>, value.model))
}

context(actionBlock: Action.Block)
infix fun <T : Value<T>> Value<T>.set(value: Value<T>) {
  actionBlock.add(ModelAction.Set(model as ModelVariable<ModelVoid>, value.model))
}

fun actionModel(fn: Action.Block.() -> Unit) = Action.Block().apply { fn() }.buildModel()
fun action(fn: Action.Block.() -> Unit) = Action.Block().apply { fn() }.build()
