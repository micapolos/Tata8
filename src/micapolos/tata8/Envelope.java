package micapolos.tata8;

import micapolos.synth.Env;

public final class Envelope {
  public volatile float attack = 0f;
  public volatile float decay = 0f;
  public volatile float sustain = 1f;
  public volatile float release = 1f;

  final Env synthEnv;

  Envelope(Env synthEnv) {
    this.synthEnv = synthEnv;
  }

  void sync() {
    synthEnv.setAttack(attack);
    synthEnv.setDecay(decay);
    synthEnv.setRelease(release);
    synthEnv.sustainValue = Math.clamp(sustain, 0f, 1f);
  }
}
