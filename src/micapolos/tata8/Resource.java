package micapolos.tata8;

import java.io.InputStream;

final class Resource {
  static InputStream stream(Class<?> baseClass, String fileName) {
    InputStream stream =  baseClass.getResourceAsStream(fileName);
    if (stream == null) {
      throw new RuntimeException("Could not find " + baseClass.getPackageName().replace('.', '/') + "/" + fileName);
    }
    return stream;
  }
}
