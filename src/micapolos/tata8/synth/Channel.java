package micapolos.tata8.synth;

public final class Channel extends Line {
  public final Osc osc1 = new Osc();
  public final Osc osc2 = new Osc();
  public final Env env1 = new Env();
  public float volume;

  @Override
  public void reset() {
    osc1.reset();
    osc2.reset();
    env1.reset();
    setVolume(1.0f);
  }

  @Override
  public float step() {
    return (osc1.step() + osc2.step()) * 0.5f * env1.step() * volume;
  }

  public void setVolume(float volume) {
    this.volume = volume;
  }

  public void note(float note) {
    osc1.setNote(note + 0.05f);
    osc2.setNote(note - 0.05f);
    osc1.restart();
    osc2.restart();
    env1.restart();
  }

  public void setSustain(boolean sustain) {
    env1.setSustain(sustain);
  }
}
