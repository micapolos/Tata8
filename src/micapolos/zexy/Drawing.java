package micapolos.zexy;

import micapolos.tata8.Canvas;

public abstract class Drawing extends Component {
  abstract void drawOn(Canvas canvas);

  Drawing(Animation animation) {
    super(animation);
  }

  public Drawing visibleWhen(Boolean condition) {
    return new Drawing(animation) {
      @Override
      void drawOn(Canvas canvas) {
        if (condition.get()) {
          Drawing.this.drawOn(canvas);
        }
      }

      @Override
      void addRunners() {
        Drawing.this.addRunnersOnce();
        condition.addRunnersOnce();
      }

      @Override
      public Drawing with(Animation animation) {
        return this;
      }
    };
  }

  Drawable drawable() {
    return new Drawable() {
      @Override
      public void drawOn(Canvas canvas) {
        Drawing.this.drawOn(canvas);
      }
    };
  }

  public abstract Drawing with(Animation animation);

  @Override
  public void show() {
    addRunnersOnce();
    drawable().show();
  }
}
