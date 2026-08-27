package micapolos.tata8;

public final class Color {
  public static final Color TRANSPARENT = rgba(0, 0, 0, 0);

  public static final Color WHITE = new Color(java.awt.Color.WHITE);
  public static final Color BLACK = new Color(java.awt.Color.BLACK);
  public static final Color RED = new Color(java.awt.Color.RED);
  public static final Color GREEN = new Color(java.awt.Color.GREEN);
  public static final Color BLUE = new Color(java.awt.Color.BLUE);
  public static final Color YELLOW = new Color(java.awt.Color.YELLOW);

  final java.awt.Color awtColor;

  Color(java.awt.Color awtColor) {
    this.awtColor = awtColor;
  }

  public static Color rgb(float r, float g, float b) {
    return rgba(r, g, b, 1);
  }

  public static Color rgba(float r, float g, float b, float a) {
    return new Color(new java.awt.Color(r, g, b, a));
  }
}
