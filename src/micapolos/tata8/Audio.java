package micapolos;

import micapolos.synth.Synth;

public final class Audio {
  final Synth synth;
  final Runnable closeRunnable;

  public float volume = 1f;
  public final Channel[] channels = new Channel[4];

  Audio(Synth synth) {
    this.synth = synth;
    for (int i = 0; i < channels.length; i++) {
      channels[i] = newChannel(synth.channels[i]);
    }
    closeRunnable = synth.listen(this::sync);
  }

  static Channel newChannel(micapolos.synth.Channel synthChannel) {
    return new Channel(synthChannel);
  }

  void sync() {
    synth.volume = volume;
    for (Channel channel : channels) {
      channel.sync();
    }
  }

  void stop() {
    closeRunnable.run();
  }
}
