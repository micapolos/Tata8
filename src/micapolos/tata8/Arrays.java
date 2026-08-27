package micapolos.tata8;

import java.lang.Math;

final class Arrays {
  static <T> T get(T[] array, int index) {
    return array[Math.floorMod(index, array.length)];
  }

  static <T> T get(T[][] array, int x, int y) {
    return get(get(array, x), y);
  }

  static <T> void set(T[] array, int index, T value) {
    array[Math.floorMod(index, array.length)] = value;
  }

  static <T> void set(T[][] array, int x, int y, T value) {
    set(get(array, x), y, value);
  }
}
