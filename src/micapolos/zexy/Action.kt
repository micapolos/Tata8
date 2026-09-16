package micapolos.zexy

import micapolos.zexy.model.Action as ModelAction
import micapolos.zexy.model.Value as ModelValue
import micapolos.zexy.model.Variable as ModelVariable

class Action internal constructor(model: ModelAction): ValueWithModel<Action>(model) {
  @Zexy
  class Block internal constructor() {
    internal val modelActions: MutableList<ModelValue<ModelAction>> = mutableListOf()

    internal fun add(modelAction: ModelValue<ModelAction>) {
      modelActions.add(modelAction)
    }

    internal fun buildModelActions() = modelActions.toList()

    fun sequence(fn: Block.() -> Unit) {
      add(ModelAction.Sequence(Block().apply { fn() }.buildModelActions()))
    }

    fun ifTrue(condition: Value<Bool>, fn: Block.() -> Unit) {
      add(
        ModelValue.Select(
          condition.integer.modelInteger,
          listOf(
            noAction.modelAction,
            Block().apply { fn() }.buildModel())))
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

internal val Value<Action>.modelAction get() = model as ModelValue<ModelAction>

val noAction = Action(ModelAction.Empty)

fun <T: Value<T>> Variable<T>.set(value: Value<T>) =
  Action(ModelAction.Set(model as ModelVariable<ModelAction>, value.model))

fun <T: Value<T>> Variable<T>.bind(value: Value<T>) =
  Action(ModelAction.Bind(model as ModelVariable<ModelAction>, value.model))

fun sequence(action: Value<Action>, vararg actions: Value<Action>) =
  sequence(listOf(action, *actions))

fun sequence(actions: List<Value<Action>>) =
  Action(ModelAction.Sequence(actions.map { it.modelAction }))

val <T: Value<T>> Value<T>.log get() = logAs(null)

infix fun <T: Value<T>> Value<T>.logAs(label: String?) =
  Action(ModelAction.Log(label, model))

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
  actionBlock.add(ModelAction.Bind(model as ModelVariable<ModelAction>, value.model))
}

context(actionBlock: Action.Block)
infix fun <T : Value<T>> Value<T>.set(value: Value<T>) {
  actionBlock.add(ModelAction.Set(model as ModelVariable<ModelAction>, value.model))
}

fun actionModel(fn: Action.Block.() -> Unit) = Action.Block().apply { fn() }.buildModel()
fun action(fn: Action.Block.() -> Unit) = Action.Block().apply { fn() }.build()
