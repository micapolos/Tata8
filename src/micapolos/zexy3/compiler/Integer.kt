package micapolos.zexy3.compiler

import micapolos.tata8.Game
import micapolos.zexy3.indexed.*
import micapolos.zexy3.indexed.Number
import micapolos.zexy3.runtime.*
import micapolos.tata8.Font as TataFont
import micapolos.tata8.Image as TataImage

fun Boolean.toInt() = if (this) 1 else 0

fun Compiler.animatedInteger(integer: Integer): Animated<Int> =
  when (integer) {
    is Integer.Constant ->
      Animated(
        IntEvaluator { integer.i },
        instantAnimation
      )

    is Integer.FromNumber -> {
      val animatedDouble = animated(integer.number)
      val doubleEvaluator = animatedDouble.evaluator as DoubleEvaluator
      Animated(IntEvaluator { doubleEvaluator.eval().toInt() }, animatedDouble.animation)
    }

    is Integer.Apply0 ->
      Animated(
        when (integer.op) {
          Integer.Op0.SCREEN_WIDTH -> IntEvaluator { Game.WIDTH }
          Integer.Op0.SCREEN_HEIGHT -> IntEvaluator { Game.HEIGHT }
          Integer.Op0.MOUSE_DOWN -> IntEvaluator { Game.mouse.button.isPressed.toInt() }
          Integer.Op0.MOUSE_X -> IntEvaluator { Game.mouse.position.x }
          Integer.Op0.MOUSE_Y -> IntEvaluator { Game.mouse.position.y }
        }, infiniteAnimation)

    is Integer.Apply1 -> {
      val animatedInt = animated(integer.integer)
      val intEvaluator = animatedInt.evaluator as IntEvaluator
      Animated(
        when (integer.op) {
          Integer.Op1.NEG -> IntEvaluator { -intEvaluator.eval() }
        }, animatedInt.animation)
    }

    is Integer.Apply2 -> {
      val animatedLhs = animated(integer.lhs)
      val animatedRhs = animated(integer.rhs)
      val lhsEvaluator = animatedLhs.evaluator as IntEvaluator
      val rhsEvaluator = animatedRhs.evaluator as IntEvaluator
      Animated(
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
        }, parallel(animatedLhs.animation, animatedRhs.animation))
    }

    is Number.Test2 -> {
      val animatedLhs = animated(integer.lhs)
      val animatedRhs = animated(integer.rhs)
      val lhsEvaluator = animatedLhs.evaluator as DoubleEvaluator
      val rhsEvaluator = animatedRhs.evaluator as DoubleEvaluator
      Animated(
        when (integer.pred) {
          Number.NumberPred2.EQ -> IntEvaluator { (lhsEvaluator.eval() == rhsEvaluator.eval()).toInt() }
          Number.NumberPred2.CMP -> IntEvaluator { lhsEvaluator.eval().compareTo(rhsEvaluator.eval()) }
        }, parallel(animatedLhs.animation, animatedRhs.animation))
    }

    is Integer.ImageHeight -> {
      val animatedImage = animated(integer.image)
      val imageEvaluator = animatedImage.evaluator as ObjectEvaluator<TataImage?>
      Animated(
        IntEvaluator {
          val image = imageEvaluator.eval()
          if (image == null) 0 else image.size.height
        }, animatedImage.animation
      )
    }

    is Integer.ImageWidth -> {
      val animatedImage = animated(integer.image)
      val imageEvaluator = animatedImage.evaluator as ObjectEvaluator<TataImage?>
      Animated(
        IntEvaluator {
          val image = imageEvaluator.eval()
          if (image == null) 0 else image.size.width
        }, animatedImage.animation
      )
    }

    is Integer.KeyDown -> {
      val key = integer.key.tata
      Animated(
        IntEvaluator { key.isPressed.toInt() },
        infiniteAnimation
      )
    }

    is Integer.TextWidth -> {
      val text = animated(integer.text)
      val font = animated(integer.font)
      val textEvaluator = text.evaluator as ObjectEvaluator<String>
      val fontEvaluator = font.evaluator as ObjectEvaluator<TataFont>
      Animated(
        IntEvaluator { fontEvaluator.eval().width(textEvaluator.eval()) },
        parallel(text.animation, font.animation)
      )
    }

    is Integer.TextHeight -> {
      val animatedText = animated(integer.text)
      val animatedFont = animated(integer.font)
      val textEvaluator = animatedText.evaluator as ObjectEvaluator<String>
      val fontEvaluator = animatedFont.evaluator as ObjectEvaluator<TataFont>
      Animated(
        IntEvaluator { fontEvaluator.eval().height(textEvaluator.eval()) },
        parallel(animatedText.animation, animatedFont.animation)
      )
    }
  }
