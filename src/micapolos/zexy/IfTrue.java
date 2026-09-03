package micapolos.zexy;

import static micapolos.zexy.Integer.*;
import static micapolos.zexy.Number.*;
import static micapolos.zexy.Value.*;

public final class IfTrue {
  public static final class WithComponent<T extends Component> {
    final Boolean bool;
    final Value<T> trueCase;

    WithComponent(Boolean bool, Value<T> trueCase) {
      this.bool = bool;
      this.trueCase = trueCase;
    }

    public Value<T> orElse(T elseCase) {
      return orElse(value(elseCase));
    }

    public Value<T> orElse(Value<T> elseCase) {
      return bool.select(trueCase, elseCase);
    }
  }

  public static final class WithNumber {
    final Boolean bool;
    final Number trueCase;

    WithNumber(Boolean bool, Number trueCase) {
      this.bool = bool;
      this.trueCase = trueCase;
    }

    public Number orElse(double d) {
      return orElse(number(d));
    }

    public Number orElse(Number falseCase) {
      return bool.select(trueCase, falseCase);
    }
  }

  public static final class WithInteger {
    final Boolean bool;
    final Integer trueCase;

    WithInteger(Boolean bool, Integer trueCase) {
      this.bool = bool;
      this.trueCase = trueCase;
    }

    public Integer orElse(int d) {
      return orElse(integer(d));
    }

    public Integer orElse(Integer falseCase) {
      return bool.select(trueCase, falseCase);
    }
  }
}
