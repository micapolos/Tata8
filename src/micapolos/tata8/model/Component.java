package micapolos.tata8.model;

public class Component implements Showable {
  final boolean isVariable;

  Component(boolean isVariable) {
    this.isVariable = isVariable;
  }

  final void checkVariable() {
    if (!isVariable) {
      throw new IllegalArgumentException("Not a variable");
    }
  }
}
