package micapolos.leo;

import static micapolos.leo.Integer.*;

public abstract class List<T extends Component> extends Component {
  final java.util.List<T> components;

  List(java.util.List<T> components) {
    super(Clip.emptyClip);
    this.components = components;
  }

  @Override
  final void addRunners() {
    for (T component : components) {
      component.addRunnersOnce();
    }
  }

  public final T get(int index) {
    return get(integer(index));
  }

  @Override
  public String toString() {
    return Strings.leo("list", components.toArray());
  }

  public abstract T get(Integer index);
}
