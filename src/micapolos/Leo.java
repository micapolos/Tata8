package micapolos;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Leo {
  public static String leo(String label, Object... bodies) {
    return leo(label, Arrays.stream(bodies).toList());
  }

  public static String leo(String label, List<?> bodies) {
    bodies = bodies.stream().filter(Objects::nonNull).toList();
    return label +
      switch (bodies.size()) {
        case 0 -> "";
        case 1 -> bodies.stream().map(Object::toString).collect(Collectors.joining("\n", ": ", ""));
        default ->
          bodies.stream().map(Object::toString).collect(Collectors.joining("\n", "\n", "")).replace("\n", "\n  ");
      };
  }

  private static final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
  static {
    decimalFormatSymbols.setDecimalSeparator('.');
  }
  private static final DecimalFormat decimalFormat = new DecimalFormat("0.000", decimalFormatSymbols);

  public static String leo(Object object) {
    if (object instanceof String string) {
      return "\"" + string + "\"";
    } else if (object instanceof Double d) {
        return decimalFormat.format(d);
    } else {
      return String.valueOf(object);
    }
  }

  static void main() {
    IO.println(leo("foo", leo("x", leo(0.0)), leo("y", leo(0.03))));
  }
}
