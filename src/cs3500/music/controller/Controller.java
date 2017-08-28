package cs3500.music.controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import cs3500.music.controller.AddingNotes.IAdd;
import cs3500.music.controller.AddingNotes.MultiAdd;
import cs3500.music.controller.AddingNotes.SingleAdd;
import cs3500.music.model.MusicEditorModelImpl;
import cs3500.music.model.Note;
import cs3500.music.model.ReadOnlyMusicEditorModel;
import cs3500.music.model.repeat.Repeat;
import cs3500.music.view.CompositeView;

/**
 * Created by jeanpaul on 3/30/2017.
 */

/**
 * Controller for the CompositeView.
 */
public class Controller implements IController {
  private MusicEditorModelImpl model;
  private CompositeView view;
  private boolean playing;
  private boolean state = false;
  int count = 0;
  private Timer time;
  boolean IncreaseDecrease = true;
  boolean pressed = false;
  private IAdd addNotes;
  //private int count = 0;

  /**
   * Initializes the Controller.
   *
   * @param model the model the Controller will be working with.
   */
  public Controller(MusicEditorModelImpl model) {
    this.model = model;
    this.view = new CompositeView(new ReadOnlyMusicEditorModel(model));
    this.playing = false;
    configureKeyBoardHandler();
    configureButtonHandler();
  }

  /**
   * Creates and sets a keyboard listener for the view.
   */
  @Override
  public void configureKeyBoardHandler() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    keyPresses.put(KeyEvent.VK_HOME, new Start());
    keyPresses.put(KeyEvent.VK_END, new End());
    keyPresses.put(KeyEvent.VK_SPACE, new PlayPause());
    keyPresses.put(KeyEvent.VK_LEFT, new ScrollLeft());
    keyPresses.put(KeyEvent.VK_RIGHT, new ScrollRight());
    keyPresses.put(KeyEvent.VK_I, new ChangeTempo("increase"));
    keyPresses.put(KeyEvent.VK_D, new ChangeTempo("decrease"));
    keyReleases.put(KeyEvent.VK_I, new updateIncreaseDecrease());
    keyReleases.put(KeyEvent.VK_D, new updateIncreaseDecrease());

