package micapolos.tata8;

public final class Channel {
  final micapolos.synth.Channel synthChannel;

  public float volume = 1.0f;
  public boolean sustain;
  public Wave wave = Wave.TRIANGLE;
  public final Envelope envelope;
  volatile boolean noteTrigger;
  volatile float note;

  Channel(micapolos.synth.Channel synthChannel) {
    this.synthChannel = synthChannel;
    this.envelope = new Envelope(synthChannel.env1);
  }

  public void play(Note note) {
    noteTrigger = true;
    this.note = note.ordinal();
  }

  void sync() {
    synthChannel.setVolume(volume);
    synthChannel.setSustain(sustain);
    synthChannel.osc1.waveform = wave.synthWaveform;
    synthChannel.osc2.waveform = wave.synthWaveform;
    envelope.sync();
    if (noteTrigger) {
      noteTrigger = false;
      synthChannel.note(note);
    }
  }
}
