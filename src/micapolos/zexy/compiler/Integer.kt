package micapolos.zexy.compiler

import micapolos.tata8.Game
import micapolos.zexy.indexed.Integer
import micapolos.zexy.indexed.Number
import micapolos.zexy.runtime.Evaluator
import micapolos.zexy.runtime.IntEvaluator
import micapolos.zexy.runtime.textLengthEvaluator
import micapolos.tata8.Font as TataFont
import micapolos.tata8.Image as TataImage

fun Boolean.toInt() = if (this) 1 else 0

fun Compiler.integerEvaluator(integer: Integer): Evaluator<Int> =
  when (integer) {
    is Integer.Constant -> IntEvaluator { integer.i }

    is Integer.FromNumber -> {
      val doubleEvaluator = evaluator(integer.number)
      IntEvaluator { doubleEvaluator.evalDouble().toInt() }
    }

    is Integer.Apply0 ->
      when (integer.op) {
        Integer.Op0.SCREEN_WIDTH -> IntEvaluator { Game.WIDTH }
        Integer.Op0.SCREEN_HEIGHT -> IntEvaluator { Game.HEIGHT }
        Integer.Op0.MOUSE_DOWN -> IntEvaluator { Game.mouse.button.isPressed.toInt() }
        Integer.Op0.MOUSE_X -> IntEvaluator { Game.mouse.position.x }
        Integer.Op0.MOUSE_Y -> IntEvaluator { Game.mouse.position.y }
      }

    is Integer.Apply1 -> {
      val intEvaluator = intEvaluator(integer.integer)
      when (integer.op) {
        Integer.Op1.NEG -> IntEvaluator { -intEvaluator.eval() }
        Integer.Op1.NOT_ZERO -> IntEvaluator { (intEvaluator.eval() != 0).toInt() }
      }
    }

    is Integer.Apply2 -> {
      val lhsEvaluator = intEvaluator(integer.lhs)
      val rhsEvaluator = intEvaluator(integer.rhs)
      when (integer.op) {
        Integer.Op2.ADD -> IntEvaluator { lhsEvaluator.eval() + rhsEvaluator.eval() }
        Integer.Op2.SUB -> IntEvaluator { lhsEvaluator.eval() - rhsEvaluator.eval() }
        Integer.Op2.MUL -> IntEvaluator { lhsEvaluator.eval() * rhsEvaluator.eval() }
        Integer.Op2.DIV -> IntEvaluator { lhsEvaluator.eval() / rhsEvaluator.eval() }
        Integer.Op2.REM -> IntEvaluator { lhsEvaluator.eval() % rhsEvaluator.eval() }
        Integer.Op2.EQ -> IntEvaluator { (lhsEvaluator.eval() == rhsEvaluator.eval()).toInt() }
        Integer.Op2.CMP -> IntEvaluator { lhsEvaluator.eval().compareTo(rhsEvaluator.eval()) }
        Integer.Op2.AND -> IntEvaluator { lhsEvaluator.eval() and rhsEvaluator.eval() }
        Integer.Op2.OR -> IntEvaluator { lhsEvaluator.eval() or rhsEvaluator.eval() }
        Integer.Op2.XOR -> IntEvaluator { lhsEvaluator.eval() xor rhsEvaluator.eval() }
      }
    }

    is Number.Test2 -> {
      val lhsEvaluator = doubleEvaluator(integer.lhs)
      val rhsEvaluator = doubleEvaluator(integer.rhs)
      when (integer.pred) {
        Number.NumberPred2.EQ -> IntEvaluator { (lhsEvaluator.eval() == rhsEvaluator.eval()).toInt() }
        Number.NumberPred2.CMP -> IntEvaluator { lhsEvaluator.eval().compareTo(rhsEvaluator.eval()) }
      }
    }

    is Integer.ImageHeight -> {
      val imageEvaluator = objectEvaluator<TataImage?>(integer.image)
      IntEvaluator {
        val image = imageEvaluator.eval()
        if (image == null) 0 else image.size.height
      }
    }

    is Integer.ImageWidth -> {
      val imageEvaluator = objectEvaluator<TataImage?>(integer.image)
      IntEvaluator {
        val image = imageEvaluator.eval()
        if (image == null) 0 else image.size.width
      }
    }

    is Integer.KeyDown -> {
      val key = integer.key.tata
      IntEvaluator { key.isPressed.toInt() }
    }

    is Integer.TextLength ->
      textLengthEvaluator(objectEvaluator(integer.text))

    is Integer.TextWidth -> {
      val textEvaluator = objectEvaluator<String>(integer.text)
      val fontEvaluator = objectEvaluator<TataFont>(integer.font)
      IntEvaluator { fontEvaluator.eval().width(textEvaluator.eval()) }
    }

    is Integer.TextHeight -> {
      val textEvaluator = objectEvaluator<String>(integer.text)
      val fontEvaluator = objectEvaluator<TataFont>(integer.font)
      IntEvaluator { fontEvaluator.eval().height(textEvaluator.eval()) }
    }
  }
