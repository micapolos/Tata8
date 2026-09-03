package micapolos.zexy;

import micapolos.tata8.Canvas;

public abstract class Drawing extends Component {
  abstract void drawOn(Canvas canvas);

  Drawing(Animation animation) {
    super(animation);
  }
}
