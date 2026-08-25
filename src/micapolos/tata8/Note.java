package micapolos;

public enum Note {
  C_0, CIS_0, D_0, DIS_0, E_0, F_0, FIS_0, G_0, GIS_0, A_0, B_0, H_0,
  C_1, CIS_1, D_1, DIS_1, E_1, F_1, FIS_1, G_1, GIS_1, A_1, B_1, H_1,
  C_2, CIS_2, D_2, DIS_2, E_2, F_2, FIS_2, G_2, GIS_2, A_2, B_2, H_2,
  C_3, CIS_3, D_3, DIS_3, E_3, F_3, FIS_3, G_3, GIS_3, A_3, B_3, H_3,
  C_4, CIS_4, D_4, DIS_4, E_4, F_4, FIS_4, G_4, GIS_4, A_4, B_4, H_4,
  C_5, CIS_5, D_5, DIS_5, E_5, F_5, FIS_5, G_5, GIS_5, A_5, B_5, H_5;

  private static final Note[] all = Note.values();

  public final int number = ordinal();

  public static Note withNumber(int number) {
    return all[Math.clamp(number, 0, all.length - 1)];
  }

  public Note plusSemitones(int semitone) {
    return withNumber(number + semitone);
  }

  public Note minusSemitones(int semitone) {
    return withNumber(number - semitone);
  }

  public Note plusOctaves(int octaves) {
    return plusSemitones(octaves * 12);
  }

  public Note minusOctaves(int octaves) {
    return minusSemitones(octaves * 12);
  }
}
