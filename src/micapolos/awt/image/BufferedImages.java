package micapolos.awt.image;

import java.awt.image.BufferedImage;

public class BufferedImages {
  public static String getTypeName(int type) {
    return switch (type) {
      case BufferedImage.TYPE_CUSTOM -> "custom";
      case BufferedImage.TYPE_INT_RGB -> "int rgb";
      case BufferedImage.TYPE_INT_ARGB -> "int argb";
      case BufferedImage.TYPE_INT_ARGB_PRE -> "int argb premultiplied";
      case BufferedImage.TYPE_INT_BGR -> "int bgr";
      case BufferedImage.TYPE_3BYTE_BGR -> "3-byte bgr";
      case BufferedImage.TYPE_4BYTE_ABGR -> "4-byte abgr";
      case BufferedImage.TYPE_4BYTE_ABGR_PRE -> "4-byte abgr premultipled";
      case BufferedImage.TYPE_USHORT_565_RGB -> "unsigned short 565 rgb";
      case BufferedImage.TYPE_USHORT_555_RGB -> "unsigned short 555 rgb";
      case BufferedImage.TYPE_BYTE_GRAY -> "byte gray";
      case BufferedImage.TYPE_USHORT_GRAY -> "unsigned short gray";
      case BufferedImage.TYPE_BYTE_BINARY -> "byt binary";
      case BufferedImage.TYPE_BYTE_INDEXED -> "byte indexed";
      default -> "unknown";
    };
  }
}
