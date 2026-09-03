package micapolos.zexy;

public interface Initializer<T extends Component> {
  Animation init(T variable);
}
