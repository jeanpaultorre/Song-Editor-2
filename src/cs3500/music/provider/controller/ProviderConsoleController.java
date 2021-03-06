package cs3500.music.provider.controller;

import java.util.Set;

import cs3500.music.controller.IController;
import cs3500.music.provider.model.IMusicModel;
import cs3500.music.provider.model.INote;

/**
 * Created by jeanpaul on 4/14/2017.
 */

/**
 * Controller for the provider's Console View.
 * It implements my Controller interface.
 */
public class ProviderConsoleController implements IController {

  //the provider's view
  private cs3500.music.provider.view.ConsoleView view
          = new cs3500.music.provider.view.ConsoleView();
  private boolean state = false;
  private Set<INote> loNote;

  /**
   * Initializes the ConsoleController. Constructor for Controller
   *
   * @param model is the provider's model interface.
   */
  public ProviderConsoleController(IMusicModel model) {
    this.view = new cs3500.music.provider.view.ConsoleView();
    loNote = model.getNotes();
  }


  /**
   * runs the program.
   */
  @Override
  public void run() {
    view.createView(loNote);
    view.start();
    state = true;
  }

  /**
   * checks to see controller ran correctly.
   *
   * @return true if controller correctly ran.
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
