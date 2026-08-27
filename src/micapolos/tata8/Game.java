package micapolos.tata8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
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
  public static final FloatVector camera = new FloatVector();
  public static final Keys keys = new Keys();
  public static final Audio audio = Audio.create();
  public static final TileMap backgroundTileMap = TileMap.create(64, 32, 16, 16);
  public static final TileMap foregroundTileMap = TileMap.create(64, 32, 16, 16);
  public static Runnable onUpdate = () -> {};
  public static final Mouse mouse = new Mouse();
  static final ArrayList<String> logStrings = new ArrayList<>();

  private Game() {}

  public static Image loadImage(Class<?> baseClass, String fileName) {
    Image image = Image.load(baseClass, fileName);
    int newLoadedImagePixelCount = loadedImagePixelCount + image.size.width * image.size.height;
    if (newLoadedImagePixelCount > MAX_IMAGES_PIXEL_COUNT) {
      throw new RuntimeException("Could not load image (maximum total pixel count is " + MAX_IMAGES_PIXEL_COUNT + ")");
    }
    return image;
  }

  public static TileSet loadTileSet(Class<?> baseClass, String fileName) {
    Image image = loadImage(baseClass, fileName);
    return TileSet.slice(image);
  }

  public static Image newImage(int width, int height) {
    return new Image(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
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
        backgroundTileMap.drawOn(compositeCanvas, camera.x, camera.y);
        sprites.sort(Comparator.naturalOrder());
        for (Sprite sprite : sprites) {
          compositeCanvas.draw(sprite, camera.x, camera.y);
        }
        foregroundTileMap.drawOn(compositeCanvas, camera.x, camera.y);
        compositeCanvas.graphics.drawImage(foregroundCanvas.image, null, null);
        int y = 0;
        for (String string : logStrings) {
          compositeCanvas.draw(string, 1, y, Color.YELLOW, Font.system, true);
          y += 8;
        }
        logStrings.clear();
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

  public static void log(String label, Object object) {
    log(String.format("%s: %s", label, object));
  }

  public static void log(Object object) {
    logStrings.add(String.valueOf(object));
  }

  public static String info() {
    return String.format(
        "game(sprites: %d / %d, imagePixels: %d / %d)",
        spriteCount(), MAX_SPRITE_COUNT,
        loadedImagePixelCount, MAX_IMAGES_PIXEL_COUNT);
  }

  static void main() {
    onUpdate = () -> {
      log(info());
      log(mouse);
      log(keys);
    };
    start();
  }
}
