package micapolos.tata8.model;

public class ValueComponent extends Component {
  public final boolean isVariable;

  ValueComponent(boolean isVariable) {
    this.isVariable = isVariable;
  }

  final void checkVariable() {
    if (!isVariable) {
      throw new IllegalArgumentException("Not a variable");
    }
  }
}
