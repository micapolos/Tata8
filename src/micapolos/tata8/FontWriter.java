package micapolos.tata8;

import java.io.File;
import java.io.IOException;

public class FontWriter {
  static void main() throws IOException {
    Font.mica.writeTo(new File("res/micapolos/tata8/mica-font.bin"));
    Font.kora.writeTo(new File("res/micapolos/tata8/kora-font.bin"));
  }
}
