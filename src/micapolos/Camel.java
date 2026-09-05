package micapolos;

public class Camel {
  public static String camelToSpaced(String input) {
    return input
      // Insert space before capital letters preceded by lowercase letters or digits
      .replaceAll("(?<=[a-z0-9])(?=[A-Z])", " ")
      // Insert space between consecutive capitals and subsequent lowercase (e.g., "AWTFilter" -> "AWT Filter")
      .replaceAll("(?<=[A-Z])(?=[A-Z][a-z])", " ")
      .toLowerCase();
  }
}
