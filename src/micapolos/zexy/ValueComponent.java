package micapolos.zexy;

public class ValueComponent extends Component {
  public final boolean isVariable;

  ValueComponent() {
    this(false);
  }

  ValueComponent(boolean isVariable) {
    this(Clip.emptyClip, isVariable);
  }

  ValueComponent(Clip clip, boolean isVariable) {
    super(clip);
    this.isVariable = isVariable;
  }

  final void checkVariable() {
    if (!isVariable) {
      throw new IllegalArgumentException("Not a variable");
    }
  }
}
