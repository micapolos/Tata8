package micapolos.tata8.synth;

public interface Waveform {
  float value(float t);

  Waveform SAWTOOTH = t -> t * 2f - 1f;

  // TODO: Use fast table lookup.
  Waveform SINE = t -> (float) Math.sin(t * Math.PI * 2);

  // TODO: Use Periodic 8-bit noise generator.
  Waveform NOISE = _ -> (float) Math.random() * 2 - 1;

  Waveform SQUARE = t -> t < 0.5f ? -1.0f : 1.0f;

  Waveform TRIANGLE = t -> {
    float t4 = t * 4f;
    if (t4 < 1f) {
      return t4;
    } else if (t4 < 3f) {
      return -t4 + 2f;
    } else {
      return t4 - 4f;
    }
  };
}
