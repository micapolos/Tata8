package micapolos.zexy3.compiler

import micapolos.tata8.Game
import micapolos.zexy3.indexed.*
import micapolos.zexy3.indexed.Number
import micapolos.zexy3.runtime.Animated
import micapolos.zexy3.runtime.Animation
import micapolos.zexy3.runtime.IntEvaluator
import micapolos.zexy3.runtime.noAnimation
import micapolos.zexy3.runtime.then

fun Boolean.toInt() = if (this) 1 else 0

fun Compiler.animatedInteger(integer: Integer): Animated<Int> =
  Animated(integerEvaluator(integer), integerAnimation(integer))

fun Compiler.integerAnimation(integer: Integer): Animation =
  when (integer) {
    is Integer.Apply0 -> noAnimation
    is Integer.Apply1 -> animation(integer.integer)
    is Integer.Apply2 -> animation(integer.lhs).then(animation(integer.rhs))
    is Integer.Constant -> noAnimation
    is Integer.FromNumber -> animation(integer.number)
    is Integer.ImageHeight -> animation(integer.image)
    is Integer.ImageWidth -> animation(integer.image)
    is Integer.KeyDown -> noAnimation
    is Integer.TextHeight -> animation(integer.font).then(animation(integer.text))
    is Integer.TextWidth -> animation(integer.font).then(animation(integer.text))
    is Number.Test2 -> animation(integer.lhs).then(animation(integer.rhs))
  }

fun Compiler.integerEvaluator(integer: Integer): IntEvaluator =
  when (integer) {
    is Integer.Constant -> IntEvaluator { integer.i }

    is Integer.FromNumber -> {
      val d = doubleEvaluator(integer.number)
      IntEvaluator { d.eval().toInt() }
    }

    is Integer.Apply0 ->
      when (integer.op) {
        Integer.Op0.SCREEN_WIDTH -> IntEvaluator { Game.WIDTH }
        Integer.Op0.SCREEN_HEIGHT -> IntEvaluator { Game.HEIGHT }
        Integer.Op0.MOUSE_DOWN -> IntEvaluator { Game.mouse.button.isPressed.toInt() }
      }

    is Integer.Apply1 -> {
      val i = intEvaluator(integer.integer)
      when (integer.op) {
        Integer.Op1.NEG -> IntEvaluator { -i.eval() }
      }
    }

    is Integer.Apply2 -> {
      val lhs = intEvaluator(integer.lhs)
      val rhs = intEvaluator(integer.rhs)
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
      val lhs = doubleEvaluator(integer.lhs)
      val rhs = doubleEvaluator(integer.rhs)
      when (integer.pred) {
        Number.Pred2.EQ -> IntEvaluator { (lhs.eval() == rhs.eval()).toInt() }
        Number.Pred2.LT -> IntEvaluator { (lhs.eval() < rhs.eval()).toInt() }
      }
    }

    is Integer.ImageHeight -> {
      val image = imageEvaluator(integer.image as Image)
      IntEvaluator {
        val image = image.eval()
        if (image == null) 0 else image.size.height
      }
    }

    is Integer.ImageWidth -> {
      val image = imageEvaluator(integer.image as Image)
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
      val text = textEvaluator(integer.text as Text)
      val font = fontEvaluator(integer.font as Font)
      IntEvaluator { font.eval().width(text.eval()) }
    }

    is Integer.TextWidth -> {
      val text = textEvaluator(integer.text as Text)
      val font = fontEvaluator(integer.font as Font)
      IntEvaluator { font.eval().height(text.eval()) }
    }
  }

