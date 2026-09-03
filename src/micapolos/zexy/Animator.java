package micapolos.zexy;

public interface Animator<T extends Component> {
  Animation animate(T t);
}
