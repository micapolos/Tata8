package micapolos.tata8.model;

import static micapolos.tata8.model.Integer.integer;

public abstract class List<T extends Component> extends Component {
  final java.util.List<T> components;

  List(java.util.List<T> components) {
    super(Clip.emptyClip);
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

  @Override
  public String toString() {
    return String.valueOf(components);
  }

  public abstract T get(Integer index);
}