    KeyboardHandler k = new KeyboardHandler();
    k.setKeyPressedMap(keyPresses);
    k.setKeyReleasedMap(keyReleases);
    k.setKeyTypedMap(keyTypes);
    view.addKeyListener(k);
  }

  /**
   * Creates and sets a Mouse Listener to the view.
   */
  @Override
  public void configureButtonHandler() {
    MouseHandler m = new MouseHandler();
    //m.setClicked(MouseEvent.BUTTON1, new SingleAddNote("single"));
    m.setPressed(MouseEvent.BUTTON1, new mousePressed());
    m.setReleased(MouseEvent.BUTTON1, new mouseReleased());

    view.addMouseListener(m);
  }

  /**
   * Starts the Controller.
   * Renders the view.
   */
  private void start() {
    count++;
//    System.out.println(count);
    try {
      view.render();
      if (playing && IncreaseDecrease) {
        nextBeat();
      }
      // Note n = new Note(1, 1, 64, Pitch.FSharp, 5,
      //      count);
      // model.addNote(n);
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    state = true;
  }

  /**
   * What runs the Controller.
   * Timer allows for running of entire program.
   */
  @Override
  public void run() {
//    count++;
//   System.out.println(count);
//    System.out.println(count);
    try {
      SwingUtilities.invokeLater(() -> {
        // System.out.println(model.getTempo());
//        count++;
//        System.out.println(count);
//        if (count == 1) {
//        count++;
//        System.out.println(count);
        time = new Timer(model.getTempo() / 1000,
                (event -> start()));

        //}
        time.setInitialDelay(0);
        time.start();
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * checks if rendered correctly.
   *
   * @return true if rendered correrctly. Otherwise false.
   */
  @Override
  public boolean state() {
    return state;
  }

  /**
   * gets the class Start.
   *
   * @return a new start class.
   */
  @Override
  public Start getClassStart() {
    return new Start();
  }

  /**
   * gets the class End.
   *
   * @return a new end class.
   */
  @Override
  public End getClassEnd() {
    return new End();
  }

  /**
   * gets the class PlayPause.
   *
   * @return a new PlayPause.
   */
  @Override
  public PlayPause getClassPlayPause() {
    return new PlayPause();
  }

  /**
   * gets the class Scroll Left.
   *
   * @return a class Scroll Left.
   */

  @Override
  public ScrollLeft getClassScrollLeft() {
    return new ScrollLeft();
  }
  /**
   * gets the class Scroll Right.
   *
   * @return a class Scroll Right.
   */

  @Override
  public ScrollRight getClassScrollRight() {
    return new ScrollRight();
  }

  /**
   * tells if currently Playing.
   * @return true if playing. False is Paused.
   */
  @Override
  public boolean isPlaying() {
    return playing;
  }

  /**
   * Updates the beat.
   */
  private void nextBeat() {
    if (model.getCurrentBeat() < model.beatLength()) {
      model.setCurrentBeat(model.getCurrentBeat() + 1);
      updateRepeatBeat();
      updateEndingBeat();
      view.updateRed(20);
    } else {
      view.playPause();
      playing = !playing;
    }
  }

  private void updateRepeatBeat() {
    for (int i = 0; i < model.loRepeat().size(); i++) {
      Repeat currentRepeat = model.loRepeat().get(i);
     // System.out.println("cb: " + model.getCurrentBeat());
     // System.out.println("er: " + currentRepeat.endRepeat());
      if (model.getCurrentBeat() == currentRepeat.endRepeat() - 1 && currentRepeat.repeats() > 0) {
        view.updateRed(20);
        view.updateRed(20 * (currentRepeat.endRepeat() - currentRepeat.startRepeat() + 1) * -1);

      }
    }
  }

  private void updateEndingBeat() {
    int size = model.loEndings().size() - 1;
    for (int i = 1; i < model.loEndings().size(); i++) {
      if ((model.loEndings().size() - 1) == i) {
        break;
      }
      size -= 1;
      Repeat currentEnding = model.loEndings().get(i);
      if (model.getCurrentBeat() == currentEnding.endRepeat() - 1 && currentEnding.repeats() > 0) {
        view.updateRed(20);
        view.updateRed(20 * (currentEnding.endRepeat() - model.loEndings().get(0).startRepeat() + 1) * -1);
      }
      System.out.println("cb: " + model.getCurrentBeat());
      System.out.println("er: " + (currentEnding.startRepeat() - 1));
      if (currentEnding.startRepeat() - 1 == model.getCurrentBeat() && size > 0 && currentEnding.repeats() == 0) {
        //System.out.println("VEEFCECEV");
        view.updateRed(20 * (model.loEndings().get(i + 1).startRepeat() - model.getCurrentBeat()));
      //  System.out.println("error2");
      }
    }
  }


  //------------------ADD NOTES-----------------------------------------

java.util.Timer addTimer = new java.util.Timer();
TimerTask task;
   /**
   * Class that adds a Note to the model.
   */
  public class SingleAddNote extends TimerTask implements Runnable {
    String selectAdd;

    SingleAddNote(String selectAdd) {
      this.selectAdd = selectAdd;
    }

    /**
     * adds a Note to the model.
     */
    @Override
    public void run() {
        addNotes = new SingleAdd(playing, view, model);
        addNotes.addNotes();
    }
  }

  private int duration = 0;
  private int multiStart = 0;
  /**
   * Class that adds a Note to the model.
   */
  public class increaseDuration extends TimerTask implements Runnable {



    /**
     * adds a Note to the model.
     */
    @Override
    public void run() {
      if (duration == 0) {
        Point mouseLoc;
        mouseLoc = view.currentMousePosition();
        Note temp = view.currentNoteAtPoint(mouseLoc);
        multiStart = temp.getStartBeat();
      }
      duration++;
      view.updateRed(20);
      }
    }

  /**
   * Class that goes to the start of the Composition.
   */
  public class Start implements Runnable {

    /**
     * Goes to the start of the Composition.
     */
    @Override
    public void run() {
      view.updateRed(45);
      model.setCurrentBeat(view.redLineBeat());
    }
  }


  /**
   * Class that goes to the end of the Composition.
   */
  public class End implements Runnable {

    /**
     * Goes to the end of the Composition.
     */
    @Override
    public void run() {
      view.updateRed(99);
      model.setCurrentBeat(view.redLineBeat());

    }
  }

  /**
   * Class that Plays and Pauses the view.
   */
  public class PlayPause implements Runnable {
    /**
     * Pchanges the current Play/Pause state.
     */
    @Override
    public void run() {
      playing = !playing;
      view.playPause();
    }
  }

  /**
   * Scrolls the Red Line to the Left.
   */
  public class ScrollLeft implements Runnable {

    /**
     * Scrolls the Red Line to the Left.
     */
    @Override
    public void run() {
      if (!playing) {
        view.updateRed(-20);
        view.updateScroll();
        model.setCurrentBeat(view.redLineBeat());
      } else {
        throw new IllegalArgumentException("Pause PLease");
      }
    }
  }

  /**
   * Scrolls the Red Line to the Right.
   */
  public class ScrollRight implements Runnable {

    /**
     * Scrolls the Red Line to the Right.
     */
    @Override
    public void run() {
      if (!playing) {
        view.updateRed(20);
        view.updateScroll();
        model.setCurrentBeat(view.redLineBeat());
      } else {
        throw new IllegalArgumentException("PausePlease");
      }
    }
  }

  /**
   * Changes the Tempo of the composition.
   */
  public class ChangeTempo implements Runnable {

    String IncreaseOrDecrease;

    /**
     * Constructor for ChangeTempo.
     * @param IncreaseOrDecrease One of increase or decrease which will dictate the change.
     */
    ChangeTempo(String IncreaseOrDecrease) {
      this.IncreaseOrDecrease = IncreaseOrDecrease;
    }
    /**
     * Increases or Decreases Tempo.
     */
    @Override
    public void run() {
      int change = 0;
      if (IncreaseOrDecrease.equals("increase")) {
        change = 10000;
      } else if (IncreaseOrDecrease.equals("decrease")) {
        change = -10000;
      } else {
        throw new IllegalArgumentException("Illegal statement");
      }
      time.stop();
      model.updateTempo(change);
      IncreaseDecrease = false;
      try {
        SwingUtilities.invokeLater(() -> {
          count++;
          time = new Timer(model.getTempo() / 1000,
                  (event -> start()));
          count++;
          time.setInitialDelay(0);
          time.start();
        });
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Updates if the tempo is currently being changed.
   */
  public class updateIncreaseDecrease implements Runnable {
    /**
     * Changes IncreaseDecrease to true if user is currently changing tempo.
     */
    @Override
    public void run() {
      IncreaseDecrease = true;
    }
  }


  /**
   * Starts the Timer for the user adding a Note.
   */
  public class mousePressed implements Runnable {
    /**
     * Decreases Tempo
     */
    @Override
    public void run() {
      addTimer = new java.util.Timer();
      task = new increaseDuration();
      addTimer.scheduleAtFixedRate(task, 100, model.getTempo() / 1000);
    }
  }


    /**
     * Uses Multi add.
     * Adds the multi add based on how when the user started pressing on a key and when the user
     * released the key.
     */
    public class mouseReleased implements Runnable {
      /**
       * Decreases Tempo
       */
      @Override
      public void run() {
        task.cancel();
          addNotes = new MultiAdd(playing, view, model, duration, multiStart);
          addNotes.addNotes();
          duration = 0;
          multiStart = 0;
        }
      }
  }