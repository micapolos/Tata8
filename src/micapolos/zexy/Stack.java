package micapolos.zexy;

import micapolos.tata8.Canvas;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public class Stack extends Drawing {
  final java.util.List<Drawing> drawings;

  Stack(Animation animation, List<Drawing> drawings) {
    super(animation);
    this.drawings = drawings;
  }

  @Override
  void addRunners() {
    for (Drawing drawing : drawings) {
      drawing.addRunnersOnce();
    }
  }

  @Override
  void drawOn(Canvas canvas) {
    for (Drawing drawing : drawings) {
      drawing.drawOn(canvas);
    }
  }

  public static Stack stackOf(Drawing... drawings) {
    return new Stack(null, Arrays.stream(drawings).toList());
  }

  public static Stack stack(int size, IntFunction<Drawing> function) {
    return new Stack(null, IntStream.range(0, size).mapToObj(function).toList());
  }
}
