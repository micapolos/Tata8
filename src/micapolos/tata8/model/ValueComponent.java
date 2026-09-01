package micapolos.tata8.model;

public class ValueComponent extends Component {
  public final boolean isVariable;

  ValueComponent() {
    this(false);
  }

  ValueComponent(boolean isVariable) {
    this(Clip.EMPTY, isVariable);
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
