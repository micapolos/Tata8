package micapolos.tata8;

import micapolos.tata8.synth.Waveform;

public enum Wave {
  SINE(Waveform.SINE),
  TRIANGLE(Waveform.TRIANGLE),
  SQUARE(Waveform.SQUARE),
  SAWTOOTH(Waveform.SAWTOOTH),
  NOISE(Waveform.NOISE);

  final Waveform synthWaveform;

  Wave(Waveform synthWaveform) {
    this.synthWaveform = synthWaveform;
  }
}
