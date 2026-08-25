package micapolos;

import micapolos.synth.Synth;

import javax.swing.*;
import java.awt.*;

public final class Game {
  static final int WIDTH = 320;
  static final int HEIGHT = 256;
  static final int SCALE = 3;

  public String title = "Game";
  public final Size size = new Size();
  public Color backgroundColor = Color.BLACK;
  public final Canvas backgroundCanvas;
  public final Canvas foregroundCanvas;
  public final Keys keys = new Keys();
  public final Sprite[] sprites = new Sprite[256];
  public final Image[] images = new Image[256];
  public final Audio audio;
  public Updater updater = Updater.EMPTY;

  final Canvas compositeCanvas;
  final JFrame frame;
  final Timer timer;

  public Game() {
    size.set(WIDTH, HEIGHT);

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
      size.set(WIDTH, HEIGHT);
      frame.setTitle(title);
      updater.update();
      panel.repaint();
      for (Key key : keys.array) {
        key.update();
      }
    });
    timer.start();
  }

  public void exit() {
    frame.setVisible(false);
    frame.dispose();
    timer.stop();
    audio.stop();
    // Remove this once every resource is closed properly.
    System.exit(0);
  }
}
