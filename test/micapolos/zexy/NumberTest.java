package micapolos.zexy;

import org.junit.jupiter.api.Test;

import static micapolos.zexy.Number.*;
import static org.junit.jupiter.api.Assertions.*;

class NumberTest {
  @Test
  public void testDoubleNumber() {
    var number = number(123);
    assertEquals(123, number.get());
  }

  @Test
  public void testNewNumberWithVariable() {
    var numberVariable = newNumber();
    var number = number(numberVariable);
    assertEquals(0, number.get());
    numberVariable.set(123).execute();
    assertEquals(123, number.get());

  }
}