package micapolos.tata8.model;

import static micapolos.tata8.model.Integer.integer;

public abstract class List<T extends Component> extends Component {
  final java.util.List<T> components;

  List(java.util.List<T> components) {
    super(Clip.EMPTY);
    this.components = components;
  }

  @Override
  final void addClips() {
    for (T component : components) {
      component.maybeAddClips();
    }
  }

  public final T get(int index) {
    return get(integer(index));
  }

  public abstract T get(Integer index);
}
