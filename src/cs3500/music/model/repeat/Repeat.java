package cs3500.music.model.repeat;

/**
 * Created by jeanpaul on 4/24/2017.
 */

/**
 * The Repeat class will be used to add functionality to the model. It will allow the user to
 * repeat sections of the song.
 */
public class Repeat implements IRepeat{
 private int startRepeat;
 private int endRepeat;
 private int repeats;

  /**
   * Constructor for Repeat.
   * @param startRepeat the start of the Repeat.
   * @param endRepeat the end of the Repeat.
   */
 public Repeat(int startRepeat, int endRepeat) {
   illegalStart();
   this.startRepeat = startRepeat;
   this.endRepeat = endRepeat;
   this.repeats = 1;
 }

  /**
   * The start of the Repeat
   * @return startRepeat.
   */
  @Override
  public int startRepeat() {
    return startRepeat;
  }

  /**
   * The end of the Repeats
   * @return The endRepeat.
   */
  @Override
  public int endRepeat() {
    return endRepeat;
  }

  /**
   * the number of repeats left.
   * @return the number of repeats left.
   */
  @Override
  public int repeats() {
    return repeats;
  }

  /**
   * The number of repeats remaining after a given change.
   * @param num the number which will be changing the repeats.
   */
  @Override
  public void repeatsRemaining(int num) {
    repeats += num;
  }

  /**
   * Constructor must follow these rules.
   */
  private void illegalStart() {
   if (startRepeat < 0) {
     throw new IllegalArgumentException("Start of repeat cant be negative");
   }
   if (startRepeat > endRepeat) {
     throw new IllegalArgumentException("Start of repeat should be greater than end of repeat");
    }
  }

  public void updateEnding(int newEnd) {
    this.endRepeat = newEnd;
  }

}
