package micapolos.tata8.synth;

public final class Synth extends Line {
  public final Channel[] channels = { new Channel(), new Channel(), new Channel(), new Channel() };
  public float volume;

  @Override
  public void reset() {
    for (Channel channel : channels) {
      channel.reset();
    }
    volume = 1;
  }

  @Override
  public float step() {
    float value = 0f;
    for (Channel channel : channels) {
      value += channel.step();
    }
    return value * volume * 0.7f;
  }
}
