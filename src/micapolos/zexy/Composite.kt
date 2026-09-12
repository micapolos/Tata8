package micapolos.zexy

import micapolos.zexy.model.Composite as ModelComposite

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