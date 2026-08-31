package micapolos.tata8.model;

public final class Background {
  public static final Color color = Color.variable();

  static {
    Game.add(new Clip() {
      @Override
      void start() {

      }

      @Override
      float advance(float seconds) {
        micapolos.tata8.Game.background.color = color.get();
        return 0;
      }
    });
  }
}
