package micapolos.zexy3

import micapolos.zexy3.model.Composite as ModelComposite

enum class Composite {
  NORMAL,
  MULTIPLY,
  SOFT_LIGHT,
}

internal val Composite.model get() =
  when (this) {
    Composite.NORMAL -> ModelComposite.NORMAL
    Composite.MULTIPLY ->  ModelComposite.MULTIPLY
    Composite.SOFT_LIGHT ->  ModelComposite.SOFT_LIGHT
  }