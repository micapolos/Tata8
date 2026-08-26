package micapolos.tata8;

import micapolos.synth.Synth;

public final class Audio {
  final Synth synth;

  public float volume = 1f;
  public final Channel[] channels;

  Audio(Synth synth, Channel[] channels) {
    this.synth = synth;
    this.channels = channels;
  }

  static Audio create() {
    Synth synth = new Synth();
    synth.reset();

    Channel[] channels = new Channel[4];
    for (int i = 0; i < channels.length; i++) {
      channels[i] = newChannel(synth.channels[i]);
    }

    return new Audio(synth, channels);
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

  void start() {
    synth.listen(this::sync);
  }
}
