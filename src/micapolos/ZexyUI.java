package micapolos;

import micapolos.tata8.Color;
import micapolos.tata8.Game;
import micapolos.tata8.Shader;

public class ZexyUI {
  static final class Colors {
    static final Color darkBackground = Color.rgb(29f / 255, 34f / 255, 39f / 255);
    static final Color background = Color.rgb(48f / 255, 56f / 255, 65f / 255);
    static final Color panelHighlight = Color.rgb(70f / 255, 74f / 255, 78f / 255);
    static final Color lineHighlight = Color.rgb(77f / 255, 88f / 255, 100f / 255);
    static final Color white = Color.rgb(214f / 255, 215f / 255, 217f / 255);
    static final Color lineNumber = Color.rgb(132f / 255, 139f / 255, 149f / 255);
    static final Color gray = Color.rgb(152f / 255, 156f / 255, 160f / 255);
    static final Color keyword = Color.rgb(102f / 255, 153f / 255, 204f / 255);
    static final Color number = Color.rgb(249f / 255, 174f / 255, 88f / 255);
    static final Color colon = Color.rgb(166f / 255, 172f / 255, 185f / 255);
    static final Color quote = Color.rgb(95f / 255, 180f / 255, 180f / 255);
    static final Color string = Color.rgb(153f / 255, 199f / 255, 148f / 255);
    static final Color cursor = Color.rgb(249f / 255, 174f / 255, 88f / 255);
  }

  static final String[] fileStrings = {
    "bucket.leo",
    "chicken.leo",
    "girl.leo",
    "house.leo",
    "scene.leo",
    "tree.leo",
  };

  static void main() {
    Game.title = "Zexy";
    int sidePanelWidth = 80;
    Game.screen.shader = Shader.LIGHT_POINT;
    Game.background.color = Colors.darkBackground;
    Game.background.canvas.fillRect(sidePanelWidth, 0, Game.WIDTH - sidePanelWidth, 256, Colors.background);

    {
      int y = 33;
      Game.background.canvas.fillRect(0, y, sidePanelWidth, 8, Colors.panelHighlight);
    }

    {
      int y = 121;
      Game.background.canvas.fillRect(sidePanelWidth, y, Game.WIDTH - sidePanelWidth, 8, Colors.lineHighlight);
    }

    {
      Game.background.canvas.color = Colors.gray;
      int x = 1;
      int y = 1;
      for (String fileString : fileStrings) {
        Game.background.canvas.draw(fileString, x, y);
        y += 8;
      }
    }

    {
      Game.background.canvas.color = Colors.lineNumber;
      for (int i = 0; i < 32; i++) {
        String s = String.valueOf(i + 1);
        int width = Game.background.canvas.font.width(s);
        Game.background.canvas.draw(s, sidePanelWidth + 13 - width, i * 8 + 1);
      }
    }

    {
      textLeft = sidePanelWidth + 1 + 18;
      textX = textLeft;
      textY = 1;
      drawKeyword("stack"); drawColon(); drawNewline();
      drawKeyword("  label"); drawColon(); drawNewline();
      drawKeyword("    text"); drawColon(); draw(" "); drawLiteral("Hello, world!"); drawNewline();
      drawKeyword("    position"); drawColon(); drawNewline();
      drawKeyword("      x"); drawColon(); draw(" "); drawLiteral(10); drawNewline();
      drawKeyword("      y"); drawColon(); draw(" "); drawLiteral(20); drawNewline();
      drawKeyword("  point"); drawColon(); drawNewline();
      drawKeyword("    position"); drawColon(); drawNewline();
      drawKeyword("      x"); drawColon(); draw(" "); drawLiteral(30); drawNewline();
      drawKeyword("      y"); drawColon(); draw(" "); drawLiteral(50); drawNewline();
      drawKeyword("  filled rectangle"); drawColon(); drawNewline();
      drawKeyword("    position"); drawColon(); drawNewline();
      drawKeyword("      x"); drawColon(); draw(" "); drawLiteral(40); drawNewline();
      drawKeyword("      y"); drawColon(); draw(" "); drawLiteral(20); drawNewline();
      drawKeyword("    size"); drawColon(); drawNewline();
      drawKeyword("      width"); drawColon(); draw(" "); drawLiteral(50); drawNewline();
      drawKeyword("      height"); drawColon(); draw(" "); drawLiteral(100); drawNewline();
    }

    {
      int y = 121;
      Game.background.canvas.fillRect(sidePanelWidth + 73, y, 1, 8, Colors.cursor);
    }

    Game.start();
  }

  static int textLeft = 0;
  static int textX = 0;
  static int textY = 0;
  static int lineCharCount = 0;

  static void draw(char ch) {
    if (ch == '\n') {
      textX = textLeft;
      textY += 8;
      lineCharCount = 0;
    } else {
      String s = String.valueOf(ch);
      if (lineCharCount != 0) {
        textX += Game.font.glyphSpacing;
      }
      Game.background.canvas.draw(s, textX, textY);
      textX += Game.font.width(s);
      lineCharCount++;
    }
  }

  static void draw(String s) {
    for (char ch : s.toCharArray()) {
      draw(ch);
    }
  }

  static void drawLiteral(int i) {
    Color previousColor = Game.background.canvas.color;
    Game.background.canvas.color = Colors.number;
    draw(String.valueOf(i));
    Game.background.canvas.color = previousColor;
  }

  static void drawLiteral(String s) {
    Color previousColor = Game.background.canvas.color;
    Game.background.canvas.color = Colors.quote;
    draw("\"");
    Game.background.canvas.color = Colors.string;
    draw(s);
    Game.background.canvas.color = Colors.quote;
    draw("\"");
    Game.background.canvas.color = previousColor;
  }

  static void drawKeyword(String s) {
    Color previousColor = Game.background.canvas.color;
    Game.background.canvas.color = Colors.keyword;
    draw(s);
    Game.background.canvas.color = previousColor;
  }

  static void drawQuote() {
    Color previousColor = Game.background.canvas.color;
    Game.background.canvas.color = Colors.quote;
    draw("\"");
    Game.background.canvas.color = previousColor;
  }

  static void drawColon() {
    Color previousColor = Game.background.canvas.color;
    Game.background.canvas.color = Colors.colon;
    draw(":");
    Game.background.canvas.color = previousColor;
  }

  static void drawNewline() {
    draw("\n");
  }
}
