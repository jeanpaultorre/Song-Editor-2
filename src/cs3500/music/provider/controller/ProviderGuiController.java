package cs3500.music.provider.controller;

import java.util.Set;

import cs3500.music.controller.IController;
import cs3500.music.provider.model.IMusicModel;
import cs3500.music.provider.model.INote;

/**
 * Created by jeanpaul on 4/14/2017.
 */

/**
 * Controller for the provider's Gui View.
 * It implements my Controller interface.
 */
public class ProviderGuiController implements IController {
  private cs3500.music.provider.view.GuiViewFrame view =
          new cs3500.music.provider.view.GuiViewFrame();
  private boolean state = false;
  private Set<INote> loNote;

  /**
   * Initializes the ConsoleController.
   * Takes in the provider's Model Interface.
   */
  public ProviderGuiController(IMusicModel model) {
    this.view = new cs3500.music.provider.view.GuiViewFrame();
    loNote = model.getNotes();
  }


  @Override
  public void run() {
    view.createView(loNote);
    view.start();
    state = true;
  }

  /**
   * checks to see if controller was ran correctly.
   *
   * @return true if controller was ran correctly.
   */
  @Override
  public boolean state() {
    return state;
  }

  /**
   * Not needed for this Controller.
   *
   * @return an exception.
   */
  @Override
  public Object getClassStart() {
    throw new IllegalArgumentException("Nope");
  }

  /**
   * Not needed for this Controller.
   *
   * @return an exception.
   */
  @Override
  public Object getClassEnd() {
    throw new IllegalArgumentException("Nope");
  }

  /**
   * Not needed for this Controller.
   *
   * @return an exception.
   */
  @Override
  public Object getClassPlayPause() {
    throw new IllegalArgumentException("Nope");
  }

  /**
   * Not needed for this Controller.
   *
   * @return an exception.
   */
  @Override
  public Object getClassScrollLeft() {
    throw new IllegalArgumentException("Nope");
  }

  /**
   * Not needed for this Controller.
   *
   * @return an exception.
   */
  @Override
  public Object getClassScrollRight() {
    throw new IllegalArgumentException("Nope");
  }

  /**
   * Not needed for this Controller.
   *
   * @return an exception.
   */
  @Override
  public boolean isPlaying() {
    throw new IllegalArgumentException("Nope");
  }


  // Not needed for this Controller.
  //@return an exception.
  @Override
  public void configureButtonHandler() {
    throw new IllegalArgumentException("Nope");
  }


  // Not needed for this Controller.
  //@return an exception.
  @Override
  public void configureKeyBoardHandler() {
    throw new IllegalArgumentException("Nope");
  }
}
