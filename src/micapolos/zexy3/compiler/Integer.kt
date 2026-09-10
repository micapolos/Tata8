package micapolos.zexy3.compiler

import micapolos.tata8.Game
import micapolos.zexy3.indexed.*
import micapolos.zexy3.indexed.Number
import micapolos.zexy3.runtime.Animated
import micapolos.zexy3.runtime.Animation
import micapolos.zexy3.runtime.IntEvaluator
import micapolos.zexy3.runtime.noAnimation

fun Boolean.toInt() = if (this) 1 else 0

fun Compiler.animation(integer: Integer): Animation =
  when (integer) {
    is Integer.Apply0 -> noAnimation
    is Integer.Apply1 -> noAnimation
    is Integer.Apply2 -> noAnimation
    is Integer.Constant -> noAnimation
    is Integer.FromNumber -> noAnimation
    is Integer.ImageHeight -> noAnimation
    is Integer.ImageWidth -> noAnimation
    is Integer.KeyDown -> noAnimation
    is Integer.TextHeight -> noAnimation
    is Integer.TextWidth -> noAnimation
    is Number.Test2 -> noAnimation
  }

fun Compiler.evaluator(integer: Integer): IntEvaluator =
  when (integer) {
    is Integer.Constant -> IntEvaluator { integer.i }

    is Integer.FromNumber -> {
      val d = evaluator(integer.number as Number)
      IntEvaluator { d.eval().toInt() }
    }

    is Integer.Apply0 ->
      when (integer.op) {
        Integer.Op0.SCREEN_WIDTH -> IntEvaluator { Game.WIDTH }
        Integer.Op0.SCREEN_HEIGHT -> IntEvaluator { Game.HEIGHT }
        Integer.Op0.MOUSE_DOWN -> IntEvaluator { Game.mouse.button.isPressed.toInt() }
      }

    is Integer.Apply1 -> {
      val i = evaluator(integer.integer as Integer)
      when (integer.op) {
        Integer.Op1.NEG -> IntEvaluator { -i.eval() }
      }
    }

    is Integer.Apply2 -> {
      val lhs = evaluator(integer.lhs as Integer)
      val rhs = evaluator(integer.rhs as Integer)
      when (integer.op) {
        Integer.Op2.ADD -> IntEvaluator { lhs.eval() + rhs.eval() }
        Integer.Op2.SUB -> IntEvaluator { lhs.eval() - rhs.eval() }
        Integer.Op2.MUL -> IntEvaluator { lhs.eval() * rhs.eval() }
        Integer.Op2.EQ -> IntEvaluator { (lhs.eval() == rhs.eval()).toInt() }
        Integer.Op2.LT -> IntEvaluator { (lhs.eval() < rhs.eval()).toInt() }
        Integer.Op2.AND -> IntEvaluator { lhs.eval() and rhs.eval() }
        Integer.Op2.OR -> IntEvaluator { lhs.eval() or rhs.eval() }
        Integer.Op2.XOR -> IntEvaluator { lhs.eval() xor rhs.eval() }
      }
    }

    is Number.Test2 -> {
      val lhs = evaluator(integer.lhs as Number)
      val rhs = evaluator(integer.rhs as Number)
      when (integer.pred) {
        Number.Pred2.EQ -> IntEvaluator { (lhs.eval() == rhs.eval()).toInt() }
        Number.Pred2.LT -> IntEvaluator { (lhs.eval() < rhs.eval()).toInt() }
      }
    }

    is Integer.ImageHeight -> {
      val image = evaluator(integer.image as Image)
      IntEvaluator {
        val image = image.eval()
        if (image == null) 0 else image.size.height
      }
    }

    is Integer.ImageWidth -> {
      val image = evaluator(integer.image as Image)
      IntEvaluator {
        val image = image.eval()
        if (image == null) 0 else image.size.width
      }
    }

    is Integer.KeyDown -> {
      val key = integer.key.tata
      IntEvaluator { key.isPressed.toInt() }
    }

    is Integer.TextHeight -> {
      val text = evaluator(integer.text as Text)
      val font = evaluator(integer.font as Font)
      IntEvaluator { font.eval().width(text.eval()) }
    }

    is Integer.TextWidth -> {
      val text = evaluator(integer.text as Text)
      val font = evaluator(integer.font as Font)
      IntEvaluator { font.eval().height(text.eval()) }
    }
  }

