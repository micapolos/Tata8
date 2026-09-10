package micapolos;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Leo {
  public static String leo(String label, Object... bodies) {
    return leo(label, Arrays.stream(bodies).toList());
  }

  public static String leo(String label, List<?> bodies) {
    return label +
      switch (bodies.size()) {
        case 0 -> "";
        case 1 -> bodies.stream().map(Object::toString).collect(Collectors.joining("\n", ": ", ""));
        default -> bodies.stream().map(Object::toString).collect(Collectors.joining("\n", "\n", "")).replace("\n", "\n  ");
      };
  }

  static void main() {
    IO.println(leo("foo", leo("x", 0), leo("y", 0)) + "!!!");
  }
}
