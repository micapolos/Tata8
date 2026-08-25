package micapolos.synth;

public final class Osc extends Line {
  float t;

  public Waveform waveform;
  public float step;

  @Override
  public void reset() {
    t = 0;
    waveform = Waveform.SQUARE;
    setNote(36);
  }

  @Override
  public float step() {
    t += step;
    while (t > 1) t -= 1;
    return waveform.value(t);
  }

  public void setNote(float note) {
    double freq = 440.0 * Math.pow(2.0, (note - 57.0) / 12.0);
    step = (float) (freq / 22050);
  }

  public void restart() {
    t = 0;
  }
}
