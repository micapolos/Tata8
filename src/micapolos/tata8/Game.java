package micapolos.tata8;

import micapolos.FloatConsumer;
import micapolos.awt.DuskFilter;
import micapolos.zexy.Animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static micapolos.zexy.Image.*;

public final class Game {
  static final boolean FULLSCREEN = false;

  public static final int WIDTH = 512 - 32;
  public static final int HEIGHT = 256;
  public static final int MAX_SPRITE_COUNT = 256;
  public static final int MAX_IMAGES_PIXEL_COUNT = 128 * 1024 * 1024;
  static final int SCALE = 3;

  static final List<Sprite> sprites = new ArrayList<>();
  static final Canvas compositeCanvas = new Canvas(WIDTH, HEIGHT);
  static final Canvas shaderCanvas = new Canvas(WIDTH * 3, HEIGHT * 3);
  static int loadedImagePixelCount;
  static final ArrayList<String> logStrings = new ArrayList<>();

  public static String title = "Game";
  public static final FinalSize size = new FinalSize(WIDTH, HEIGHT);
  public static Background background = new Background(new Canvas(WIDTH, HEIGHT), TileMap.create(64, 32, 16, 16));
  public static Foreground foreground = new Foreground(new Canvas(WIDTH, HEIGHT), TileMap.create(64, 32, 16, 16));
  public static final Camera camera = new Camera();
  public static final Keys keys = new Keys();
  public static final Audio audio = Audio.create();
  public static final Mouse mouse = new Mouse();
  public static Runnable onUpdate = () -> {};
  public static FloatConsumer onStep = seconds -> onUpdate.run();
  public static final Screen screen = new Screen();
  public static Animation animation = Animation.instant();
  public static double dusk = 0;
  public static double targetDusk = 0;

  @Deprecated(forRemoval = true)
  public static final Canvas backgroundCanvas = background.canvas;
  @Deprecated(forRemoval = true)
  public static final Canvas foregroundCanvas = foreground.canvas;
  @Deprecated(forRemoval = true)
  public static final TileMap backgroundTileMap = background.tileMap;
  @Deprecated(forRemoval = true)
  public static final TileMap foregroundTileMap = foreground.tileMap;

  private Game() {
  }

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
    frame.setLayout(new java.awt.GridLayout(1, 1));
    frame.setBackground(java.awt.Color.RED);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice device = ge.getDefaultScreenDevice();

    var fullScreen = FULLSCREEN && device.isFullScreenSupported();

    frame.setResizable(false);

    JPanel panel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        compositeCanvas.graphics.setBackground(background.color.awtColor);
        compositeCanvas.clear();
        compositeCanvas.graphics.drawImage(background.canvas.image, null, null);
        background.tileMap.drawOn(compositeCanvas, -camera.position.x, -camera.position.y);
        sprites.sort(Comparator.naturalOrder());
        for (Sprite sprite : sprites) {
          compositeCanvas.draw(sprite, -camera.position.x, -camera.position.y);
        }
        foreground.tileMap.drawOn(compositeCanvas, -camera.position.x, -camera.position.y);
        compositeCanvas.graphics.drawImage(foreground.canvas.image, null, null);
        {
          int textY = 0;
          for (String string : logStrings) {
            compositeCanvas.draw(string, 1, textY, Color.YELLOW, Font.system, true);
            textY += 8;
          }
        }
        DuskFilter.applyDuskFilter(compositeCanvas.image, dusk);
        logStrings.clear();
        Graphics2D g2d = (Graphics2D) g;
        BufferedImageOp imageOp = screen.imageOp();

        int containerWidth = getWidth();
        int containerHeight = getHeight();

        int imageWidth = compositeCanvas.image.getWidth() * (imageOp == null ? 1 : 3);
        int imageHeight = compositeCanvas.image.getHeight() * (imageOp == null ? 1 : 3);

        double scale = java.lang.Math.min((double) containerWidth / imageWidth, (double) containerHeight / imageHeight);

        int scaledWidth = (int) java.lang.Math.round(imageWidth * scale);
        int scaledHeight = (int) java.lang.Math.round(imageHeight * scale);

        int x = (containerWidth - scaledWidth) / 2;
        int y = (containerHeight - scaledHeight) / 2;

        if (imageOp == null) {
          g2d.drawImage(compositeCanvas.image, x, y, scaledWidth, scaledHeight, null);
        } else {
          shaderCanvas.clear();
          shaderCanvas.graphics.drawImage(compositeCanvas.image, imageOp, 0, 0);
          x = (getWidth() - imageWidth) / 2;
          y = (getHeight() - imageHeight) / 2;
          g2d.drawImage(shaderCanvas.image, x, y, imageWidth, imageHeight, null);
        }
      }
    };

    panel.addKeyListener(keys.listener);

    if (!fullScreen) {
      panel.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
    }
    panel.setBackground(java.awt.Color.BLACK);
    frame.add(panel);

    if (fullScreen) {
      device.setFullScreenWindow(frame);
    } else {
      frame.pack();
      frame.setVisible(true);
    }

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

    animation.startInternal();

    Timer timer = new Timer(16, _ -> {
      frame.setTitle(title);
      float multiplier = keys.slow.isPressed
        ? keys.fast.isPressed ? 1f : 0.25f
        : keys.fast.isPressed ? 4f : 1f;
      if (keys.shader.pressed()) {
        screen.shader = Shader.nextOf(screen.shader);
      }
      dusk = Math.elastic((float) dusk, (float) targetDusk, 0.01f);
      if (keys.dusk.pressed()) {
        targetDusk = 1 - targetDusk;
      }
      animation.advanceInternal(multiplier / 60f);
      onStep.accept(multiplier / 60f);
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
