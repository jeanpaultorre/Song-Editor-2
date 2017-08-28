package cs3500.music.controller.PracticeMode;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;

import cs3500.music.controller.Controller;
import cs3500.music.controller.IController;
import cs3500.music.controller.KeyboardHandler;
import cs3500.music.controller.MouseHandler;
import cs3500.music.model.MusicEditorModelImpl;
import cs3500.music.model.ReadOnlyMusicEditorModel;
import cs3500.music.view.CompositeView;

/**
 * Created by jeanpaul on 4/23/2017.
 */

/**
 * Practice version for the MusicEditor.
 * Program waits for user to click on the corresponding Keys in the piano that match those in the
 * model.
 */
public class PracticeController implements IController {

  private MusicEditorModelImpl model;
  private CompositeView view;
  private boolean playing;
  private boolean state = false;
  int count = 0;
  private Timer time;
  boolean correctKeys = false;

  /**
   * Initializes the Controller.
   *
   * @param model the model the Controller will be working with.
   */
  public PracticeController(MusicEditorModelImpl model) {
    this.model = model;
    this.view = new CompositeView(new ReadOnlyMusicEditorModel(model));
    this.playing = true;
    configureKeyBoardHandler();
    configureButtonHandler();
  }

  @Override
  public void run() {
    try {
      SwingUtilities.invokeLater(() -> {
        time = new Timer(model.getTempo() / 1000,
                (event -> start()));
        time.setInitialDelay(100);
        time.start();
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Starts the Controller.
   * Renders the view.
   */
  private void start() {
    try {
      view.render();
      if (view.currentSize() == 0) {
        nextBeat();
      }
      if (correctKeys) {
        view.playPause();
        nextBeat();
        correctKeys = false;
        view.playPause();
      }
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    state = true;
  }

  /**
   * Updates the beat.
   */
  private void nextBeat() {
    model.setCurrentBeat(model.getCurrentBeat() + 1);
    view.updateRed(20);
  }

  @Override
  public boolean state() {
    return false;
  }

  @Override
  public Object getClassStart() {
    throw new IllegalArgumentException("wtf eli");
  }

  @Override
  public Object getClassEnd() {
    throw new IllegalArgumentException("wtf eli");
  }

  @Override
  public Object getClassPlayPause() {
    throw new IllegalArgumentException("wtf eli");
  }

  @Override
  public Object getClassScrollLeft() {
    throw new IllegalArgumentException("wtf eli");
  }

  @Override
  public Object getClassScrollRight() {
    throw new IllegalArgumentException("wtf eli");
  }

  @Override
  public boolean isPlaying() {
    return playing;
  }

  @Override
  public void configureButtonHandler() {
    MouseHandler m = new MouseHandler();
    //m.setClicked(MouseEvent.BUTTON1, new SingleAddNote("single"));
    m.setPressed(MouseEvent.BUTTON1, new ClickPracticeKeys());
    view.addMouseListener(m);
  }

  @Override
  public void configureKeyBoardHandler() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    KeyboardHandler k = new KeyboardHandler();
    k.setKeyPressedMap(keyPresses);
    k.setKeyReleasedMap(keyReleases);
    k.setKeyTypedMap(keyTypes);
    view.addKeyListener(k);
  }

  /**
   * Handles the clicks made by the user on the Keys.
   */
  public class ClickPracticeKeys implements Runnable {
    /**
     * Decreases Tempo
     */
    @Override
    public void run() {
      count++;
      view.updatePracticeNote(view.currentMousePosition());
      if (!correctKeys) {
        view.playPause();
      }
      correctKeys = view.completePractice();
      }
  }
}
