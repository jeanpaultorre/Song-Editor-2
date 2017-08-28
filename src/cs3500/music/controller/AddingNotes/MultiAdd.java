package cs3500.music.controller.AddingNotes;

import java.awt.*;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.model.MusicEditorModelImpl;
import cs3500.music.model.Note;
import cs3500.music.view.CompositeView;

/**
 * Created by jeanpaul on 4/22/2017.
 */

/**
 * Used to add a Note with a duration specific to the amount of time the user
 * presses on the mouse.
 */
public class MultiAdd extends TimerTask implements IAdd {
  private boolean playing;
  private CompositeView view;
  private MusicEditorModelImpl model;
  private int duration;
  private int start;

  /**
   * Constructor for MultiAdd.
   * @param playing checks if the composition is playing or paused.
   * @param view the view for this controller.
   * @param model the model for this controller.
   * @param duration the duration for the new Note.
   * @param start the start of the Note.
   */
  public MultiAdd(boolean playing, CompositeView view, MusicEditorModelImpl model, int duration,
                  int start) {
    this.playing = playing;
    this.view = view;
    this.model = model;
    this.duration = duration;
    this.start = start;
  }

  @Override
  public void addNotes() {
    if (!playing) {
      Point mouseLoc;
      mouseLoc = view.currentMousePosition();
      Note temp = view.currentNoteAtPoint(mouseLoc);
      Note n = new Note(duration, 1, 64, temp.getPitch(), temp.getOctave(),
              start);
      model.addNote(n);
      try {
        view.render();
      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }
    } else {
      throw new IllegalArgumentException("pause please");
    }
  }

  @Override
  public void run() {
    addNotes();
  }
}
