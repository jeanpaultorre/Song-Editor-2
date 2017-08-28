package cs3500.music.model.MultipleEndings;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.model.repeat.Repeat;

/**
 * Created by jeanpaul on 4/25/2017.
 */
public class MultipleEndings {
  Repeat startRepeat;
  List<Repeat> endings;

  public MultipleEndings(Repeat startRepeat, ArrayList<Repeat> endings) {
    this.startRepeat = startRepeat;
    this.endings = endings;
  }


}