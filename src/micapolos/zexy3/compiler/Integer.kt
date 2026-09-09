package micapolos.zexy3.compiler

import micapolos.tata8.Game
import micapolos.zexy3.indexed.*
import micapolos.zexy3.indexed.Number
import micapolos.zexy3.runtime.IntEvaluator

fun Boolean.toInt() = if (this) 1 else 0

fun Compiler.compile(integer: Integer): IntEvaluator =
  when (integer) {
    is Integer.Constant -> IntEvaluator { integer.i }

    is Integer.FromNumber -> {
      val d = compile(integer.number as Number)
      IntEvaluator { d.eval().toInt() }
    }

    is Integer.Apply0 ->
      when (integer.op) {
        Integer.Op0.SCREEN_WIDTH -> IntEvaluator { Game.WIDTH }
        Integer.Op0.SCREEN_HEIGHT -> IntEvaluator { Game.HEIGHT }
        Integer.Op0.MOUSE_DOWN -> IntEvaluator { Game.mouse.button.isPressed.toInt() }
      }

    is Integer.Apply1 -> {
      val i = compile(integer.integer as Integer)
      when (integer.op) {
        Integer.Op1.NEG -> IntEvaluator { -i.eval() }
      }
    }

    is Integer.Apply2 -> {
      val lhs = compile(integer.lhs as Integer)
      val rhs = compile(integer.rhs as Integer)
      when (integer.op) {
        Integer.Op2.ADD -> IntEvaluator { lhs.eval() + rhs.eval() }
        Integer.Op2.SUB -> IntEvaluator { lhs.eval() - rhs.eval() }
        Integer.Op2.MUL -> IntEvaluator { lhs.eval() * rhs.eval() }
        Integer.Op2.EQ -> IntEvaluator { (lhs.eval() == rhs.eval()).toInt() }
        Integer.Op2.LT -> IntEvaluator { (lhs.eval() < rhs.eval()).toInt() }
      }
    }

    is Number.Test2 -> {
      val lhs = compile(integer.lhs as Number)
      val rhs = compile(integer.rhs as Number)
      when (integer.pred) {
        Number.Pred2.EQ -> IntEvaluator { (lhs.eval() == rhs.eval()).toInt() }
        Number.Pred2.LT -> IntEvaluator { (lhs.eval() < rhs.eval()).toInt() }
      }
    }

    is Integer.ImageHeight -> {
      val image = compile(integer.image as Image)
      IntEvaluator { image.eval().size.height }
    }

    is Integer.ImageWidth -> {
      val image = compile(integer.image as Image)
      IntEvaluator { image.eval().size.width }
    }

    is Integer.KeyDown -> {
      val key = integer.key.tata
      IntEvaluator { key.isPressed.toInt() }
    }

    is Integer.TextHeight -> {
      val text = compile(integer.text as Text)
      val font = compile(integer.font as Font)
      IntEvaluator { font.eval().width(text.eval()) }
    }

    is Integer.TextWidth -> {
      val text = compile(integer.text as Text)
      val font = compile(integer.font as Font)
      IntEvaluator { font.eval().height(text.eval()) }
    }
  }

