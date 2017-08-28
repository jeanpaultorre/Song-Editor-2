package cs3500.music.model.repeat;

/**
 * Created by jeanpaul on 4/24/2017.
 */
public interface IRepeat {

  /**
   * gets the start of the repeat.
   * @return the startRepeat.
   */
  int startRepeat();

  /**
   * gets the end of the Repeat.
   * @return the end Repeat.
   */
  int endRepeat();

  /**
   * gets how many repeats are left.
   * Was originally going to allow multiple repeats of the same Repeat but was not needed.
   * @return the amount of repeats left.
   */
  int repeats();

  /**
   * Updates the amount of repeats remaining based on the given Integer.
   * @param num the number which will be changing the repeats.
   */
  void repeatsRemaining(int num);

  void updateEnding(int update);

}
