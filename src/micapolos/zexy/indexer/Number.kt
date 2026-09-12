package micapolos.zexy.indexer

import micapolos.zexy.indexed.Number
import micapolos.zexy.indexed.Number.*
import micapolos.zexy.model.Number as ModelNumber
import micapolos.zexy.model.Number.Apply0 as ModelApply0
import micapolos.zexy.model.Number.Apply1 as ModelApply1
import micapolos.zexy.model.Number.Apply2 as ModelApply2
import micapolos.zexy.model.Number.Constant as ModelConstant
import micapolos.zexy.model.Number.FromInteger as ModelFromInteger
import micapolos.zexy.model.Number.Op0 as ModelOp0
import micapolos.zexy.model.Number.Op1 as ModelOp1
import micapolos.zexy.model.Number.Op2 as ModelOp2

val ModelOp0.indexed get() =
  when (this) {
    ModelOp0.FRAME_TIME -> Op0.FRAME_TIME
  }

val ModelOp1.indexed get() =
  when (this) {
    ModelOp1.NEG -> Op1.NEG
    ModelOp1.SIN -> Op1.SIN
    ModelOp1.COS -> Op1.COS
    ModelOp1.ABS -> Op1.ABS
    ModelOp1.SQRT -> Op1.SQRT
    ModelOp1.FLOOR -> Op1.FLOOR
    ModelOp1.CEIL -> Op1.CEIL
    ModelOp1.ROUND -> Op1.ROUND
    ModelOp1.FRACT -> Op1.FRACT
  }

val ModelOp2.indexed get() =
  when (this) {
    ModelOp2.ADD -> Op2.ADD
    ModelOp2.SUB -> Op2.SUB
    ModelOp2.MUL -> Op2.MUL
  }

fun Indexer.indexedNumber(model: ModelNumber): Number =
  when (model) {
    is ModelConstant -> Constant(model.d)
    is ModelApply0 -> Apply0(model.op.indexed)
    is ModelApply1 -> Apply1(model.op.indexed, indexed(model.n))
    is ModelApply2 -> Apply2(model.op.indexed, indexed(model.lhs), indexed(model.rhs))
    is ModelFromInteger -> FromInteger(indexed(model.i))
  }