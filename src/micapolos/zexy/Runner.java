package micapolos.zexy;

interface Runner {
  default void init() {}
  default void update(float seconds) {}
}
