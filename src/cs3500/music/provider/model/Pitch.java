package cs3500.music.provider.model;

/**
 * Represents the 12 pitches from C to B, with their associated number 1-12.
 */
public enum Pitch {
  C(1, "C"), C_SHARP(2, "C#"), D(3, "D"), D_SHARP(4, "D#"), E(5, "E"), F(6, "F"),
  F_SHARP(7, "F#"), G(8, "G"), G_SHARP(9, "G#"), A(10, "A"), A_SHARP(11, "A#"), B(12, "B");

  private final int pitchNumber;
  private final String pitchName;

  /**
   * Constructor.
   *
   * @param pitchNumber the number of the pitch in an octave.
   * @param pitchName   the name of the pitch as a String.
   */
  Pitch(int pitchNumber, String pitchName) {
    this.pitchNumber = pitchNumber;
    this.pitchName = pitchName;
  }

  /**
   * Returns the number representing the pitch.
   *
   * @return the number representing the pitch.
   */
  int getPitchNumber() {
    return pitchNumber;
  }

  /**
   * Returns the pitch associated with the given int number.
   *
   * @param value the number of the pitch.
   * @return      the Pitch with the given number.
   */
  public static Pitch fromNumber(int value) {
    while (value < 0) {
      value += 12;
    }
    while (value > 12) {
      value -= 12;
    }
    switch (value) {
      case 12:
      case 0:
        return Pitch.B;
      case 1: return Pitch.C;
      case 2: return Pitch.C_SHARP;
      case 3: return Pitch.D;
      case 4: return Pitch.D_SHARP;
      case 5: return Pitch.E;
      case 6: return Pitch.F;
      case 7: return Pitch.F_SHARP;
      case 8: return Pitch.G;
      case 9: return Pitch.G_SHARP;
      case 10: return Pitch.A;
      case 11: return Pitch.A_SHARP;
      default: return Pitch.C;
    }
  }

  /**
   * Returns the string associated with the pitch.
   *
   * @return the string associated with the pitch.
   */
  @Override
  public String toString() {
    return this.pitchName;

  }
}
