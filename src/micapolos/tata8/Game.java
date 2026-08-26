package micapolos.tata8;

import micapolos.synth.Synth;

import javax.swing.*;
import java.awt.*;

public final class Game {
  static final int WIDTH = 320;
  static final int HEIGHT = 256;
  static final int SCALE = 3;

  public static String title = "Game";
  public static final FinalSize size;
  public static Color backgroundColor = Color.BLACK;
  public static final Canvas backgroundCanvas;
  public static final Canvas foregroundCanvas;
  public static final Keys keys = new Keys();
  public static final Sprite[] sprites = new Sprite[256];
  public static final Image[] images = new Image[256];
  public static final Audio audio;
  public static Updater updater = Updater.EMPTY;

  static final Canvas compositeCanvas;
  static final JFrame frame;
  static final Timer timer;

  static {
    size = new FinalSize(WIDTH, HEIGHT);

    backgroundCanvas = new Canvas(WIDTH, HEIGHT);
    foregroundCanvas = new Canvas(WIDTH, HEIGHT);
    compositeCanvas = new Canvas(WIDTH, HEIGHT);

    for (int i = 0; i < images.length; i++) {
      images[i] = new Image();
    }

    for (int i = 0; i < sprites.length; i++) {
      sprites[i] = new Sprite();
    }

    Synth synth = new Synth();
    synth.reset();
    audio = new Audio(synth);

    frame = new JFrame(title);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setResizable(false);

    JPanel panel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        compositeCanvas.graphics.setBackground(backgroundColor.awtColor);
        compositeCanvas.clear();
        compositeCanvas.graphics.drawImage(backgroundCanvas.image, null, null);
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

    timer = new Timer(16, _ -> {
      frame.setTitle(title);
      updater.update();
      panel.repaint();
      for (Key key : keys.array) {
        key.update();
      }
    });
    timer.start();
  }

  private Game() {}
}
