package micapolos.tata8;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Game {
  static final int WIDTH = 320;
  static final int HEIGHT = 256;
  static final int SCALE = 3;

  static final List<Sprite> sprites = new ArrayList<>();
  static final Canvas compositeCanvas = new Canvas(WIDTH, HEIGHT);

  public static String title = "Game";
  public static final FinalSize size = new FinalSize(WIDTH, HEIGHT);
  public static Color backgroundColor = Color.BLACK;
  public static final Canvas backgroundCanvas = new Canvas(WIDTH, HEIGHT);
  public static final Canvas foregroundCanvas = new Canvas(WIDTH, HEIGHT);
  public static final Keys keys = new Keys();
  public static final Audio audio = Audio.create();
  public static Runnable onUpdate = () -> {};

  public static Image loadImage(Class<?> baseClass, String fileName) {
    return Image.load(baseClass, fileName);
  }

  public static Sprite newSprite() {
    Sprite sprite = new Sprite();
    sprites.add(sprite);
    return sprite;
  }

  public static void start() {
    JFrame frame = new JFrame(title);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setResizable(false);

    JPanel panel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        compositeCanvas.graphics.setBackground(backgroundColor.awtColor);
        compositeCanvas.clear();
        compositeCanvas.graphics.drawImage(backgroundCanvas.image, null, null);
        sprites.sort(Comparator.naturalOrder());
        for (Sprite sprite : sprites) {
          compositeCanvas.draw(sprite);
        }
        compositeCanvas.graphics.drawImage(foregroundCanvas.image, null, null);
        g.drawImage(compositeCanvas.image, 0, 0, Game.WIDTH * SCALE, Game.HEIGHT * SCALE, null);
      }
    };

    panel.addKeyListener(keys.listener);

    panel.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
    panel.setBackground(java.awt.Color.BLACK);
    frame.add(panel);
    frame.pack();
    frame.setVisible(true);

    panel.requestFocus();

    Timer timer = new Timer(16, _ -> {
      frame.setTitle(title);
      onUpdate.run();
      panel.repaint();
      for (Key key : keys.array) {
        key.update();
      }
    });
    timer.start();
  }

  private Game() {}
}
