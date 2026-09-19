package micapolos.leo

import micapolos.zexy.model.*
import micapolos.zexy.model.Number

fun interface Processor<T> {
  fun process(value: T)
}

//val eof get(): Nothing = error("Unexpected end of token")
//infix fun <T> Any?.not(type: Type): T = error("$this is not $type")
//
//enum class Type {
//  INTEGER, NUMBER, TEXT, IMAGE, FONT, DRAWING
//}
//
//fun Iterator<Token>.nextValue(type: Type): Value =
//  when (val token = next()) {
//    is Token.WithString -> when (type) {
//      Type.INTEGER -> Integer.Constant(token.string.toInt())
//      Type.NUMBER -> Number.Constant(token.string.toDouble())
//      Type.TEXT -> Text.Constant(token.string)
//      else -> token.string not type
//    }
//
//    is Token.WithBegin -> when (token.begin.string) {
//      "filled rectangle" -> when (type) {
//        Type.DRAWING -> nextRect()
//        else -> token.begin.string not type
//      }
//
//      else -> error("Unknown token: ${token.begin.string}")
//    }
//
//    is Token.WithEnd -> eof
//  }
//
//fun Iterator<Token>.nextEnd(): Unit =
//  when (val token = next()) {
//    is Token.WithEnd -> Unit
//    else -> error("End expected")
//  }
//
//tailrec fun Iterator<Token>.nextPosition(x: Value? = null, y: Value? = null): List<Value> =
//  when (val token = next()) {
//    is Token.WithBegin -> when (token.begin.string) {
//      "x" -> if (x != null) {
//        error("already has: x")
//      } else {
//        nextPosition(nextValue(Type.INTEGER).also { nextEnd() }, y)
//      }
//
//      "y" -> if (y != null) {
//        error("already has: y")
//      } else {
//        nextPosition(x, nextValue(Type.INTEGER).also { nextEnd() })
//      }
//
//      else -> error("expected one of: x, y")
//    }
//
//    is Token.WithEnd -> when {
//      x == null -> error("missing: x")
//      y == null -> error("missing: x")
//      else -> listOf(x, y)
//    }
//
//    else -> error("Unexpected token: $token")
//  }
//
//tailrec fun Iterator<Token>.nextSize(width: Value? = null, height: Value? = null): List<Value> =
//  when (val token = next()) {
//    is Token.WithBegin -> when (token.begin.string) {
//      "width" -> if (width != null) {
//        error("already has: width")
//      } else {
//        nextSize(nextValue(Type.INTEGER).also { nextEnd() }, height)
//      }
//
//      "height" -> if (height != null) {
//        error("already has: height")
//      } else {
//        nextSize(width, nextValue(Type.INTEGER).also { nextEnd() })
//      }
//
//      else -> error("expected one of: width, height")
//    }
//
//    is Token.WithEnd -> when {
//      width == null -> error("missing: width")
//      height == null -> error("missing: height")
//      else -> listOf(width, height)
//    }
//
//    else -> error("Unexpected token: $token")
//  }
//
//tailrec fun Iterator<Token>.nextRect(position: List<Value>? = null, size: List<Value>? = null): Drawing.Rect =
//  when (val token = next()) {
//    is Token.WithBegin -> when (token.begin.string) {
//      "position" ->
//        if (position != null) {
//          error("already has: position")
//        } else {
//          nextRect(nextPosition().also { nextEnd() }, size)
//        }
//
//      "size" ->
//        if (size != null) {
//          error("already has: size")
//        } else {
//          nextRect(position, nextSize().also { nextEnd() })
//        }
//
//      else -> error("expected: one of: position, size")
//    }
//
//    is Token.WithEnd -> when {
//      position == null -> error("missing: position")
//      size == null -> error("missing: size")
//      else -> Drawing.Rect(position[0], position[1], size[0], size[1])
//    }
//
//    else -> error("Unexpected token: $token")
//  }
//
////fun main() {
////  listOf(
////    Token.WithBegin(Begin("x")),
////    Token.WithInt(10),
////    Token.WithEnd(End),
////    Token.WithBegin(Begin("y")),
////    Token.WithInt(20),
////    Token.WithEnd(End),
////    Token.WithEnd(End)
////  )
////    .iterator()
////    .nextPosition()
////    .also { println(it) }
////
////  listOf(
////    Token.WithBegin(Begin("width")),
////    Token.WithInt(10),
////    Token.WithEnd(End),
////    Token.WithBegin(Begin("height")),
////    Token.WithInt(20),
////    Token.WithEnd(End),
////    Token.WithEnd(End)
////  )
////    .iterator()
////    .nextSize()
////    .also { println(it) }
////
////  listOf(
////    Token.WithBegin(Begin("filled rectangle")),
////    Token.WithBegin(Begin("size")),
////    Token.WithBegin(Begin("width")),
////    Token.WithInt(10),
////    Token.WithEnd(End),
////    Token.WithBegin(Begin("height")),
////    Token.WithInt(20),
////    Token.WithEnd(End),
////    Token.WithEnd(End),
////    Token.WithBegin(Begin("position")),
////    Token.WithBegin(Begin("x")),
////    Token.WithInt(10),
////    Token.WithEnd(End),
////    Token.WithBegin(Begin("y")),
////    Token.WithInt(20),
////    Token.WithEnd(End),
////    Token.WithEnd(End),
////    Token.WithEnd(End))
////    .iterator()
////    .nextValue(Type.DRAWING)
////    .also { println(it) }
////}
