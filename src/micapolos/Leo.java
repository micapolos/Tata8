package micapolos;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class Leo {
  public static String leo(String label, Object... bodies) {
    return label +
        switch (bodies.length) {
          case 0 -> "";
          case 1 -> Arrays.stream(bodies).map(Object::toString).collect(Collectors.joining("\n", ": ", ""));
          default -> Arrays.stream(bodies).map(Object::toString).collect(Collectors.joining("\n", "\n", "")).replace("\n", "\n  ");
        };
  }

  static void main() {
    IO.println(leo("foo", leo("x", 0), leo("y", 0)) + "!!!");
  }
}
