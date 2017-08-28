package cs3500.music.provider.controller;

/**
 * Created by jeanpaul on 4/5/2017.
 */

import cs3500.music.controller.IController;
import cs3500.music.provider.model.IMusicModel;

/**
 * Factory for the Controllers.
 */
public class ProviderControllerFactory {
  private IMusicModel model;

  /**
   * Constructor for the Controller Factory.
   * Takes in the provider's Model Interface.
   */
  public ProviderControllerFactory(IMusicModel model) {
    this.model = model;
  }


  /**
   * @param view A string representation of the View.
   * @return the Iview based on the view.
   */
  public IController createController(String view) {
    if (view == null) {
      throw new IllegalArgumentException("Invalid view");
    } else if (view.equals("console")) {
      return new ProviderConsoleController(model);
    } else if (view.equals("visual")) {
      return new ProviderGuiController(model);
    } else if (view.equals("midi")) {
      return new ProviderMidiController(model);
    } else if (view.equals("composite")) {
      throw new IllegalArgumentException("Provider did not provide a Composite view :(");
    } else {
      throw new IllegalArgumentException("Invalid Controller");
    }
  }
}
