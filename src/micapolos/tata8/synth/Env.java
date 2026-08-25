package micapolos.synth;

public final class Env extends Line {
  float value;

  public boolean isAttack;
  public boolean isSustain;
  public float attackStep;
  public float decayStep;
  public float sustainValue;
  public float releaseCoefficient;

  @Override
  public void reset() {
    isAttack = false;
    isSustain = false;
    setAttack(0.1f);
    setDecay(0.1f);
    setRelease(1.0f);
    sustainValue = 0.5f;
    value = 0;
  }

  @Override
  public float step() {
    if (isAttack) {
      value += attackStep;
      if (value >= 1f) {
        value = 1f;
        isAttack = false;
      }
    } else if (isSustain) {
      if (value > sustainValue) {
        value -= decayStep;
        if (value <= sustainValue) {
          value = sustainValue;
        }
      }
    } else {
      value *= releaseCoefficient;
    }
    return value;
  }

  public void restart() {
    isAttack = true;
  }

  public void setAttack(float seconds) {
    float samples = Math.clamp(seconds, 0.001f, 10.0f) * 22050;
    attackStep = 1f / samples;
  }

  public void setDecay(float seconds) {
    float samples = Math.clamp(seconds, 0.001f, 10.0f) * 22050;
    decayStep = 1f / samples;
  }

  public void setRelease(float seconds) {
    float samples = Math.clamp(seconds, 0.001f, 10.0f) * 22050;
    releaseCoefficient = (float) Math.exp(-6.907755278982137 / samples);
  }

  public void setSustainValue(float sustainValue) {
    this.sustainValue = sustainValue;
  }

  public void setSustain(boolean sustain) {
    this.isSustain = sustain;
  }
}
