package micapolos.tata8.model;

public abstract class List<T extends Component> extends Component {
  final java.util.List<T> components;

  List(java.util.List<T> components) {
    this.components = components;
  }

  @Override
  final void addClips() {
    for (T component : components) {
      component.maybeAddClips();
    }
  }

  public abstract T get(Integer index);
}
