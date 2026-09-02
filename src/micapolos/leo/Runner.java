package micapolos.leo;

interface Runner {
  default void init() {}
  default void update(float seconds) {}
}
