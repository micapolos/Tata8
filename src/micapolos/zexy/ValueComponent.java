package micapolos.zexy;

public class ValueComponent extends Component {
  public final boolean isVariable;

  ValueComponent() {
    this(false);
  }

  ValueComponent(boolean isVariable) {
    this(Animation.EMPTY_ANIMATION, isVariable);
  }

  ValueComponent(Animation animation, boolean isVariable) {
    super(animation);
    this.isVariable = isVariable;
  }

  final void checkVariable() {
    if (!isVariable) {
      throw new IllegalArgumentException("Not a variable");
    }
  }
}
