package micapolos.tata8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class Game {
  static final int WIDTH = 320;
  static final int HEIGHT = 256;
  public static final int MAX_SPRITE_COUNT = 256;
  public static final int MAX_IMAGES_PIXEL_COUNT = 1024 * 1024;
  static final int SCALE = 3;

  static final List<Sprite> sprites = new ArrayList<>();
  static final Canvas compositeCanvas = new Canvas(WIDTH, HEIGHT);
  static int loadedImagePixelCount;

  public static String title = "Game";
  public static final FinalSize size = new FinalSize(WIDTH, HEIGHT);
  public static Color backgroundColor = Color.BLACK;
  public static final Canvas backgroundCanvas = new Canvas(WIDTH, HEIGHT);
  public static final Canvas foregroundCanvas = new Canvas(WIDTH, HEIGHT);
  public static final Keys keys = new Keys();
  public static final Audio audio = Audio.create();
  public static Runnable onUpdate = () -> {};
  public static final Mouse mouse = new Mouse();

  public static Image loadImage(Class<?> baseClass, String fileName) {
    Image image = Image.load(baseClass, fileName);
    int newLoadedImagePixelCount = loadedImagePixelCount + image.size.width * image.size.height;
    if (newLoadedImagePixelCount > MAX_IMAGES_PIXEL_COUNT) {
      throw new RuntimeException("Could not load image (maximum total pixel count is " + MAX_IMAGES_PIXEL_COUNT + ")");
    }
    return image;
  }

  public static int spriteCount() {
    return sprites.size();
  }

  public static Sprite newSprite() {
    if (sprites.size() == MAX_SPRITE_COUNT) {
      throw new RuntimeException("Could not create new sprite (max is " + MAX_SPRITE_COUNT + ")");
    }
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

    panel.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        mouse.button.press();
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        mouse.button.release();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        mouse.isOutside = false;
      }

      @Override
      public void mouseExited(MouseEvent e) {
        mouse.isOutside = true;
      }
    });

    panel.addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        mouse.position.set(e.getX() / SCALE, e.getY() / SCALE);
      }

      @Override
      public void mouseDragged(MouseEvent e) {
        mouse.position.set(e.getX() / SCALE, e.getY() / SCALE);
      }
    });

    Timer timer = new Timer(16, _ -> {
      frame.setTitle(title);
      onUpdate.run();
      panel.repaint();
      for (Key key : keys.array) {
        key.update();
      }
      mouse.update();
    });
    timer.start();
  }

  private Game() {}
}
