package micapolos.zexy3.indexer

import micapolos.zexy3.indexed.Integer
import micapolos.zexy3.indexed.Integer.*
import micapolos.zexy3.indexed.Number
import micapolos.zexy3.model.Integer as ModelInteger
import micapolos.zexy3.model.Integer.Op0 as ModelOp0
import micapolos.zexy3.model.Integer.Op1 as ModelOp1
import micapolos.zexy3.model.Integer.Op2 as ModelOp2
import micapolos.zexy3.model.Number as ModelNumber

val ModelOp0.indexed
  get() =
    when (this) {
      ModelOp0.SCREEN_WIDTH -> Op0.SCREEN_WIDTH
      ModelOp0.SCREEN_HEIGHT -> Op0.SCREEN_HEIGHT
      ModelOp0.MOUSE_DOWN -> Op0.MOUSE_DOWN
    }

val ModelOp1.indexed
  get() =
    when (this) {
      ModelOp1.NEG -> Op1.NEG
    }

val ModelOp2.indexed
  get() =
    when (this) {
      ModelOp2.ADD -> Op2.ADD
      ModelOp2.SUB -> Op2.SUB
      ModelOp2.MUL -> Op2.MUL
      ModelOp2.EQ -> Op2.EQ
      ModelOp2.LT -> Op2.LT
      ModelOp2.AND -> Op2.AND
      ModelOp2.OR -> Op2.OR
      ModelOp2.XOR -> Op2.XOR
    }

val ModelNumber.Pred2.indexed
  get() =
    when (this) {
      ModelNumber.Pred2.EQ -> Number.Pred2.EQ
      ModelNumber.Pred2.LT -> Number.Pred2.LT
    }

fun Indexer.indexed(model: ModelInteger): Integer =
  when (model) {
    is ModelInteger.Constant -> Constant(model.i)
    is ModelInteger.Apply0 -> Apply0(model.op.indexed)
    is ModelInteger.Apply1 -> Apply1(model.op.indexed, indexed(model.integer))
    is ModelInteger.Apply2 -> Apply2(model.op.indexed, indexed(model.lhs), indexed(model.rhs))
    is ModelInteger.FromNumber -> FromNumber(indexed(model.number))
    is ModelInteger.ImageWidth -> ImageWidth(indexed(model.image))
    is ModelInteger.ImageHeight -> ImageHeight(indexed(model.image))
    is ModelInteger.KeyDown -> KeyDown(model.key.indexed)
    is ModelInteger.TextWidth -> TextWidth(indexed(model.text), indexed(model.font))
    is ModelInteger.TextHeight -> TextHeight(indexed(model.text), indexed(model.font))
    is ModelNumber.Test2 -> Number.Test2(model.pred.indexed, indexed(model.lhs), indexed(model.rhs))
  }