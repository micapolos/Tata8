package micapolos.tata8;

import java.io.InputStream;

final class Resource {
  static InputStream stream(Class<?> clazz, String fileName) {
    InputStream stream =  clazz.getResourceAsStream(fileName);
    if (stream == null) {
      throw new RuntimeException("Could not find " + clazz.getPackageName().replace('.', '/') + "/" + fileName);
    }
    return stream;
  }
  private Resource() {}
}
