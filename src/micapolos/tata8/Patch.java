package micapolos;

public final class Patch {
  public Wave osc1Wave = Wave.SQUARE;
  public int osc1Pitch;
  public float osc1Fine;
  public float osc1PulseWidth;
  public float osc1Volume;

  public Wave osc2Wave = Wave.SQUARE;
  public int osc2Pitch;
  public float osc2Fine;
  public float osc2PulseWidth;
  public float osc2Volume;

  public float ampAttack;
  public float ampDecay;
  public float ampSustain;
  public float ampRelease;

  public float modAttack;
  public float modDecay;
  public float modSustain;
  public float modRelease;
  public float modPitchDepth;
  public float modPwmDepth;
}
