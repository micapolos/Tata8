package micapolos.zexy2.live

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ExecutorTest {
  @Test
  fun testConditional() {
    val condition = Live.Variable(Live.Constant(Boolean::class, true))
    val trueConstant = Live.Constant(Integer::class, 10)
    val falseConstant = Live.Bottom
    val conditional =
      Live.Conditional(
        Integer::class,
        condition,
        trueConstant,
        falseConstant)
    val executor = Executor()
    val conditionalState = executor.state(conditional)

    // Dependent states should be created
    val conditionState = executor.states[condition]!!
    val trueState = executor.states[trueConstant]!!
    val falseState = executor.states[falseConstant]!!

    assertNull(conditionState.value)
    assertNull(conditionalState.value)
    assertNull(trueState.value)
    assertNull(falseState.value)

    val runner = executor.runner
    runner.init()
    assertEquals(true, conditionState.value)
    assertEquals(10, trueState.value)
    assertNull(falseState.value)
    assertNull(conditionalState.value)

    runner.step(1f)
    assertEquals(true, conditionState.value)
    assertEquals(10, conditionalState.value)

    conditionState.value = false
    try {
      runner.step(1f)
      throw AssertionError("Should throw")
    } catch (e: IllegalStateException) {
      // OK
    }
  }

}