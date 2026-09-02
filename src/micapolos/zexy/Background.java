package micapolos.zexy;

public final class Background {
  public static final Color color = Color.variable();

  static {
    Game.add(new Clip() {
      @Override
      void start() {

      }

      @Override
      float step(float seconds) {
        micapolos.tata8.Game.background.color = color.get();
        return 0;
      }
    });
  }
}
