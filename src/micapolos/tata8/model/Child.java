package micapolos.tata8.model;

public class Child extends Component {
  final Component[] components;

  Child(Component... components) {
    this.components = components;
  }

  @Override
  final void start() {
    for (Component component : components) {
      component.start();
    }
  }

  @Override
  final float advance(float seconds) {
    float overflow = Float.POSITIVE_INFINITY;
    for (Component component : components) {
      overflow = Math.min(overflow, component.advance(seconds));
    }
    return overflow;
  }
}
